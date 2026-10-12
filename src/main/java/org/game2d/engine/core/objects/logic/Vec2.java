package org.game2d.engine.core.objects.logic;

public class Vec2 {
    public double x, y;

    public Vec2(){
        x = 0;
        y = 0;
    }
    public Vec2(double x, double y){
        this.x = x;
        this.y = y;
    }
    public Vec2(int x, int y){
        this.x = x;
        this.y = y;
    }
    // copy constructor
    public Vec2(Vec2 v){
        x = v.x;
        y =v.y;
    }
    // copy constructor
    public Vec2(Vec3 v){
        x = v.x;
        y =v.y;
    }
    // vector from a to b
    public Vec2(Vec2 a, Vec2 b){
        x = b.x - a.x;
        y = b.y - a.y;
    }
    // vector from a to b
    public Vec2(Vec3 a, Vec3 b){
        x = b.x - a.x;
        y = b.y - a.y;
    }
    // creates the vector based on its barycentric coordinates
    public Vec2(Vec2 a, Vec2 b, Vec2 c, Vec3 barycentric){
        x = a.x * barycentric.x;
        y = a.y * barycentric.x;
        add(b, barycentric.y);
        add(c, barycentric.z);
    }
    // creates a point on the line from a to b
    public Vec2(Vec2 a, Vec2 b, double between){
        x = a.x + between * (b.x - a.x);
        y = a.y + between * (b.y - a.y);
    }

    public void add(Vec2 v, double factor){
        x += v.x * factor;
        y += v.y * factor;
    }

    public boolean isNullVector(){ return x == 0 && y == 0; }
}

