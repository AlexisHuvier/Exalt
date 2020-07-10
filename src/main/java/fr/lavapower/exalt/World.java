package fr.lavapower.exalt;

import fr.lavapower.exalt.entity.EntitySystem;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.render.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class World
{
    private EntitySystem entitySystem;
    private Matrix4f world;
    public Exalt exalt;

    public World() {
        entitySystem = new EntitySystem();
        entitySystem.world = this;
        world = new Matrix4f().setTranslation(new Vector3f(0));
    }

    public EntitySystem getEntitySystem() {
        return entitySystem;
    }

    public void update(float delta) throws IllegalComponentException
    {
        entitySystem.update(delta);
    }

    public void render(Shader shader, Camera camera) throws IllegalComponentException
    {
        entitySystem.render(shader, world , camera);
    }
}
