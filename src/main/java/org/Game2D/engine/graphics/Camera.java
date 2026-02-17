/**
 * /engine/graphics/Camera.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Silas Vogel, Christian von Bohlen
 */

package org.Game2D.engine.graphics;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.objects.GameObject;

public class Camera {

    private static double x;
    private static double y;
    private static double width;
    private static double height;

    public static GameObject trackingObject = null;

    public static double pixelsPerUnit;

    public Camera(double x, double y, double windowHeightUnits)
    {
        //setting the position and windowDimensions
        this.x = x;
        this.y = y;

        height = windowHeightUnits;
        width = ((double)DataHand.renderLoop.getWidth() / (double)DataHand.renderLoop.getHeight()) * windowHeightUnits;

        pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / windowHeightUnits);
    }
    public Camera(double windowHeightUnits){

    }

    // kamera wird bewegt skalliert etc und gibt den chunk in ihrem mittelpunkt zurück
    public static Chunk renderUpdate(){
        syncPositionToObject();
        // set height and width if not set correctly
        if(height == 0){ height = (double)DataHand.renderLoop.getHeight(); }

        // in case of errors with creating the value
        if(pixelsPerUnit == 0){ pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / height); }

        // ===== Test Kamera bewegung =====

        updateScreenHeight(height);

        // =========== Ende Test ==========

        // translating the world coordinates into screenspace
        return ChunkMan.chunkFromCoordinates((int)((x + width / 2.0) * pixelsPerUnit),(int)((y + height / 2.0) * pixelsPerUnit));
    }

    public static void updateScreenHeight(double newHeight){
        height = newHeight;
        width = ((double)DataHand.renderLoop.getWidth() / (double)DataHand.renderLoop.getHeight()) * height;
        pixelsPerUnit = ((double)DataHand.renderLoop.getHeight() / height);
    }

    private static void syncPositionToObject() {
        if (trackingObject == null) return;
        x = trackingObject.hitBox.x;
        y = trackingObject.hitBox.y;
    }

    public static int getScreenSpacePosX(){
        return (int)(x * pixelsPerUnit);
    }
    public static int getScreenSpacePosY(){
        return (int)(y * pixelsPerUnit);
    }

    private static void move(double shiftX, double shiftY){
        x += shiftX;
        y += shiftY;
    }
}
