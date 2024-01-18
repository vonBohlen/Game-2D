package test_objs;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.ObjectConfig;
import de.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(ObjectConfig config) {
        super(config);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

    }

}
