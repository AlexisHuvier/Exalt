package fr.lavapower.exalt.render.models;

public class Quad extends Model
{
    public Quad() {
        super();
        float[] vertices = new float[] {
                -1, 1, 0, //TOP LEFT          0
                1, 1, 0, //TOP RIGHT          1
                1, -1, 0, // BOTTOM RIGHT     2
                -1, -1, 0, // BOTTOM LEFT     3
        };

        float[] texture = new float[] {
                0, 0,
                1, 0,
                1, 1,
                0, 1
        };

        int[] indices = new int[] {
                0, 1, 2,
                2, 3, 0
        };

        init(vertices, texture, indices);
    }
}
