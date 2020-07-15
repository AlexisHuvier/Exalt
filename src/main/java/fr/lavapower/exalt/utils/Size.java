package fr.lavapower.exalt.utils;

import org.joml.Vector2f;

public class Size
{
    public float width;
    public float height;

    public Size(float width, float height)
    {
        this.width = width;
        this.height = height;
    }

    public Size() {
        this.width = 0;
        this.height = 0;
    }

    public Vector2f toVector2f() { return new Vector2f(width, height); }

    @Override
    public boolean equals(Object o)
    {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        Size size = (Size)o;
        return width == size.width && height == size.height;
    }

    @Override
    public String toString()
    {
        return "Size{" + "width=" + width + ", height=" + height + '}';
    }
}
