package org.Game2D.engine.audio;

import lombok.Getter;

import java.io.File;
import java.util.UUID;

public class AudioPlayer {

    public final UUID uuid = UUID.randomUUID();

    public final File audioFile;

    @Getter
    private boolean stopped = false;

    public AudioPlayer(File audioFile) {
        this.audioFile = audioFile;
    }

}
