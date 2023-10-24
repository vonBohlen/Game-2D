package de.Alienlive.games.Game2D.entities.entity;

import java.util.List;

public class CollisionManager {

    public List<Entity> RenderedObjects;

    public CollisionManager(){}

    public void AddEntity(Entity newObject){
        RenderedObjects.add(newObject);
    }


}
