package org.Game2D.v1.engine.core.handlers;

import org.Game2D.v1.engine.core.managers.ActionMan;
import org.Game2D.v1.engine.core.managers.RenderMan;
import org.Game2D.v1.engine.objects.GameObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class  DataHand {

    public static Path confPath = null;

    public static ActionMan actionMan = null;
    public static RenderMan renderMan = null;

    //Handlers
    public static Keyhand keyHand = null;


    private static final ArrayList<GameObject> gameObjects = new ArrayList<>();

    public static void regGameObj(GameObject go) {
        if (!gameObjects.contains(go)){
            gameObjects.add(go);
            sortList(0, gameObjects.size());
        }
    }

    public static void remGameObj(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject == go);
        sortList(0, gameObjects.size());
    }

    public static List<GameObject> getGameObjs() {
        return (List<GameObject>) gameObjects.clone();
    }

    protected static List<GameObject> getOriginalsGameObjs() {
        return gameObjects;
    }

    private static void sortList(int start, int end){
        if(start == end){ return; }

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
