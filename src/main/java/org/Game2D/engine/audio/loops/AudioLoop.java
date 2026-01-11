package org.Game2D.engine.audio.loops;

import org.Game2D.engine.audio.AudioPlayer;

import java.util.HashMap;
import java.util.UUID;

public class AudioLoop implements Runnable {

    private Thread audioThread;
    private boolean run = true;
    private boolean exit = false;

    private final HashMap<UUID, AudioPlayer> players = new HashMap<>();

    public void startAudioThread() {
        audioThread = new Thread(this);
        audioThread.start();
    }

    @Override
    public void run() {

        while (audioThread != null && !exit) {

            int atps = 60;
            double updateInterval = (double) 1000000000 / atps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int updateCount = 0;
//            long startTime;
//            long tickTime;

            while (run) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / updateInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {

//                    startTime = System.nanoTime();
//
                    managePlayers();
//
//                    tickTime = System.nanoTime() - startTime;
//
//                    DebugScreen.updateTickTime(tickTime);

                    delta--;
                    updateCount++;

                }

                if (timer >= 1000000000) {

//                    DebugScreen.updateTPS(updateCount);

                    updateCount = 0;
                    timer = 0;

                }

            }

        }

    }

    private void managePlayers() {
        players.forEach((uuid, audioPlayer) -> {if (audioPlayer.isStopped()) players.remove(uuid);});
    }

    public void addAudioPlayer(AudioPlayer audioPlayer) {
        players.put(audioPlayer.uuid, audioPlayer);
    }

    public AudioPlayer getAudioPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void removeAudioPlayer(UUID uuid) {
        players.remove(uuid);
    }

    public void freeze() {
        run = false;
    }

    /**
     * Resume the AudioLoop and continue AudioPlayer management
     */
    public void resume() {
        run = true;
    }

    /**
     * Stop the audioLoop and exit
     */
    public void exit() {
        freeze();
        exit = true;
    }

}
