package fr.lavapower.exalt;

import fr.lavapower.exalt.component.ControlComponent;
import fr.lavapower.exalt.component.PositionComponent;
import fr.lavapower.exalt.component.SpriteComponent;
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
        e.addComponent(new SpriteComponent("resources/test.png").scale(2).flipX(true).flipY(true));
        e.addComponent(new ControlComponent(ControlType.FOURDIRECTION));
        exalt.getWorld().getEntitySystem().addEntity(e);

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
