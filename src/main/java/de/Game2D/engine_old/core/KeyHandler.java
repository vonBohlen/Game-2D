package de.Game2D.engine_old.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean keyPressed_W = false;
    public boolean keyPressed_A = false;
    public boolean keyPressed_S = false;
    public boolean keyPressed_D = false;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {keyPressed_W = true;}
            case KeyEvent.VK_A -> {keyPressed_A = true;}
            case KeyEvent.VK_S -> {keyPressed_S = true;}
            case KeyEvent.VK_D -> {keyPressed_D = true;}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {keyPressed_W = false;}
            case KeyEvent.VK_A -> {keyPressed_A = false;}
            case KeyEvent.VK_S -> {keyPressed_S = false;}
            case KeyEvent.VK_D -> {keyPressed_D = false;}
        }
    }

}
