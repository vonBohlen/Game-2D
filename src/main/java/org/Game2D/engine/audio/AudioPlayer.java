package org.Game2D.engine.audio;

import lombok.Getter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AudioPlayer {

    public final UUID uuid = UUID.randomUUID();

    public final File audioFile;
    private final Clip audioClip;

    @Getter
    private boolean stopped = false;

    public AudioPlayer(File audioFile) {
        this.audioFile = audioFile;
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        audioClip.start();
    }

    public void pause() {
        audioClip.stop();
    }

}
