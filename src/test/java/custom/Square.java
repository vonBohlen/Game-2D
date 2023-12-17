package custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(i, new Rectangle(pX, pY, pWidth, pHeight), true);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

    }

}
