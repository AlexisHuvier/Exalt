package fr.lavapower.exalt.utils.shapes;

import fr.lavapower.exalt.utils.Size;

public class QuadShape implements Shape
{
    public int width;
    public int height;

    public QuadShape() { width = 0; height = 0; }
    public QuadShape(Size size) { width = (int)size.width; height = (int)size.height; }
    public QuadShape(int width, int height) { this.width = width; this.height = height; }

    @Override
    public String getType()
    {
        return "Quad";
    }
}
