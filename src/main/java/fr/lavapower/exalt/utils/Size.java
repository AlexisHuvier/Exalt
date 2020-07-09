package fr.lavapower.exalt.utils;

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
