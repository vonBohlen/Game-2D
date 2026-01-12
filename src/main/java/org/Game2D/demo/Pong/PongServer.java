package org.Game2D.demo.Pong;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Pong Game Server
 * Manages 2-player Pong game over TCP
 * Handles connections, game state, and synchronization
 */
public class PongServer {
    private static final int PORT = 12347;
    private static ServerSocket serverSocket;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private static PongGameState gameState = new PongGameState();
    private static boolean gameRunning = false;

    public static void main(String[] args) throws IOException {
        System.out.println("=== PONG GAME SERVER STARTING ===");
        System.out.println("Port: " + PORT);
        System.out.println("Waiting for 2 players...");

        serverSocket = new ServerSocket(PORT);

        // Wait for exactly 2 players
        for (int i = 0; i < 2; i++) {
            Socket clientSocket = serverSocket.accept();
            int playerId = i + 1;

            ClientHandler client = new ClientHandler(clientSocket, playerId);
            clients.add(client);
            new Thread(client).start();

            System.out.println("Player " + playerId + " connected from " +
                    clientSocket.getInetAddress());

            // Send welcome message with player ID
            client.send("WELCOME:" + playerId);
        }

        System.out.println("Both players connected! Starting game...");
        startGame();
    }

    /**
     * Starts the game loop after both players are connected
     */
    private static void startGame() {
        gameRunning = true;

        // Send initial game state to both players
        broadcast("GAMESTATE:" + gameState.serialize());
        broadcast("START");

        // Game loop thread (runs at ~60 FPS)
        new Thread(() -> {
            try {
                while (gameRunning) {
                    // Update game physics
                    gameState.update();

                    // Broadcast new state to all clients
                    broadcast("GAMESTATE:" + gameState.serialize());

                    // Maintain 60 FPS
                    Thread.sleep(16);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Sends a message to all connected clients
     */
    private static void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.send(message);
        }
    }

    /**
     * Handles paddle position updates from clients
     */
    private static void handlePaddleUpdate(int playerId, float y) {
        // Update server's game state
        if (playerId == 1) {
            gameState.player1Y = y;
        } else {
            gameState.player2Y = y;
        }

        // Send update to the other player only
        for (ClientHandler client : clients) {
            if (client.playerId != playerId) {
                client.send("PADDLE_UPDATE:" + playerId + ":" + y);
            }
        }
    }

    /**
     * Handles individual client connections
     */
    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private int playerId;

        ClientHandler(Socket socket, int playerId) throws IOException {
            this.socket = socket;
            this.playerId = playerId;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String message;
                // Listen for messages from this client
                while ((message = in.readLine()) != null) {
                    System.out.println("Player " + playerId + " sent: " + message);

                    if (message.startsWith("PADDLE:")) {
                        // Extract paddle position
                        float y = Float.parseFloat(message.substring(7));
                        handlePaddleUpdate(playerId, y);
                    }
                }
            } catch (IOException e) {
                System.out.println("Player " + playerId + " disconnected");
            } finally {
                // Remove client and check if game should continue
                clients.remove(this);
                if (clients.size() < 2) {
                    gameRunning = false;
                    System.out.println("Game stopped - not enough players");
                }
            }
        }

        /**
         * Sends a message to this specific client
         */
        void send(String message) {
            out.println(message);
        }
    }
}