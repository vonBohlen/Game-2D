package org.Game2D.demo.Pong;

import java.io.Serializable;

/**
 * Represents the complete game state for Pong
 * Contains player positions, ball position, and scores
 */
public class PongGameState implements Serializable {
    // Player paddle positions
    public float player1Y = 250;  // Left paddle (Player 1)
    public float player2Y = 250;  // Right paddle (Player 2)

    // Ball position and velocity
    public float ballX = 400;
    public float ballY = 300;
    public float ballSpeedX = 5;
    public float ballSpeedY = 3;

    // Scores
    public int scorePlayer1 = 0;
    public int scorePlayer2 = 0;

    // Player ID (1 or 2) - used by clients
    public int playerId;

    /**
     * Updates the game state (ball movement, collisions, scoring)
     * Called every frame by the server
     */
    public void update() {
        // Move ball
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Top/bottom wall collision
        if (ballY <= 0 || ballY >= 600) {
            ballSpeedY = -ballSpeedY;
            ballY = Math.max(0, Math.min(600, ballY)); // Keep within bounds
        }

        // Left wall (Player 2 scores)
        if (ballX <= 0) {
            scorePlayer2++;
            resetBall();
        }

        // Right wall (Player 1 scores)
        if (ballX >= 800) {
            scorePlayer1++;
            resetBall();
        }

        // Paddle collision - Player 1 (left)
        if (ballX <= 50 && ballX >= 30 &&
                ballY >= player1Y && ballY <= player1Y + 100) {
            ballSpeedX = Math.abs(ballSpeedX); // Move right
            // Add spin based on hit position
            float hitY = ballY - (player1Y + 50);
            ballSpeedY = hitY * 0.1f;
        }

        // Paddle collision - Player 2 (right)
        if (ballX >= 750 && ballX <= 770 &&
                ballY >= player2Y && ballY <= player2Y + 100) {
            ballSpeedX = -Math.abs(ballSpeedX); // Move left
            float hitY = ballY - (player2Y + 50);
            ballSpeedY = hitY * 0.1f;
        }
    }

    /**
     * Resets ball to center after a point is scored
     */
    private void resetBall() {
        ballX = 400;
        ballY = 300;
        ballSpeedX = 5 * (Math.random() > 0.5 ? 1 : -1);
        ballSpeedY = 3 * (Math.random() > 0.5 ? 1 : -1);

        // Brief pause after scoring
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore interruption
        }
    }

    /**
     * Serializes game state to string for network transmission
     * Format: "STATE:player1Y:player2Y:ballX:ballY:score1:score2"
     */
    public String serialize() {
        return String.format("STATE:%.1f:%.1f:%.1f:%.1f:%d:%d",
                player1Y, player2Y, ballX, ballY, scorePlayer1, scorePlayer2);
    }

    /**
     * Deserializes string back to game state object
     */
    public static PongGameState deserialize(String data) {
        try {
            PongGameState state = new PongGameState();
            String[] parts = data.substring(6).split(":");
            state.player1Y = Float.parseFloat(parts[0]);
            state.player2Y = Float.parseFloat(parts[1]);
            state.ballX = Float.parseFloat(parts[2]);
            state.ballY = Float.parseFloat(parts[3]);
            state.scorePlayer1 = Integer.parseInt(parts[4]);
            state.scorePlayer2 = Integer.parseInt(parts[5]);
            return state;
        } catch (Exception e) {
            System.err.println("Deserialize error: " + data);
            return new PongGameState();
        }
    }
}