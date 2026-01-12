package org.Game2D.engine.network;

import java.io.*;
import java.net.*;
import java.util.*;

public class CombinedClient {
    // Connection
    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private PrintWriter tcpOut;
    private BufferedReader tcpIn;

    // Server info
    private String serverHost = "localhost";
    private int tcpPort = 12345;
    private int udpPort = 12346;

    // Client state
    private String clientId;
    private String playerName;
    private boolean connected = false;

    // Game state
    private final Map<String, Player> otherPlayers = new HashMap<>();
    private Player localPlayer;

    public static void main(String[] args) {
        CombinedClient client = new CombinedClient();
        client.start();
    }

    public void start() {
        try {
            System.out.println("=== COMBINED GAME CLIENT ===");
            System.out.println("Connecting to " + serverHost + "...");

            // Connect TCP
            connectTcp();

            // Setup UDP
            setupUdp();

            // Start message handlers
            startMessageHandlers();

            // Start console input
            startConsoleInput();

            // Keep alive
            while (connected) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectTcp() throws IOException {
        tcpSocket = new Socket(serverHost, tcpPort);
        tcpOut = new PrintWriter(tcpSocket.getOutputStream(), true);
        tcpIn = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

        System.out.println("[TCP] Connected to server");
    }

    private void setupUdp() throws SocketException {
        udpSocket = new DatagramSocket();
        System.out.println("[UDP] Socket created on port " + udpSocket.getLocalPort());
    }

    private void startMessageHandlers() {
        // TCP receiver thread
        new Thread(() -> {
            try {
                String message;
                while ((message = tcpIn.readLine()) != null) {
                    System.out.println("[TCP] << " + message);
                    handleTcpMessage(message);
                }
            } catch (IOException e) {
                System.out.println("[TCP] Connection lost");
                connected = false;
            }
        }).start();

        // UDP receiver thread
        new Thread(() -> {
            byte[] buffer = new byte[1024];

            try {
                while (connected) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);

                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("[UDP] << " + message);
                    handleUdpMessage(message);
                }
            } catch (IOException e) {
                if (connected) {
                    System.out.println("[UDP] Receive error: " + e.getMessage());
                }
            }
        }).start();
    }

    private void handleTcpMessage(String message) {
        if (message.startsWith("WELCOME:")) {
            clientId = message.substring(8);
            System.out.println("Client ID assigned: " + clientId);

        } else if (message.startsWith("JOIN_ACK:")) {
            // Format: "JOIN_ACK:name:x:y"
            String[] parts = message.substring(9).split(":");
            playerName = parts[0];
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);

            localPlayer = new Player(playerName, x, y, true);
            connected = true;

            System.out.println("✅ Joined game as: " + playerName);
            System.out.println("Start position: (" + x + ", " + y + ")");

        } else if (message.startsWith("PLAYER_JOINED:")) {
            // New player joined
            String[] parts = message.substring(14).split(":");
            String name = parts[0];
            String id = parts[1];

            if (!otherPlayers.containsKey(id)) {
                otherPlayers.put(id, new Player(name, 0, 0, false));
                System.out.println("👤 Player joined: " + name);
            }

        } else if (message.startsWith("CHAT:")) {
            String[] parts = message.substring(5).split(":", 2);
            if (parts.length == 2) {
                System.out.println("💬 [" + parts[0] + "]: " + parts[1]);
            }

        } else if (message.startsWith("SERVER_SHUTDOWN")) {
            System.out.println("Server is shutting down...");
            connected = false;
        }
    }

    private void handleUdpMessage(String message) {
        if (message.startsWith("POS_UPDATE:")) {
            // Format: "POS_UPDATE:clientId:x:y"
            String[] parts = message.substring(11).split(":");
            String playerId = parts[0];
            float x = Float.parseFloat(parts[1]);
            float y = Float.parseFloat(parts[2]);

            if (otherPlayers.containsKey(playerId)) {
                otherPlayers.get(playerId).updatePosition(x, y);
            }

        } else if (message.startsWith("PONG:")) {
            long sentTime = Long.parseLong(message.substring(5));
            long rtt = System.currentTimeMillis() - sentTime;
            System.out.println("📶 Ping: " + rtt + "ms");
        }
    }

    private void startConsoleInput() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=== CLIENT COMMANDS ===");
            System.out.println("  join <name>      - Join game");
            System.out.println("  chat <message>   - Send chat");
            System.out.println("  pos <x> <y>      - Update position");
            System.out.println("  move <dx> <dy>   - Move relative");
            System.out.println("  ping             - Test latency");
            System.out.println("  players          - List players");
            System.out.println("  quit             - Disconnect");
            System.out.println("========================\n");

            while (true) {
                System.out.print("client> ");
                String input = scanner.nextLine();

                if (input.equals("quit")) {
                    disconnect();
                    break;
                }

                processCommand(input);
            }
        }).start();
    }

    private void processCommand(String command) {
        try {
            if (command.startsWith("join ")) {
                if (connected) {
                    System.out.println("Already joined as: " + playerName);
                    return;
                }

                playerName = command.substring(5);
                sendTcp("JOIN:" + playerName);
                System.out.println("Joining as: " + playerName);

            } else if (command.startsWith("chat ")) {
                if (!connected) {
                    System.out.println("Not connected to game");
                    return;
                }

                String chatMsg = command.substring(5);
                sendTcp("CHAT:" + chatMsg);

            } else if (command.startsWith("pos ")) {
                if (!connected) {
                    System.out.println("Not connected to game");
                    return;
                }

                String[] parts = command.substring(4).split(" ");
                if (parts.length == 2) {
                    float x = Float.parseFloat(parts[0]);
                    float y = Float.parseFloat(parts[1]);

                    localPlayer.updatePosition(x, y);
                    sendUdp("POS:" + x + ":" + y);
                    System.out.println("Position sent: (" + x + ", " + y + ")");
                }

            } else if (command.startsWith("move ")) {
                if (!connected) {
                    System.out.println("Not connected to game");
                    return;
                }

                String[] parts = command.substring(5).split(" ");
                if (parts.length == 2) {
                    float dx = Float.parseFloat(parts[0]);
                    float dy = Float.parseFloat(parts[1]);

                    localPlayer.x += dx;
                    localPlayer.y += dy;

                    sendUdp("POS:" + localPlayer.x + ":" + localPlayer.y);
                    System.out.println("Moved to: (" + localPlayer.x + ", " + localPlayer.y + ")");
                }

            } else if (command.equals("ping")) {
                sendUdp("PING:" + System.currentTimeMillis());
                System.out.println("Ping sent...");

            } else if (command.equals("players")) {
                System.out.println("\n=== PLAYERS ===");
                System.out.println("You: " + localPlayer);

                if (!otherPlayers.isEmpty()) {
                    System.out.println("Others:");
                    for (Player p : otherPlayers.values()) {
                        System.out.println("  " + p);
                    }
                }
                System.out.println("===============");

            } else {
                System.out.println("Unknown command. Type 'join <name>' to start.");
            }

        } catch (Exception e) {
            System.out.println("Command error: " + e.getMessage());
        }
    }

    private void sendTcp(String message) {
        tcpOut.println(message);
        System.out.println("[TCP] >> " + message);
    }

    private void sendUdp(String message) {
        try {
            byte[] data = message.getBytes();
            InetAddress address = InetAddress.getByName(serverHost);
            DatagramPacket packet = new DatagramPacket(data, data.length, address, udpPort);
            udpSocket.send(packet);
            System.out.println("[UDP] >> " + message);
        } catch (IOException e) {
            System.out.println("[UDP] Send error: " + e.getMessage());
        }
    }

    private void disconnect() {
        connected = false;

        try {
            if (tcpSocket != null) tcpSocket.close();
            if (udpSocket != null) udpSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Disconnected.");
        System.exit(0);
    }

    // Player class
    private static class Player {
        String name;
        float x, y;
        boolean isLocal;

        Player(String name, float x, float y, boolean isLocal) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.isLocal = isLocal;
        }

        void updatePosition(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return name + " (" + (isLocal ? "you" : "remote") +
                    ") at (" + String.format("%.1f", x) + ", " + String.format("%.1f", y) + ")";
        }
    }
}