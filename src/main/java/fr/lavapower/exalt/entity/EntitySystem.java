package fr.lavapower.exalt.entity;

import fr.lavapower.exalt.World;
import fr.lavapower.exalt.component.AnimComponent;
import fr.lavapower.exalt.component.ControlComponent;
import fr.lavapower.exalt.component.ShapeComponent;
import fr.lavapower.exalt.component.SpriteComponent;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class EntitySystem
{
    private final ArrayList<Entity> entities;
    public World world;

    public EntitySystem() {
        entities = new ArrayList<Entity>();
    }

    public ArrayList<Entity> getEntities() { return entities; }

    public Entity getEntity(int id) {
        for(Entity e: entities)
        {
            if(e.id == id)
                return e;
        }
        return null;
    }

    public boolean hasEntity(int id) {
        for(Entity e: entities) {
            if(e.id == id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEntity(Entity e) {
        if(e.id == -1)
            return false;

        for(Entity en: entities) {
            if(en == e) {
                return true;
            }
        }
        return false;
    }

    public void addEntity(Entity entity) {
        if(entity.id != -1)
            throw new IllegalStateException("Entity already have EntitySystem");

        if(entities.size() == 0)
            entity.id = 0;
        else
            entity.id = entities.get(entities.size() - 1).id + 1;
        entity.entitySystem = this;
        entities.add(entity);
    }

    public void addEntities(Entity[] entities) {
        for(Entity e: entities)
            addEntity(e);
    }

    public void update(float delta) throws IllegalComponentException
    {
        for(Entity e: entities)
            e.update(delta);
    }

    public void render(Matrix4f scale, Camera camera) throws IllegalComponentException
    {
        for(Entity e: entities)
            e.render(scale, camera);
    }
}
