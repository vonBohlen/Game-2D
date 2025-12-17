package org.Game2D.engine.core.managers;

import org.Game2D.engine.chunks.Chunk;

import java.awt.*;

public class Camera {

    private static Rectangle dimensions = new Rectangle();
    private static double x;
    private static double y;
    private static double width;
    private static double height;
    public static int pixelsPerUnit = 0;

    public Camera(double x, double y, double windowHeightUnits)
    {
        //setting the position and windowDimensions
        this.x = x;
        this.y = y;

        this.height = ()windowHeightUnits;
        dimensions.width =
    }

    // kamera wird bewegt skalliert etc und gibt den chunk in ihrem mittelpunkt zurück
    public static Chunk update(){ return null; }

    private static void move(int shiftX, int shiftY){

    }
}
