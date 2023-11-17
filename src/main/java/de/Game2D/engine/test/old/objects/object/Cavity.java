package de.Game2D.engine.test.old.objects.object;

public class Cavity {

    private final int[][] coordinates;

    public Cavity(int x, int y, int height, int width) {
        coordinates = new int[width * height][2];

        int index = 0;

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                coordinates[index][0] = i; // x-coordinate
                coordinates[index][1] = j; // y-coordinate
                index++;
            }
        }
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

}
