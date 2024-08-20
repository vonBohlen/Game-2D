package org.Game2D.engine.core.handlers;

import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.GameObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class  DataHand {

    public static Path confPath = null;

    public static ActionMan actionMan = null;
    public static RenderMan renderMan = null;

    //Handlers
    public static Keyhand keyHand = null;


    private static List<GameObject> gameObjects = new ArrayList<>();

    public static void regGameObj(GameObject go) {
        gameObjects.add(go);
    }

    public static void remGameObj(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject == go);
    }

    public static List<GameObject> getGameObjs() {
        return gameObjects;
    }

}
