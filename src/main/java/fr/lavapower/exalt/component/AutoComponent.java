package fr.lavapower.exalt.component;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.utils.Position;
import org.joml.Vector2f;

public class AutoComponent extends Component
{
    private Vector2f movement;
    private int rotation;

    private void init(Vector2f movement, int rotation) {
        this.movement = movement;
        this.rotation = rotation;
    }

    public AutoComponent() { init(new Vector2f(0, 0), 0); }
    public AutoComponent(int rotation) { init(new Vector2f(0, 0), rotation); }
    public AutoComponent(int x, int y) { init(new Vector2f(x, y), 0); }
    public AutoComponent(int x, int y, int rotation) { init(new Vector2f(x, y), rotation); }

    @Override
    public String[] getDependancies()
    {
        return new String[] { "PositionComponent", "GraphicComponent" };
    }

    public Position getMouvement() {
        if(movement != null)
            return new Position(movement.x, movement.y);
        else
            return null;
    }
    public void setMovement(int x, int y) { movement = new Vector2f(x, y); }
    public AutoComponent movement(int x, int y) { setMovement(x, y); return this;}

    public int getRotation() { return rotation; }
    public void setRotation(int rotation) { this.rotation = rotation; }
    public AutoComponent rotation(int rotation) { setRotation(rotation); return this; }

    public void update(float delta) throws IllegalComponentException
    {
        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");
        positionComponent.x += movement.x;
        positionComponent.y += movement.y;

        if(e.hasComponent("SpriteComponent")) {
            SpriteComponent spriteComponent = (SpriteComponent) e.getComponent("SpriteComponent");
            int newRotation = spriteComponent.getRotation() + rotation;
            if(newRotation > 360)
                spriteComponent.setRotation(newRotation - 360);
            else
                spriteComponent.setRotation(newRotation);
        }
        else if(e.hasComponent("AnimComponent")) {
            AnimComponent animComponent = (AnimComponent) e.getComponent("AnimComponent");
            int newRotation = animComponent.getRotation() + rotation;
            if(newRotation > 360)
                animComponent.setRotation(newRotation - 360);
            else
                animComponent.setRotation(newRotation);
        }
        else if(e.hasComponent("ShapeComponent")) {
            ShapeComponent shapeComponent = (ShapeComponent) e.getComponent("ShapeComponent");
            int newRotation = shapeComponent.getRotation() + rotation;
            if(newRotation > 360)
                shapeComponent.setRotation(newRotation - 360);
            else
                shapeComponent.setRotation(newRotation);
        }

        if(e.hasComponent("CollisionComponent")) {
            ((CollisionComponent) e.getComponent("CollisionComponent")).updateCollision(positionComponent);
        }
    }
}
