package org.Game2D.demo.schnake.logic;

import org.Game2D.engine.objects.GameObject;

public class Cell {
    private final int posX;
    private final int posY;
    private GameObject go;
    private CellType cellType;

    public Cell(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public GameObject getGo() {
        return this.go;
    }

    public void setGo(GameObject go) {
        this.go = go;
    }
}
