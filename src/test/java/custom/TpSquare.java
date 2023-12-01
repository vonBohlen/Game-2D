package custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(i, new Rectangle(pX, pY, pWidth, pHeight));
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

        g2.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

    }

}
