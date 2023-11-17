package de.Game2D.engine.test.old;

public class ActionManagerOld {


    /*private final List<Entity> entities = new ArrayList<>();
    private final List<Object> objects = new ArrayList<>();

    private int gameTick = 0;

    private boolean updateUI = false;

    Thread actionThread;

    private final Instance instance;

    public ActionManagerOld(Instance i) {
        instance = i;
    }

    protected void startActionThread() {
        actionThread = new Thread(this);
        actionThread.start();
    }

    @Override
    public void run() {
        while (actionThread != null) {
            int tps = Integer.parseInt(PropertiesManager.getSettings().getProperty("game2d.core.tps"));
            double drawInterval = 1000000000 / tps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            while (actionThread != null) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {
                    gameTick++;
                    if (!updateUI) update();
                    else updateUI();
                    delta--;
                    drawCount++;
                }

                if (timer >= 1000000000) {
                    instance.getDebugDisplay().updateTPS(drawCount);
                    drawCount = 0;
                    timer = 0;
                }

                if (gameTick >= tps) gameTick = 0;
            }
        }
    }

    private void update() {
        for (Entity ce : entities) {
            ce.update();
        }
        for (Object o : objects) {
            o.update();
        }
    }

    public Entity checkCollisionForEntity(Entity e, Rectangle r) {
        for (Entity current : entities) {
            if (!current.equals(e) && r.intersects(current.box)) return current;
        }
        return null;
    }

    public Object checkCollisionForObject(Rectangle r) {
        for (Object object : objects) {
            int[][] objectCoordinates = object.getCoordinates();
            for (int i = 0; i < objectCoordinates.length; i++) {
                int objectX = objectCoordinates[i][0];
                int objectY = objectCoordinates[i][1];
                if (r.contains(objectX, objectY)) {
                    return object;
                }
            }
        }
        return null;
    }

    private void updateUI() {

    }

    public void registerEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.removeIf(entity -> entity == e);
    }

    public List<Entity> getEntities() {
        return entities;
    }


    public void registerObject(Object o) {
        objects.add(o);
    }

    public void removeObject(Object o) {
        objects.removeIf(object -> object == o);
    }

    public List<Object> getObjects() {
        return objects;
    }


    public int getGameTick() {
        return gameTick;
    }*/

}
