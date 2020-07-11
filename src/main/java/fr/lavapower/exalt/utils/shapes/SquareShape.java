package fr.lavapower.exalt.utils.shapes;

public class SquareShape implements Shape
{
    public int size;

    public SquareShape() { size = 0; }
    public SquareShape(int size) { this.size = size; }

    @Override
    public String getType()
    {
        return "Square";
    }
}
