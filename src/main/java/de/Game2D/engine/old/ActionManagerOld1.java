package de.Game2D.engine.old;

public class ActionManagerOld1 {

/*
    private final List<Entity> entities = new ArrayList<>();

    Thread actionThread;

    int tps = 60;

    public void startActionThread() {
        actionThread = new Thread(this);
        actionThread.start();
    }

    @Override
    public void run() {
        while (actionThread != null) {

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
                    update();
                    delta--;
                    drawCount++;
                }

                if (timer >= 1000000000) {
                    Main.getGamePanel().debugDisplay.updateTPS(drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }
    }

    private void update() {
        for (Entity ce : entities) {
            ce.update();
        }
    }

    public void registerEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.removeIf(entity -> entity == e);
    }

    public List<Entity> getEntities() {
        return entities;
    }*/

}
