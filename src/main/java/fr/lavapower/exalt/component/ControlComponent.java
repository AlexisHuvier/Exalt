package fr.lavapower.exalt.component;

import fr.lavapower.exalt.collision.AABB;
import fr.lavapower.exalt.collision.Collision;
import fr.lavapower.exalt.collision.CollisionInfo;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.input.Key;
import fr.lavapower.exalt.utils.ControlType;
import org.joml.Vector2f;

import java.util.HashMap;

public class ControlComponent extends Component
{
    private ControlType controlType;
    private int speed;
    private HashMap<String, Key> keys;

    private void init(ControlType controlType, int speed) {
        this.controlType = controlType;
        this.speed = speed;
        keys = new HashMap<>();
        keys.put("left", Key.LEFT);
        keys.put("right", Key.RIGHT);
        keys.put("up", Key.UP);
        keys.put("down", Key.DOWN);
    }

    public ControlComponent() { init(ControlType.FOURDIRECTION, 7); }
    public ControlComponent(ControlType controlType) { init(controlType, 7); }
    public ControlComponent(ControlType controlType, int speed) { init(controlType, speed); }

    public ControlType getControlType() { return controlType; }
    public void setControlType(ControlType controlType) { this.controlType = controlType; }
    public ControlComponent controlType(ControlType controlType) { setControlType(controlType); return this; }

    public int getSpeed() { return speed; }
    public void setSpeed(int speed) { this.speed = speed; }
    public ControlComponent speed(int speed) { setSpeed(speed); return this; }

    public Key getKey(String name) {
        if(keys.containsKey(name))
            return keys.get(name);
        return null;
    }
    public void setKey(String name, Key key) {
        if(keys.containsKey(name))
            keys.replace(name, key);
    }
    public ControlComponent key(String name, Key key) { setKey(name, key); return this; }

    @Override
    public String[] getDependancies()
    {
        return new String[] {"PositionComponent"};
    }

    public void update(float delta) throws IllegalComponentException
    {
        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");
        if(e.entitySystem.world.exalt.getInput().isKeyDown(keys.get("left"))) {
            if(controlType == ControlType.FOURDIRECTION || controlType == ControlType.LEFTRIGHT)
                positionComponent.x -= speed*delta;
        }
        if(e.entitySystem.world.exalt.getInput().isKeyDown(keys.get("right"))) {
            if(controlType == ControlType.FOURDIRECTION || controlType == ControlType.LEFTRIGHT)
                positionComponent.x += speed*delta;
        }
        if(e.entitySystem.world.exalt.getInput().isKeyDown(keys.get("up"))) {
            if(controlType == ControlType.FOURDIRECTION || controlType == ControlType.UPDOWN)
                positionComponent.y += speed*delta;

        }
        if(e.entitySystem.world.exalt.getInput().isKeyDown(keys.get("down"))) {
            if(controlType == ControlType.FOURDIRECTION || controlType == ControlType.UPDOWN)
                positionComponent.y -= speed*delta;



        if(e.hasComponent("CollisionComponent")) {
            CollisionComponent collisionComponent = ((CollisionComponent) e.getComponent("CollisionComponent"));
            collisionComponent.box.getCenter().set(positionComponent.x, positionComponent.y);

            CollisionComponent collision = null;
            for(Entity entity: e.entitySystem.getEntities())
            {
                if(entity.getId() != e.getId() && entity.hasComponent("CollisionComponent"))
                {
                    CollisionComponent collision1 = ((CollisionComponent) entity.getComponent("CollisionComponent"));
                    if(collision == null) { collision = collision1; }
                    Vector2f length1 = collision.box.getCenter().sub(positionComponent.x, positionComponent.y, new Vector2f());
                    Vector2f length2 = collision1.box.getCenter().sub(positionComponent.x, positionComponent.y, new Vector2f());
                    if(length1.lengthSquared() > length2.lengthSquared()) { collision = collision1; }
                }
            }

            if(collision != null) {
                Collision data = collisionComponent.box.getCollision(collision.box);
                if(data.isIntersecting) {
                    if(collisionComponent.isSolid() && collision.isSolid())
                    {
                        collisionComponent.box.correctPosition(collision.box, data);
                        positionComponent.x = collisionComponent.box.getCenter().x;
                        positionComponent.y = collisionComponent.box.getCenter().y;
                    }
                    if(collisionComponent.hasCallback())
                        collisionComponent.consumer.accept(new CollisionInfo(e, collision.e));
                }
            }
        }
    }
}
