package fr.lavapower.exalt.component;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.render.Shader;
import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.render.models.QuadModel;
import fr.lavapower.exalt.render.models.SquareModel;
import fr.lavapower.exalt.utils.Color;
import fr.lavapower.exalt.utils.Colors;
import fr.lavapower.exalt.utils.shapes.QuadShape;
import fr.lavapower.exalt.utils.shapes.Shape;
import fr.lavapower.exalt.utils.shapes.SquareShape;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ShapeComponent extends Component
{
    private Model model;
    private Color color;
    private int scale = 1;
    private boolean visible = true;
    private boolean flipX = false;
    private boolean flipY = false;
    private int rotation = 0;
    private final Shader shader = new Shader("color");

    public ShapeComponent(Shape shape) {
        color = Colors.WHITE.get();
        switch(shape.getType()) {
            case "Quad":
                QuadShape quadShape = (QuadShape) shape;
                model = new QuadModel(quadShape.width, quadShape.height);
                break;
            case "Square":
                SquareShape squareShape = (SquareShape) shape;
                model = new SquareModel(squareShape.size);
                break;
            default:
                throw new IllegalArgumentException("Unknown Shape : "+shape.getType());
        }
    }

    @Override
    public String[] getDependancies()
    {
        return new String[] { "PositionComponent" };
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
    public ShapeComponent color(Color color) { setColor(color); return this; }

    public void setShape(Shape shape) {
        switch(shape.getType()) {
            case "Quad":
                QuadShape quadShape = (QuadShape) shape;
                model = new QuadModel(quadShape.width, quadShape.height);
                break;
            case "Square":
                SquareShape squareShape = (SquareShape) shape;
                model = new SquareModel(squareShape.size);
                break;
            default:
                throw new IllegalArgumentException("Unknown Shape : "+shape.getType());
        }
    }
    public ShapeComponent shape(Shape shape) { setShape(shape); return this; }

    public int getScale() { return scale; }
    public void setScale(int scale) { this.scale = scale;}
    public ShapeComponent scale(int scale) { setScale(scale); return this; }

    public int getRotation() { return rotation; }
    public void setRotation(int rotation) { this.rotation = rotation; }
    public ShapeComponent rotation(int rotation) { setRotation(rotation); return this; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public ShapeComponent visible(boolean visible) { setVisible(visible); return this; }

    public boolean isFlipX() { return flipX; }
    public void setFlipX(boolean flipX) { this.flipX = flipX; }
    public ShapeComponent flipX(boolean flipX) { setFlipX(flipX); return this; }

    public boolean isFlipY() { return flipY; }
    public void setFlipY(boolean flipY) { this.flipY = flipY; }
    public ShapeComponent flipY(boolean flipY) { setFlipY(flipY); return this; }

    public void render(Matrix4f world, Camera camera) throws IllegalComponentException
    {
        if(!visible)
            return;

        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");

        shader.bind();

        Matrix4f entityPos = new Matrix4f().translate(new Vector3f(positionComponent.x, positionComponent.y, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(entityPos);
        target.scale(scale);

        if(flipX)
            target.scale(-1, 1, 1);
        if(flipY)
            target.scale(1, -1, 1);

        target.rotate(-(float)(rotation * Math.PI / 180), new Vector3f(0, 0, 1));

        shader.setUniform("red", color.getFloatRed());
        shader.setUniform("green", color.getFloatGreen());
        shader.setUniform("blue", color.getFloatBlue());
        shader.setUniform("alpha", color.getFloatAlpha());
        shader.setUniform("projection", target);

        model.render();

    }
}
