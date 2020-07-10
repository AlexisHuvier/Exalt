package fr.lavapower.exalt;

import fr.lavapower.exalt.component.AnimComponent;
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
        e.addComponent(new SpriteComponent("resources/test.png").flipX(true).flipY(true));

        Entity e1 = new Entity();
        e1.addComponent(new PositionComponent(50, 50));
        e1.addComponent(new AnimComponent(5, new String[]{"resources/anim0.png", "resources/anim1.png", "resources/anim2.png", "resources/anim3.png"}));
        e1.addComponent(new ControlComponent(ControlType.FOURDIRECTION));

        exalt.getWorld().getEntitySystem().addEntities(new Entity[]{e, e1});

        setUpIsDone = true;
    }

    public void testRun() throws IllegalComponentException
    {
        exalt.run();
    }
}
