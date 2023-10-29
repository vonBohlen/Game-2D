package de.Alienlive.games.Game2D.objects.objects.object;

import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.RenderManager;

import java.awt.*;
import java.util.Arrays;

public class Object {

    public RenderManager renderManager;
    public ActionManager actionManager;

    private final int[][] coordinates;

    public int x;
    public int y;

    public int height;
    public int width;

    public Object(int x, int y, int height, int width, Cavity[] cavities, Instance instance) {

        renderManager = instance.getRenderManager();
        actionManager = instance.getActionManager();

        this.x = x;
        this.y = y;

        this.height = height;
        this.width = width;

        int[][] coordinates1;
        coordinates1 = new int[width * height][2];

        int index = 0;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                coordinates1[index][0] = i; // x-coordinate
                coordinates1[index][1] = j; // y-coordinate
                index++;
            }
        }

        for (Cavity cavity : cavities) {
            int[][] cavityCoordinates = cavity.getCoordinates();
            for (int[] cavityCoordinate : cavityCoordinates) {
                coordinates1 = Arrays.stream(coordinates1)
                        .filter(coordinate -> coordinate[0] != cavityCoordinate[0] || coordinate[1] != cavityCoordinate[1])
                        .toArray(int[][]::new);
            }
        }
        coordinates = coordinates1;

        actionManager.registerObject(this);
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void update() {}
    public void draw(Graphics2D g2) {}

}
