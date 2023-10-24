package de.Alienlive.games.Game2D.entities.entity;

import de.Alienlive.games.Game2D.entities.entity.Edge;

import java.awt.*;

public class HitBox {
    public Edge TopLeft, TopRight, DownLeft, DownRight;

    public HitBox(Edge tl, Edge tr, Edge dl, Edge dr){
        this.TopLeft = tl;
        this.TopRight = tr;
        this.DownLeft = dl;
        this.DownRight = dr;
    }

    public HitBox(){}
}
