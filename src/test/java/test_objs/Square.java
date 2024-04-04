package test_objs;

import de.Game2D.engine_old.objects.ObjectConfig;
import de.Game2D.engine_old.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(ObjectConfig config) {
        super(config);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
