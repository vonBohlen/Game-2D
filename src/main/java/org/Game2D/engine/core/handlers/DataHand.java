package org.Game2D.engine.core.handlers;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.SpinLock;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class  DataHand {

    public static Path confPath = null;

    private static SpinLock lock = new SpinLock();

    public static ActionMan actionMan = null;
    public static RenderMan renderMan = null;

    //Handlers
    public static Keyhand keyHand = null;


//    private static final ArrayList<Camera> cameras = new ArrayList<>();

    private static final ArrayList<GameObject> gameObjects = new ArrayList<>();

//    public static void addCamera(Camera camera) {
//        long threadId = Thread.currentThread().getId();
//        lock.acquirerLock(threadId);
//        cameras.add(camera);
//        lock.dropLock(threadId);
//    }
//
//    public static void removeCamera(Camera camera) {
//        long threadId = Thread.currentThread().getId();
//        lock.acquirerLock(threadId);
//        cameras.remove(camera);
//        lock.dropLock(threadId);
//    }
//
//    public static List<Camera> getCameras() {
//        return new ArrayList<>(cameras);
//    }

    public static void regGameObj(GameObject go) {
        if (!gameObjects.contains(go)){
            gameObjects.add(go);
            Chunk currentChunk = ChunkMan.ChunkFromCoordinates(go.hitBox.x, go.hitBox.y);
            currentChunk.addGameObject(go);
            sortList(0, gameObjects.size() - 1);
        }
    }

    public static void remGameObj(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject == go);
        sortList(0, gameObjects.size() - 1);
    }

    public static List<GameObject> getGameObjs() {
        return (List<GameObject>) gameObjects.clone();
    }

    protected static List<GameObject> getOriginalsGameObjs() {
        return gameObjects;
    }

    private static void sortList(int start, int end){
        if(start >= end){ return; }

        int pivot = end;
        int pointer = end - 1;
        while(pointer >= start){
            if(gameObjects.get(pointer).objectLayer > gameObjects.get(pivot).objectLayer){
                queueObjects(pointer, end);
                pivot--;
            }
            pointer--;
        }

        //left elements
        if (pivot > 0) sortList(start, pivot - 1);
        //right elements
        if (pivot < gameObjects.size() - 1) sortList(pivot + 1, end);
    }
    private static void queueObjects(int origin, int target){
        GameObject storedObject = gameObjects.get(origin);
        for(int i = origin + 1; i <= target; i++){
            gameObjects.set(i-1, gameObjects.get(i));
        }
        gameObjects.set(target, storedObject);
    }

}
