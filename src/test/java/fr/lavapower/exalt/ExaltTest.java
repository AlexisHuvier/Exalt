package fr.lavapower.exalt;

import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.utils.Colors;
import fr.lavapower.exalt.utils.ControlType;
import fr.lavapower.exalt.utils.shapes.QuadShape;
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
        e.addComponent(new ShapeComponent(new QuadShape(10, 50)).color(Colors.BLUE.get()).rotation(-50));
        e.addComponent(new SpriteComponent("resources/test.png").rotation(50));
        e.addComponent(new ControlComponent(ControlType.FOURDIRECTION, 50));

        exalt.getWorld().getEntitySystem().addEntity(e);

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
