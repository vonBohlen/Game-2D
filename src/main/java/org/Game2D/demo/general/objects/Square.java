/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.general.objects;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

}
