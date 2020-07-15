package fr.lavapower.exalt;

import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.utils.ControlType;
import junit.framework.*;

public class ExaltTest extends TestCase
{
    private static Exalt exalt;
    private static boolean setUpIsDone = false;

    protected void setUp() throws Exception {
        if(setUpIsDone) return;

        exalt = new Exalt();
        exalt.setDebug(true);
        exalt.center();

        Entity e = new Entity();
        e.addComponent(new PositionComponent(0, 0));
        e.addComponent(new SpriteComponent("resources/test.png"));
        e.addComponent(new ControlComponent());
        e.addComponent(new CollisionComponent(System.out::println));

        Entity e2 = new Entity();
        e2.addComponent(new PositionComponent(100, 0));
        e2.addComponent(new SpriteComponent("resources/test.png"));
        e2.addComponent(new CollisionComponent());

        exalt.getWorld().getEntitySystem().addEntities(new Entity[] {e, e2});

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
