package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;
import org.Game2D.v1.engine.utils.SpinLock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Chunk {

    private final SpinLock lock = new SpinLock();


    private final Vector<Integer> id;

    private final List<GameObject> gameObjects = new ArrayList<>();


    public Chunk(Vector<Integer> id, List<GameObject> initialObjects) {
        this.id = id;

        addGameObjects(initialObjects);
    }


    //Sorting

    void insertSort(GameObject object){
        int index = 0;
        for(GameObject currentObject : gameObjects){
            if(currentObject.objectLayer >= object.objectLayer){
                gameObjects.add(object);
                queueObjects(gameObjects.size()-1, index);
            }
            else{
                index++;
            }
        }
    }

    private void quickSort(int start, int end){
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
        if (pivot > 0) quickSort(start, pivot - 1);
        //right elements
        if (pivot < gameObjects.size() - 1) quickSort(pivot + 1, end);
    }
    private void queueObjects(int origin, int target){
        if(origin < target){
            GameObject storedObject = gameObjects.get(origin);
            for(int i = origin + 1; i <= target; i++){
                gameObjects.set(i-1, gameObjects.get(i));
            }
            gameObjects.set(target, storedObject);
        }
        else if(target < origin){
            GameObject storedObject = gameObjects.get(target);
            for(int i = target + 1; i <= origin; i++){
                gameObjects.set(i-1, gameObjects.get(i));
            }
            gameObjects.set(origin, storedObject);
        }
    }


    //List content management

    public void addGameObjects(GameObject gameObject) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        insertSort(gameObject);
        lock.dropLock(threadId);
    }

    public void addGameObjects(List<GameObject> gameObjects) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        this.gameObjects.addAll(gameObjects);
        quickSort(0, this.gameObjects.size()-1);
        lock.dropLock(threadId);
    }

    public void removeGameObject(GameObject gameObject) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        gameObjects.remove(gameObject);
        lock.dropLock(threadId);
    }

    public void removeGameObjects(List<GameObject> gameObjects) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        this.gameObjects.removeAll(gameObjects);
        lock.dropLock(threadId);
    }


    //

    public void update() {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        lock.dropLock(threadId);
    }

    public void render(Graphics2D g2, int offsetX, int offsetY) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (GameObject gameObject : gameObjects) {
            gameObject.render(g2, offsetX, offsetY);
        }
        lock.dropLock(threadId);
    }


    //

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public Vector<Integer> getId() {
        return new Vector<>(id);
    }

}
