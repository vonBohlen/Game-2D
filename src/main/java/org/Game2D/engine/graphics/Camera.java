/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.graphics;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;

public class Camera {

    private static double x;
    private static double y;
    private static double width;
    private static double height;

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
    public Camera(double windowHeightUnits){

    }

    // kamera wird bewegt skalliert etc und gibt den chunk in ihrem mittelpunkt zurück
    public static Chunk renderUpdate(){
        // in case of errors with creating the value
        if(pixelsPerUnit == 0){ pixelsPerUnit = ((double)DataHand.renderMan.getHeight() / height); }

        // ===== Test Kamera bewegung =====

        updateScreenHeight(height);

        // =========== Ende Test ==========

        // translating the world coordinates into screenspace
        return ChunkMan.ChunkFromCoordinates((int)((x + width / 2.0) * pixelsPerUnit),(int)((y + height / 2.0) * pixelsPerUnit));
    }

    public static void updateScreenHeight(double newHeight){
        height = newHeight;
        width = ((double)DataHand.renderMan.getWidth() / (double)DataHand.renderMan.getHeight()) * height;
        pixelsPerUnit = ((double)DataHand.renderMan.getHeight() / height);
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
