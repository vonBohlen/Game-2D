package org.Game2D.engine.graphics;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.objects.GameObject;

import javax.annotation.Nullable;
import java.util.UUID;

public class CameraReplacement {

    // Identifier
    public final UUID UUID = java.util.UUID.randomUUID();

    // Camera x and y position
    private double x, y;

    // Viewport dimensions
    private double viewportWidth, viewportHeight;

    // Render scale
    public double pixelsPerUnit;

    // Object witch the camera should track
    @Nullable
    public GameObject trackingObject = null;

    public CameraReplacement(double x, double y, double viewportHeight) {

        // Setting initial position
        setPosition(x, y);

        // Setting viewport dimensions
        updateViewportDimensions(viewportHeight);

        // Setting render scale
        pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / viewportHeight);

    }

    public CameraReplacement(GameObject trackingObject, double viewportHeight) {

        // Setting object to track
        this.trackingObject = trackingObject;

        // Setting initial position
        syncPositionToTrackingObject();

        // Setting viewport dimensions
        updateViewportDimensions(viewportHeight);

        // Setting scale
        pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / viewportHeight);

    }

    private void syncPositionToTrackingObject() {
        if (trackingObject == null) return;
        x = trackingObject.hitBox.x;
        y = trackingObject.hitBox.y;
    }

    public void updateViewportDimensions(double height){
        viewportHeight = height;
        viewportWidth = ((double)DataHand.renderLoop.getWidth() / (double)DataHand.renderLoop.getHeight()) * viewportHeight;
        pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / viewportHeight);
    }

    public Chunk getViewportChunk() {
        syncPositionToTrackingObject();

        // set height and width if not set correctly
        if(viewportHeight == 0) updateViewportDimensions(DataHand.renderLoop.getHeight());

        // translating the world coordinates into screenspace
        return ChunkMan.chunkFromCoordinates((int)((x + viewportWidth / 2.0) * pixelsPerUnit),(int)((y + viewportHeight / 2.0) * pixelsPerUnit));
    }

    public int getScreenSpacePosX(){
        return (int)(x * pixelsPerUnit);
    }
    public int getScreenSpacePosY(){
        return (int)(y * pixelsPerUnit);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private void move(double shiftX, double shiftY){
        x += shiftX;
        y += shiftY;
    }

}
