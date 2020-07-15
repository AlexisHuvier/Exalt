package fr.lavapower.exalt.component;

import fr.lavapower.exalt.collision.AABB;
import fr.lavapower.exalt.collision.Collision;
import fr.lavapower.exalt.collision.CollisionInfo;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import org.joml.Vector2f;

import java.util.function.Consumer;

public class CollisionComponent extends Component
{
    private boolean solid;
    AABB box;
    Consumer<CollisionInfo> consumer;

    private void init(boolean solid, Consumer<CollisionInfo> consumer)
    {
        this.solid = solid;
        this.consumer = consumer;
    }

    public CollisionComponent() { init(true,null); }
    public CollisionComponent(Consumer<CollisionInfo> consumer) { init(true, consumer); }
    public CollisionComponent(boolean solid) { init(solid, null); }
    public CollisionComponent(boolean solid, Consumer<CollisionInfo> consumer) { init(solid, consumer); }

    @Override
    public String[] getDependancies()
    {
        return new String[] {"PositionComponent", "GraphicComponent"};
    }

    @Override
    public void initAfterEntitySetting() throws IllegalComponentException
    {
        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");
        if(e.hasComponent("SpriteComponent")) {
            SpriteComponent spriteComponent = (SpriteComponent) e.getComponent("SpriteComponent");
            box = new AABB(positionComponent.getPosition().toVector2f(), spriteComponent.getTextureSize().toVector2f());
        }
        else if(e.hasComponent("AnimComponent")) {
            AnimComponent animComponent = (AnimComponent) e.getComponent("AnimComponent");
            box = new AABB(positionComponent.getPosition().toVector2f(), animComponent.getTextureSize().toVector2f());
        }
        else if(e.hasComponent("ShapeComponent")) {
            ShapeComponent shapeComponent = (ShapeComponent) e.getComponent("ShapeComponent");
            box = new AABB(positionComponent.getPosition().toVector2f(), shapeComponent.getShapeSize().toVector2f());
        }

    }

    public void updateCollision(PositionComponent positionComponent) throws IllegalComponentException
    {
        box.getCenter().set(positionComponent.x, positionComponent.y);

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
            Collision data = box.getCollision(collision.box);
            if(data.isIntersecting) {
                if(isSolid() && collision.isSolid())
                {
                    box.correctPosition(collision.box, data);
                    positionComponent.x = box.getCenter().x;
                    positionComponent.y = box.getCenter().y;
                }
                if(hasCallback())
                    consumer.accept(new CollisionInfo(e, collision.e));
            }
        }
    }

    public boolean isSolid() { return solid; }
    public void setSolid(boolean solid) { this.solid = solid; }
    public CollisionComponent solid(boolean solid) { setSolid(solid); return this; }

    public boolean hasCallback() { return consumer != null;}
    public void setCallback(Consumer<CollisionInfo> consumer) { this.consumer = consumer; }
    public CollisionComponent callback(Consumer<CollisionInfo> consumer) { setCallback(consumer); return this; }
}
