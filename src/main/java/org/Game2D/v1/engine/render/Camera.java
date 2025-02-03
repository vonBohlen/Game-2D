package org.Game2D.v1.engine.render;

import org.Game2D.v1.engine.chunks.Chunk;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.objects.GameObject;
import org.Game2D.v1.engine.utils.SpinLock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//TODO: calculate witch chunks need to be simulated / rendered
//TODO: rework object rendering

//TODO: ALL OTHER CHANGES -> v.1.1 NOTE: v.1.1 WILL BE A COMPLETE REWRITE AND FULLY DOCUMENTED

public class Camera {

    private SpinLock lock = new SpinLock();

    private Rectangle position;

    private int offsetX = DataHand.renderMan.getWidth() / 2;
    private int offsetY = DataHand.renderMan.getHeight() / 2;

    private int simulationDistance = 8;
    private int renderDistance = 16;

    private final List<Chunk> simulationCache = new ArrayList<>();

    private final List<Chunk> renderCache = new ArrayList<>();

    public Camera() {
        resetPosition();
        DataHand.addCamera(this);
    }

    public void claculateChunks() {

    }

    public void mapObject(GameObject object) {
        position = object.hitBox;
    }

    public void setPosition(int posX, int posY) {
        position = new Rectangle(posX, posY, 0, 0);
    }

    public void resetPosition() {
        position = new Rectangle(0, 0, 0, 0);
    }

    public void updateChunks() {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : simulationCache) {
            chunk.update();
        }
        lock.dropLock(threadId);
    }

    public void renderChunks(Graphics2D graphics2D) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : renderCache) {
            chunk.render(graphics2D, offsetX, offsetY);
        }
        lock.dropLock(threadId);
    }

}
