package org.Game2D.engine.simulation;

import lombok.Getter;
import lombok.Setter;

public class SimulationThread implements Runnable {

    private final Thread thread = new Thread(this);

    private boolean run = false;
    private boolean compute = false;

    @Getter
    @Setter
    private int tps = 0;

    @Getter
    private int tick = 0;

    //TODO: count computed tp/s

    @Override
    public void run() {

        double updateInterval;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long startTime;
        long tickTime;

        while (run) {

            while (compute) {

                updateInterval = 1000000000 / tps;

                currentTime = System.nanoTime();
                delta += (currentTime - lastTime) / updateInterval;
                lastTime = currentTime;

                if (delta >= 1) {

                    tick++;

                    startTime = System.nanoTime();

                    //Update

                    tickTime = System.nanoTime() - startTime;

                    //Display computing time

                }
            }
        }
    }

    public void freeze() {
        compute = false;
    }

    public void resume() {
        compute = true;
    }

    public void stop() {
        run = false;
    }

    public void start(int tps) {

        this.tps = tps;

        run = true;

        thread.start();
        thread.setPriority(10);

    }

}
