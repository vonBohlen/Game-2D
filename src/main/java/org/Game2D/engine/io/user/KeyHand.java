/**
 * /engine/io/user/KeyHand.java
 *
 * Manages received keystrokes and sets the appropriate <code>keyPressed_*</code> boolean
 *
 * Copyright (C) 2026 Christian von Bohlen, J. K.
 */

package org.Game2D.engine.io.user;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Key-input Handler
 * Manages received keystrokes and sets the appropriate <code>keyPressed_*</code> boolean
 */
public class KeyHand implements KeyListener {

    public boolean keyPressed_W = false;
    public boolean keyPressed_A = false;
    public boolean keyPressed_S = false;
    public boolean keyPressed_D = false;
    public boolean keyPressed_SPACE = false;
    public boolean keyPressed_ESC = false;

    private final ThreadLocal<StringBuilder> stringBuilderCache = ThreadLocal.withInitial(() -> new StringBuilder(64));

    // Ignore the typed key since we are only interested
    // in which keys are currently pressed, not which character

    /**
     * @hidden
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> keyPressed_W = true;
            case KeyEvent.VK_A -> keyPressed_A = true;
            case KeyEvent.VK_S -> keyPressed_S = true;
            case KeyEvent.VK_D -> keyPressed_D = true;
            case KeyEvent.VK_SPACE -> keyPressed_SPACE = true;
            case KeyEvent.VK_ESCAPE -> keyPressed_ESC = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> keyPressed_W = false;
            case KeyEvent.VK_A -> keyPressed_A = false;
            case KeyEvent.VK_S -> keyPressed_S = false;
            case KeyEvent.VK_D -> keyPressed_D = false;
            case KeyEvent.VK_SPACE -> keyPressed_SPACE = false;
            case KeyEvent.VK_ESCAPE -> keyPressed_ESC = false;
        }
    }

    public String getPressedKeysAsString() {
        StringBuilder sb = stringBuilderCache.get();
        sb.setLength(0);
        if (keyPressed_A) sb.append(" A");
        if (keyPressed_D) sb.append( " D");
        if (keyPressed_S) sb.append(" S");
        if (keyPressed_W) sb.append(" W");
        if (keyPressed_SPACE) sb.append(" SPACE");
        if (keyPressed_ESC) sb.append(" ESC");
        sb.replace(0, 1, "");
        return sb.toString();
    }


}
