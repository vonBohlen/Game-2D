package org.Game2D.demo.Pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Pong Game Client
 * Connects to server, displays game, handles player input
 */
public class PongClient extends JFrame {
    // Network connection
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 12347;

    // Game state
    private PongGameState gameState = new PongGameState();
    private int playerId = 0;  // 0 = not connected, 1 or 2 = player number
    private boolean gameStarted = false;

    // GUI components
    private GamePanel gamePanel;
    private JLabel statusLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PongClient client = new PongClient();
            client.setVisible(true);
            client.connectToServer();
        });
    }

    public PongClient() {
        setTitle("Multiplayer Pong - Player ?");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status label at the top
        statusLabel = new JLabel("Connecting to server...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(statusLabel, BorderLayout.NORTH);

        // Game drawing panel
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // Info panel at the bottom
        JPanel infoPanel = new JPanel(new FlowLayout());
        infoPanel.add(new JLabel("Controls: UP/DOWN arrows"));
        add(infoPanel, BorderLayout.SOUTH);

        // Setup keyboard controls
        setupControls();

        // Center window on screen
        setLocationRelativeTo(null);
    }

    /**
     * Establishes connection to the game server
     */
    private void connectToServer() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            statusLabel.setText("Connected! Waiting for opponent...");

            // Start thread to receive messages from server
            new Thread(this::receiveMessages).start();

        } catch (IOException e) {
            statusLabel.setText("Connection failed: " + e.getMessage());
        }
    }

    /**
     * Listens for messages from the server (runs in separate thread)
     */
    private void receiveMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server: " + message);
                handleServerMessage(message);
            }
        } catch (IOException e) {
            SwingUtilities.invokeLater(() ->
                    statusLabel.setText("Connection lost"));
        }
    }

    /**
     * Processes messages received from the server
     */
    private void handleServerMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message.startsWith("WELCOME:")) {
                // Server assigned us a player ID
                playerId = Integer.parseInt(message.substring(8));
                setTitle("Multiplayer Pong - Player " + playerId);
                statusLabel.setText("You are Player " + playerId + ". Waiting for game start...");

            } else if (message.equals("START")) {
                // Game is starting
                gameStarted = true;
                statusLabel.setText("Game Started! Player " + playerId);
                requestFocus(); // Get keyboard focus for controls

            } else if (message.startsWith("GAMESTATE:")) {
                // Full game state update
                gameState = PongGameState.deserialize(message.substring(10));
                gamePanel.repaint();

            } else if (message.startsWith("PADDLE_UPDATE:")) {
                // Opponent's paddle moved
                String[] parts = message.substring(14).split(":");
                int updatePlayerId = Integer.parseInt(parts[0]);
                float y = Float.parseFloat(parts[1]);

                // Only update if it's the other player
                if (updatePlayerId != playerId) {
                    if (updatePlayerId == 1) {
                        gameState.player1Y = y;
                    } else {
                        gameState.player2Y = y;
                    }
                    gamePanel.repaint();
                }
            }
        });
    }

    /**
     * Sets up keyboard controls for paddle movement
     */
    private void setupControls() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Only accept input when game is running
                if (!gameStarted || playerId == 0) {
                    System.out.println("Game not started yet");
                    return;
                }

                float paddleSpeed = 15;
                float currentY = (playerId == 1) ? gameState.player1Y : gameState.player2Y;
                float newY = currentY;

                // Determine movement direction
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    newY -= paddleSpeed;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    newY += paddleSpeed;
                } else {
                    return; // Ignore other keys
                }

                // Keep paddle within screen bounds (0-500)
                newY = Math.max(0, Math.min(500, newY));

                // Send new position to server
                out.println("PADDLE:" + newY);
                System.out.println("Sent paddle position: " + newY);

                // Update locally for immediate visual feedback
                if (playerId == 1) {
                    gameState.player1Y = newY;
                } else {
                    gameState.player2Y = newY;
                }
                gamePanel.repaint();
            }
        });

        setFocusable(true);
    }

    /**
     * Custom JPanel that draws the game
     */
    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw black background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            // Draw center line (dashed)
            g.setColor(Color.GRAY);
            Graphics2D g2d = (Graphics2D) g;
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    0, new float[]{10}, 0);
            g2d.setStroke(dashed);
            g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

            // Restore normal stroke
            g2d.setStroke(new BasicStroke(1));

            // Draw paddles
            g.setColor(Color.GREEN);
            g.fillRect(30, (int)gameState.player1Y, 20, 100);  // Player 1 (left)

            g.setColor(Color.RED);
            g.fillRect(getWidth() - 50, (int)gameState.player2Y, 20, 100); // Player 2 (right)

            // Draw ball
            g.setColor(Color.WHITE);
            g.fillOval((int)gameState.ballX - 10, (int)gameState.ballY - 10, 20, 20);

            // Draw scores
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            String scoreText = gameState.scorePlayer1 + "   " + gameState.scorePlayer2;
            int scoreWidth = g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, getWidth() / 2 - scoreWidth / 2, 60);

            // Draw player info
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            String playerText = "You: Player " + playerId + " (" +
                    (playerId == 1 ? "GREEN" : "RED") + ")";
            g.drawString(playerText, 20, getHeight() - 20);

            // Draw waiting message if game hasn't started
            if (!gameStarted) {
                g.setColor(new Color(255, 255, 255, 200));
                g.fillRect(getWidth() / 2 - 150, getHeight() / 2 - 50, 300, 100);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                String waitText = "Waiting for opponent...";
                int waitWidth = g.getFontMetrics().stringWidth(waitText);
                g.drawString(waitText, getWidth() / 2 - waitWidth / 2, getHeight() / 2);
            }
        }
    }
}