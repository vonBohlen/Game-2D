package org.Game2D.engine.utils;

import java.util.ArrayList;

public class SpinLock {

    private volatile boolean lockAcquired = false;

    private final ArrayList<Long> queue = new ArrayList<>();

    private long current = 0;


    public void acquirerLock(long id) {
        queue.add(id);
        while (lockAcquired && queue.get(0) == id) {
            Thread.onSpinWait();
        }
        current = queue.get(0);
        queue.remove(0);
        lockAcquired = true;
    }

    public void dropLock(long id) {
        if (id == current) lockAcquired = false;
    }

}
