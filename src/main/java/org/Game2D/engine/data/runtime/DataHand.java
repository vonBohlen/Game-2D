/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.data.runtime;

import org.Game2D.engine.audio.loops.AudioLoop;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.io.user.Keyhand;
import org.Game2D.engine.objects.loops.ActionLoop;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.objects.GameObject;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DataHand {

    private static final ArrayList<GameObject> gameObjects = new ArrayList<>();


    public static Path confPath = null;

    public static ActionLoop actionLoop = null;
    public static RenderLoop renderLoop = null;
    public static AudioLoop audioLoop = null;

    //Handlers
    public static Keyhand keyHand = null;

    /**
     * Automatically add the specified <code>GameObject</code>
     * to a <code>Chunk</code> based on its position<br>
     * Also add it to the global list of all <code>GameObjects</code>
     *
     * @param go <code>GameObject</code> to be registered
     */
    public static void regGameObj(GameObject go) {
        if (!gameObjects.contains(go)) {
            gameObjects.add(go);
            if (!(go.hitBox == null)) {
                Chunk currentChunk = ChunkMan.ChunkFromCoordinates(go.hitBox.x, go.hitBox.y);
                currentChunk.addGameObject(go);
            }
            sortList(0, gameObjects.size() - 1);
        }
    }

    /**
     * Remove a <code>GameObject</code> from the parent list
     * TODO: Remove from Chunk as well
     *
     * @param go <code>GameObject</code> to be removed
     */
    public static void remGameObj(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject == go);
        sortList(0, gameObjects.size() - 1);
    }

    /**
     * Returns a copy of the currently registered <code>GameObjects</code>
     *
     * @return Current <code>GameObjects</code>
     */
    @SuppressWarnings("unchecked")
    public static List<GameObject> getGameObjs() {
        return (List<GameObject>) gameObjects.clone();
    }

    /**
     * Sort the parent list of <code>GameObjects</code>
     * between the two specified indices
     *
     * @param start First index to be sorted
     * @param end   Last index to be sorted
     */
    private static void sortList(int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = end;
        int pointer = end - 1;
        while (pointer >= start) {
            if (gameObjects.get(pointer).objectLayer > gameObjects.get(pivot).objectLayer) {
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

    /**
     * Switch the place of the <code>GameObject</code> at origin
     * to target by shifting over the entries (to the left to
     * clear up a free space at the end
     *
     * @param origin Index of the <code>GameObject</code> to be moved
     * @param target Index to move to
     */
    private static void queueObjects(int origin, int target) {
        GameObject storedObject = gameObjects.get(origin);
        for (int i = origin + 1; i <= target; i++) {
            gameObjects.set(i - 1, gameObjects.get(i));
        }
        gameObjects.set(target, storedObject);
    }

}
