package fr.lavapower.exalt;

import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import junit.framework.*;

public class ExaltTest extends TestCase
{
    private static Exalt exalt;
    private static boolean setUpIsDone = false;

    protected void setUp() throws Exception {
        if(setUpIsDone) return;

        exalt = new Exalt().debug(true).center();

        System.out.println(exalt.getClipboard());
        exalt.setClipboard("TESTING");

        Entity e = new Entity();
        e.addComponents(new PositionComponent(0, 0), new SpriteComponent("resources/test.png"), new ControlComponent(), new CollisionComponent());

        Entity e2 = new Entity();
        e2.addComponents(new PositionComponent(100, 0), new SpriteComponent("resources/test.png"),
                new CollisionComponent(collisionInfo -> {
                    try
                    {
                        collisionInfo.getMyEntity().delete();
                    }
                    catch(IllegalComponentException illegalComponentException)
                    {
                        illegalComponentException.printStackTrace();
                    }
                }), new AutoComponent(-2, 0, 10));

        exalt.getWorld().getEntitySystem().addEntities(e, e2);

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
