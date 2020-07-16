package fr.lavapower.exalt;

import fr.lavapower.exalt.collision.CollisionInfo;
import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Font;
import junit.framework.*;

public class ExaltTest extends TestCase
{
    private static Exalt exalt;
    private static boolean setUpIsDone = false;

    public void collision(CollisionInfo collisionInfo) {
        collisionInfo.getOtherEntity().kill();
    }

    protected void setUp() throws Exception {
        if(setUpIsDone) return;

        exalt = new Exalt();
        exalt.setDebug(true);
        exalt.center();

        Entity e = new Entity();
        e.addComponents(new PositionComponent(0, 0), new SpriteComponent("resources/test.png"), new ControlComponent(), new CollisionComponent());

        Entity e2 = new Entity();
        e2.addComponents(new PositionComponent(100, 0), new SpriteComponent("resources/test.png"),
                new CollisionComponent(collisionInfo -> collisionInfo.getMyEntity().kill()), new AutoComponent(-2, 0, 10));

        Entity e3 = new Entity();
        e3.addComponents(new PositionComponent(-100, -100), new TextComponent(new Font()));

        exalt.getWorld().getEntitySystem().addEntities(e, e2, e3);

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
