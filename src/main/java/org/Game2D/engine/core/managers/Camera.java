package org.Game2D.engine.core.managers;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.handlers.DataHand;

import java.awt.*;

public class Camera {

    private static double x;
    private static double y;
    private static double width;
    private static double height;

    private static double bow = 0;

    public static double pixelsPerUnit;

    public Camera(double x, double y, double windowHeightUnits)
    {
        //setting the position and windowDimensions
        this.x = x;
        this.y = y;

        height = windowHeightUnits;
        width = ((double)DataHand.renderMan.getWidth() / (double)DataHand.renderMan.getHeight()) * windowHeightUnits;

        pixelsPerUnit = ((double)DataHand.renderMan.getHeight() / windowHeightUnits);
    }

    // kamera wird bewegt skalliert etc und gibt den chunk in ihrem mittelpunkt zurück
    public static Chunk renderUpdate(){
        // in case of errors with creating the value
        if(pixelsPerUnit == 0){ pixelsPerUnit = ((double)DataHand.renderMan.getHeight() / height); }

        //move(0.3,0);
        //System.out.println(x + " " + y);
        //updateScreenHeight(height + 10);

        // ===== Test Kamera bewegung =====

//        bow += 0.01;
//        updateScreenHeight((int)(500 + 500 * Math.sin(bow)));
//        System.out.println(pixelsPerUnit);

        // =========== Ende Test ==========

        // translating the world coordinates into screenspace
        return ChunkMan.ChunkFromCoordinates((int)((x + width / 2.0) * pixelsPerUnit),(int)((y + height / 2.0) * pixelsPerUnit));
    }

    public static void updateScreenHeight(double newHeight){
        height = newHeight;
        pixelsPerUnit = (int)((double)DataHand.renderMan.getHeight() / height);
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
