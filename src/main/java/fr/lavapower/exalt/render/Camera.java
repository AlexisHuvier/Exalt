package fr.lavapower.exalt.render;

import fr.lavapower.exalt.utils.Position;
import fr.lavapower.exalt.utils.Size;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera
{
    private Vector3f position;
    private Matrix4f projection;
    private float width;
    private float height;

    public Camera(float width, float height)
    {
        position = new Vector3f(0, 0, 0);
        setProjection(width, height);
        this.width = width;
        this.height = height;
    }

    public Size getSize() { return new Size(width, height); }

    public void setPosition(Position position) { this.position = position.toVector3f(); }
    public void addPosition(Vector3f position) { this.position.add(position); }
    public Position getPosition() { return new Position((int)position.x, (int)position.y); }

    public void setProjection(float width, float height) {
        projection = new Matrix4f().setOrtho2D(-width/2f, width/2f, -height/2f, height/2f);
        this.width = width;
        this.height = height;
    }
    public void setProjection(Size size) { setProjection(size.width, size.height); }
    public Matrix4f getUntransformedProjection() { return projection; }
    public Matrix4f getProjection() { return projection.translate(position, new Matrix4f()); }
}
