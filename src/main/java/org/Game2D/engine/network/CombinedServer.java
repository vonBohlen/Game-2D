package org.Game2D.engine.network;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class CombinedServer {
    // Port configuration
    private static final int TCP_PORT = 12345;
    private static final int UDP_PORT = 12346;

    // Server sockets
    private ServerSocket tcpServerSocket;
    private DatagramSocket udpSocket;

    // Client management
    private final Map<String, ClientInfo> connectedClients = new ConcurrentHashMap<>();
    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    // Game state (simple example)
    private final Map<String, PlayerPosition> playerPositions = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        CombinedServer server = new CombinedServer();
        server.start();
    }

    public void start() {
        try {
            System.out.println("=== COMBINED GAME SERVER STARTING ===");
            System.out.println("TCP Port: " + TCP_PORT);
            System.out.println("UDP Port: " + UDP_PORT);

            // Start TCP server
            startTcpServer();

            // Start UDP server
            startUdpServer();

            // Keep main thread alive
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nServer commands:");
            System.out.println("  list      - Show connected clients");
            System.out.println("  broadcast <msg> - Send message to all");
            System.out.println("  quit      - Shutdown server");

            while (true) {
                System.out.print("\nserver> ");
                String command = scanner.nextLine();

                if (command.equals("quit")) {
                    shutdown();
                    break;
                } else if (command.equals("list")) {
                    listClients();
                } else if (command.startsWith("broadcast ")) {
                    String message = command.substring(10);
                    broadcastTcp("SERVER_MSG: " + message);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ============ TCP SERVER PART ============
    private void startTcpServer() throws IOException {
        tcpServerSocket = new ServerSocket(TCP_PORT);
        System.out.println("[TCP] Server listening on port " + TCP_PORT);

        // TCP acceptor thread
        new Thread(() -> {
            try {
                while (!tcpServerSocket.isClosed()) {
                    Socket clientSocket = tcpServerSocket.accept();
                    threadPool.submit(() -> handleTcpClient(clientSocket));
                }
            } catch (IOException e) {
                if (!tcpServerSocket.isClosed()) {
                    System.out.println("[TCP] Accept error: " + e.getMessage());
                }
            }
        }).start();
    }

    private void handleTcpClient(Socket clientSocket) {
        String clientId = null;

        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Get client info
            InetAddress clientAddress = clientSocket.getInetAddress();
            int clientPort = clientSocket.getPort();
            clientId = clientAddress.getHostAddress() + ":" + clientPort;

            System.out.println("[TCP] Client connected: " + clientId);

            // Register client
            ClientInfo clientInfo = new ClientInfo(clientSocket, out);
            connectedClients.put(clientId, clientInfo);

            // Send welcome
            out.println("WELCOME:" + clientId);
            out.println("INFO:TCP=" + TCP_PORT + ",UDP=" + UDP_PORT);

            // Handle TCP messages
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[TCP][" + clientId + "] " + message);
                processTcpMessage(clientId, message);
            }

        } catch (IOException e) {
            System.out.println("[TCP] Client disconnected: " + clientId);
        } finally {
            if (clientId != null) {
                connectedClients.remove(clientId);
                playerPositions.remove(clientId);
                System.out.println("[TCP] Removed client: " + clientId);
            }
        }
    }

    // ============ UDP SERVER PART ============
    private void startUdpServer() throws IOException {
        udpSocket = new DatagramSocket(UDP_PORT);
        System.out.println("[UDP] Server listening on port " + UDP_PORT);

        // UDP handler thread
        new Thread(() -> {
            byte[] buffer = new byte[1024];

            try {
                while (!udpSocket.isClosed()) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);

                    threadPool.submit(() -> handleUdpPacket(packet));
                }
            } catch (IOException e) {
                if (!udpSocket.isClosed()) {
                    System.out.println("[UDP] Receive error: " + e.getMessage());
                }
            }
        }).start();
    }

    private void handleUdpPacket(DatagramPacket packet) {
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();
        String clientKey = clientAddress.getHostAddress() + ":" + clientPort;

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.println("[UDP][" + clientKey + "] " + message);

        // Process UDP message
        processUdpMessage(clientKey, message, clientAddress, clientPort);
    }

    // ============ MESSAGE PROCESSING ============
    private void processTcpMessage(String clientId, String message) {
        if (message.startsWith("JOIN:")) {
            String playerName = message.substring(5);
            System.out.println("[GAME] Player joined: " + playerName + " (" + clientId + ")");

            // Assign random position
            PlayerPosition pos = new PlayerPosition(
                    (float)(Math.random() * 800),
                    (float)(Math.random() * 600)
            );
            playerPositions.put(clientId, pos);

            // Notify client
            ClientInfo client = connectedClients.get(clientId);
            if (client != null) {
                client.send("JOIN_ACK:" + playerName + ":" + pos.x + ":" + pos.y);
            }

            // Notify others
            broadcastTcpExcept("PLAYER_JOINED:" + playerName + ":" + clientId, clientId);

        } else if (message.startsWith("CHAT:")) {
            String chatMsg = message.substring(5);
            broadcastTcp("CHAT:" + clientId + ":" + chatMsg);

        } else if (message.equals("GET_PLAYERS")) {
            ClientInfo client = connectedClients.get(clientId);
            if (client != null) {
                client.send("PLAYERS:" + buildPlayersList());
            }
        }
    }

    private void processUdpMessage(String clientKey, String message,
                                   InetAddress address, int port) {
        if (message.startsWith("POS:")) {
            // Format: "POS:x:y"
            String[] parts = message.substring(4).split(":");
            if (parts.length == 2) {
                try {
                    float x = Float.parseFloat(parts[0]);
                    float y = Float.parseFloat(parts[1]);

                    // Update position
                    playerPositions.put(clientKey, new PlayerPosition(x, y));

                    // Broadcast to all other clients (except sender)
                    String posMsg = "POS_UPDATE:" + clientKey + ":" + x + ":" + y;
                    broadcastUdpExcept(posMsg, address, port);

                } catch (NumberFormatException e) {
                    System.out.println("[UDP] Invalid position format: " + message);
                }
            }

        } else if (message.startsWith("PING")) {
            // Send pong back
            sendUdp("PONG:" + System.currentTimeMillis(), address, port);

        } else if (message.startsWith("INPUT:")) {
            // Game input from client
            broadcastUdpExcept(message, address, port);
        }
    }

    // ============ UTILITY METHODS ============
    private void sendUdp(String message, InetAddress address, int port) {
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            udpSocket.send(packet);
        } catch (IOException e) {
            System.out.println("[UDP] Send error: " + e.getMessage());
        }
    }

    private void broadcastUdp(String message) {
        for (ClientInfo client : connectedClients.values()) {
            if (client.udpAddress != null && client.udpPort > 0) {
                sendUdp(message, client.udpAddress, client.udpPort);
            }
        }
    }

    private void broadcastUdpExcept(String message, InetAddress exceptAddress, int exceptPort) {
        for (ClientInfo client : connectedClients.values()) {
            if (client.udpAddress != null && client.udpPort > 0 &&
                    !(client.udpAddress.equals(exceptAddress) && client.udpPort == exceptPort)) {
                sendUdp(message, client.udpAddress, client.udpPort);
            }
        }
    }

    private void broadcastTcp(String message) {
        for (ClientInfo client : connectedClients.values()) {
            client.send(message);
        }
    }

    private void broadcastTcpExcept(String message, String exceptClientId) {
        for (Map.Entry<String, ClientInfo> entry : connectedClients.entrySet()) {
            if (!entry.getKey().equals(exceptClientId)) {
                entry.getValue().send(message);
            }
        }
    }

    private String buildPlayersList() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, PlayerPosition> entry : playerPositions.entrySet()) {
            sb.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue().x)
                    .append(":")
                    .append(entry.getValue().y)
                    .append(";");
        }
        return sb.toString();
    }

    private void listClients() {
        System.out.println("\n=== CONNECTED CLIENTS ===");
        System.out.println("Total: " + connectedClients.size());

        for (Map.Entry<String, ClientInfo> entry : connectedClients.entrySet()) {
            ClientInfo client = entry.getValue();
            PlayerPosition pos = playerPositions.get(entry.getKey());

            System.out.println("  " + entry.getKey() +
                    " [TCP: " + client.socket.getPort() +
                    ", UDP: " + (client.udpPort > 0 ? client.udpPort : "not set") + "]" +
                    (pos != null ? String.format(" Position: (%.1f, %.1f)", pos.x, pos.y) : ""));
        }
    }

    private void shutdown() {
        System.out.println("\nShutting down server...");

        // Notify all clients
        broadcastTcp("SERVER_SHUTDOWN");

        // Close sockets
        try {
            if (tcpServerSocket != null && !tcpServerSocket.isClosed()) {
                tcpServerSocket.close();
            }
            if (udpSocket != null && !udpSocket.isClosed()) {
                udpSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Shutdown thread pool
        threadPool.shutdown();
        System.out.println("Server stopped.");
    }

    // ============ SUPPORT CLASSES ============
    private static class ClientInfo {
        Socket socket;
        PrintWriter tcpOut;
        InetAddress udpAddress;
        int udpPort = -1;

        ClientInfo(Socket socket, PrintWriter out) {
            this.socket = socket;
            this.tcpOut = out;
            this.udpAddress = socket.getInetAddress();
            this.udpPort = socket.getPort() + 1; // Guess UDP port (TCP port + 1)
        }

        void send(String message) {
            tcpOut.println(message);
        }
    }

    private static class PlayerPosition {
        float x, y;

        PlayerPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}