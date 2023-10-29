package de.Alienlive.games.Game2D.objects.objects;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.objects.objects.object.Cavity;
import de.Alienlive.games.Game2D.objects.objects.object.Object;

import java.awt.*;

public class TestObject extends Object {
    public TestObject(int x, int y, int height, int width, Cavity[] cavities, Instance instance) {
        super(x, y, height, width, cavities, instance);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(x, y, width, height);

    }
}
