package org.Game2D.engine.savegame;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.disk.conf.ConfProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

public class SaveGameMan {
    private final Path savegame_location;
    private List<Chunk> currentGame;

    public SaveGameMan() {
        savegame_location = Path.of(ConfProvider.getConfValue("game2d.savegame.location"));
        saveCurrentObjectStorage();
    }

    public void saveCurrentGame() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Chunk i : currentGame) {
            stringBuilder.append(i.POS_X);
            stringBuilder.append(",");
            stringBuilder.append(i.POS_Y);
            stringBuilder.append(".g2d");

            FileOutputStream chunkFile = new FileOutputStream(savegame_location.resolve(stringBuilder.toString()).toFile(), false);

            serializeChunk(i, chunkFile);
            stringBuilder.setLength(0);
        }
    }

    private void saveCurrentObjectStorage() {
        currentGame.addAll(ChunkMan.getObjectStorage().values());
    }

    public void serializeChunk(Chunk i, FileOutputStream outputStream) throws IOException {
        ObjectOutputStream objectStream = new ObjectOutputStream(outputStream);
        objectStream.writeObject(i);
        objectStream.flush();
        objectStream.close();
    }
}
