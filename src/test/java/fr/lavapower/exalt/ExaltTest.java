package fr.lavapower.exalt;

import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.utils.Color;
import fr.lavapower.exalt.utils.Colors;
import fr.lavapower.exalt.utils.shapes.QuadShape;
import fr.lavapower.exalt.utils.shapes.SquareShape;
import junit.framework.*;

import java.util.Arrays;

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
        e.addComponent(new ShapeComponent(new QuadShape(10, 50)).color(Colors.BLUE.get()));

        exalt.getWorld().getEntitySystem().addEntity(e);

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
