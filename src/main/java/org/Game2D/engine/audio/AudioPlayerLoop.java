package org.Game2D.engine.audio;

import java.util.HashMap;
import java.util.UUID;

public class AudioPlayerLoop {

    private static final HashMap<UUID, AudioPlayer> players = new HashMap<>();

    public void addAudioPlayer(AudioPlayer audioPlayer) {
        players.put(audioPlayer.uuid, audioPlayer);
    }

    public AudioPlayer getAudioPlayer(UUID uuid) {
        return players.get(uuid);
    }
}
