package org.game2d.engine.core.objects.logic;

public class Vec3 {
    public double x, y, z;

    public Vec3(){
        x = 0;
        y = 0;
        z = 0;
    }
    public Vec3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    // create a Vec3 and set Length to 1 if 'normalize'
    public Vec3(double x, double y, double z, boolean normalize){
        this.x = x;
        this.y = y;
        this.z = z;
        if(normalize){ setLength(1.0); }
    }
    // vector from a to b
    public Vec3(Vec3 a, Vec3 b){
        x = b.x - a.x;
        y = b.y - a.y;
        z = b.z - a.z;
    }
    // copy constructor
    public Vec3(Vec3 v){
        x = v.x;
        y = v.y;
        z = v.z;
    }
    // copy and scaling constructor
    public Vec3(Vec3 v, double scalingFactor){
        x = v.x * scalingFactor;
        y = v.y * scalingFactor;
        z = v.z * scalingFactor;
    }
    // creates a vector between a and b
    public Vec3(Vec3 a, Vec3 b, double between){
        x = a.x + between * (b.x - a.x);
        y = a.y + between * (b.y - a.y);
        z = a.z + between * (b.z - a.z);
    }
    // creates the vector based on its barycentric coordinates
    public Vec3(Vec3 a, Vec3 b, Vec3 c, Vec3 barycentric){
        x = a.x * barycentric.x;
        y = a.y * barycentric.x;
        z = a.z * barycentric.x;
        add(b, barycentric.y);
        add(c, barycentric.z);
    }

    public double getLength(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void setLength(double newLength){
        double length = getLength();
        if(length == 0) return;
        double factor = newLength / length;
        x *= factor;
        y *= factor;
        z *= factor;
    }

    public void add(Vec3 v){
        x += v.x;
        y += v.y;
        z += v.z;
    }
    public void add(Vec3 v, double factor){
        x += v.x * factor;
        y += v.y * factor;
        z += v.z * factor;
    }

    // returns new Vec3 which is the sum of this and v
    public Vec3 getAdd(Vec3 v){
        return new Vec3(x + v.x, y + v.y, z + v.z);
    }
    // returns new Vec3 which is the difference of this and v
    public Vec3 getSub(Vec3 v){
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    // has to be used on vectors that really define a direction and not a position
    public Vec3 getNormal(Vec3 v){
        return new Vec3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x, true);
    }

    public void scaleBy(double scaler){
        x *= scaler;
        y *= scaler;
        z *= scaler;
    }

    public boolean isNullVector(){ return x == 0 && y == 0 && z == 0; }
}

