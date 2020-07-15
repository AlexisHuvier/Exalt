package fr.lavapower.exalt.component;

import fr.lavapower.exalt.collision.AABB;
import fr.lavapower.exalt.collision.CollisionInfo;
import fr.lavapower.exalt.exceptions.IllegalComponentException;

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

    public boolean isSolid() { return solid; }
    public void setSolid(boolean solid) { this.solid = solid; }
    public CollisionComponent solid(boolean solid) { setSolid(solid); return this; }

    public boolean hasCallback() { return consumer != null;}
    public void setCallback(Consumer<CollisionInfo> consumer) { this.consumer = consumer; }
    public CollisionComponent callback(Consumer<CollisionInfo> consumer) { setCallback(consumer); return this; }
}
