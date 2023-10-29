package de.Alienlive.games.Game2D.objects.objects;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.objects.objects.object.Cavity;
import de.Alienlive.games.Game2D.objects.objects.object.Object;

public class Border extends Object {
    public Border(int x, int y, int height, int width, Cavity[] cavities, Instance instance) {
        super(x, y, height, width, cavities, instance);
    }
}
