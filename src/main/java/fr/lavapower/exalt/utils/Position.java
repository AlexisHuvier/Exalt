package fr.lavapower.exalt.utils;

import org.joml.Vector3f;

public class Position
{
    public float x;
    public float y;

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Position(float x, float y) { init(x, y); }
    public Position() { init(0, 0);}

    public Vector3f toVector3f() {
        return new Vector3f(x, y, 0);
    }

    public Vector3f toVector3f(int z) {
        return new Vector3f(x, y, z);
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        Position position = (Position)o;
        return x == position.x && y == position.y;
    }

    @Override
    public String toString()
    {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
