package org.Game2D.engine.audio.managers;

import org.Game2D.engine.audio.AudioPlayer;
import org.Game2D.engine.data.runtime.DataHand;

import java.io.File;
import java.util.UUID;

public class AudioMan {

    public static UUID playAudio(String path) {
        File audioFile = new File(path);
        AudioPlayer audioPlayer = new AudioPlayer(audioFile);
        DataHand.audioLoop.addAudioPlayer(audioPlayer);
        return audioPlayer.uuid;
    }

    public static void pauseAudio(UUID playerUUID) {
        DataHand.audioLoop.getAudioPlayer(playerUUID).pause();
    }

    public static void stopAudio(UUID playerUUID) {
        DataHand.audioLoop.getAudioPlayer(playerUUID).stop();
    }

}
