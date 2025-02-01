package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Chunk {

    private volatile boolean lockAcquired = false;

    private final Vector<Integer> id;

    private final List<GameObject> gameObjects = new ArrayList<>();

    private final ArrayList<Long> improQueue = new ArrayList<>();

    public Chunk(Vector<Integer> id, List<GameObject> initialObjects) {
        this.id = id;

        addGameObject(initialObjects);
    }


    //Spin lock
    private void acquirerLock(long id) {
        addToQueue(id);
        while (lockAcquired && getFirstInQueue() == id) {
            Thread.onSpinWait();
        }
        removeFirstInQueue();
        lockAcquired = true;
    }
    private void addToQueue(Long id){
        improQueue.add(id);
    }
    private long getFirstInQueue(){
        return improQueue.get(0);
    }
    private void removeFirstInQueue(){
        improQueue.remove(0);
    }

    private void dropLock() {
        lockAcquired = false;
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
    public void addGameObject(GameObject gameObject) {
        acquirerLock(Thread.currentThread().getId());
        insertSort(gameObject);
        dropLock();
    }

    public void addGameObject(List<GameObject> gameObjects) {
        acquirerLock(Thread.currentThread().getId());
        this.gameObjects.addAll(gameObjects);
        quickSort(0, this.gameObjects.size()-1);
        dropLock();
    }

    public void removeGameObject(GameObject gameObject) {
        acquirerLock(Thread.currentThread().getId());
        gameObjects.removeIf(current -> current == gameObject);
        dropLock();
    }

    public void removeGameObjects(List<GameObject> gameObjects) {
        acquirerLock(Thread.currentThread().getId());
        this.gameObjects.removeAll(gameObjects);
        dropLock();
    }


    //
    public void update() {
        acquirerLock(Thread.currentThread().getId());
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        dropLock();
    }

    public void draw(Graphics2D g2) {
        acquirerLock(Thread.currentThread().getId());
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2);
        }
        dropLock();
    }


    //
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public Vector<Integer> getId() {
        return new Vector<>(id);
    }

}
