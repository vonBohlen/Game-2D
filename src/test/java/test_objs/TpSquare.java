package test_objs;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.ObjectConfig;
import de.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare(ObjectConfig config) {
        super(config);
    }

    public void update() {

        if (!(actionManager.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX, randomY;

        while (true) {
            randomX = random.nextInt(instance.getWidth()-hitBox.width);
            randomY = random.nextInt(instance.getHeight()-hitBox.height);

            if (setPosition(randomX, randomY) == null) break;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
