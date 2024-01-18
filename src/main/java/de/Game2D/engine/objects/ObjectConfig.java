package de.Game2D.engine.objects;

import de.Game2D.engine.core.Instance;

public class ObjectConfig {

    private final Instance instance;

    public int hitBoxWith = 48;
    public int hitBoxHeight = 48;

    public int positionX = 0;
    public int positionY = 0;

    public int positionLayer = 0;

    public boolean collision = false;

    public ObjectConfig(Instance i) {
        instance = i;
    }

    public Instance getInstance() {
        return instance;
    }

}
