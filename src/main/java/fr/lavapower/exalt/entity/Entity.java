package fr.lavapower.exalt.entity;

import fr.lavapower.exalt.component.*;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.utils.ExaltUtilities;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Arrays;

public class Entity
{
    private final ArrayList<Component> components;
    private static final ArrayList<String> graphicsComponent = new ArrayList<>(Arrays.asList("AnimComponent", "SpriteComponent", "ShapeComponent"));
    int id;
    public EntitySystem entitySystem;

    public Entity() {
        id = -1;
        entitySystem = null;
        components = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addComponent(Component component) throws IllegalComponentException
    {
        if(component.e != null)
            throw new IllegalComponentException("Component have added to Entity "+component.e.id);

        for(String depends: component.getDependancies()) {
            boolean find = false;
            for(Component comp: components)
            {
                if(ExaltUtilities.getClassName(comp).equals(depends) ||
                        (depends.equals("GraphicComponent") && graphicsComponent.contains(ExaltUtilities.getClassName(comp))))
                {
                    find = true;
                    break;
                }
            }
            if(!find)
                throw new IllegalComponentException("Entity must have "+depends+" to add "+ExaltUtilities.getClassName(component));
        }

        for(String incompatible: component.getIncompatibilities()) {
            boolean find = false;
            for(Component comp: components)
            {
                if(comp.getClass().getName().equals(incompatible) ||
                        (incompatible.equals("GraphicComponent") && graphicsComponent.contains(ExaltUtilities.getClassName(comp))))
                {
                    find = true;
                    break;
                }
            }
            if(find)
                throw new IllegalComponentException("Entity mustn't have "+incompatible+" to add "+ExaltUtilities.getClassName(component));
        }

        component.e = this;
        component.initAfterEntitySetting();
        components.add(component);
    }

    public boolean hasComponent(Component component) {
        for(Component comp : components)
        {
            if(comp == component)
                return true;
        }
        return false;
    }

    public boolean hasComponent(String name) {
        for(Component comp: components) {
            if(ExaltUtilities.getClassName(comp).equals(name))
                return true;
        }
        return false;
    }

    public Component getComponent(String name) throws IllegalComponentException
    {
        for(Component comp: components) {
            if(ExaltUtilities.getClassName(comp).equals(name))
                return comp;
        }
        throw new IllegalComponentException("Entity doesn't have a "+name+" component");
    }

    public void removeComponent(Component component) throws IllegalComponentException
    {
        if(!hasComponent(component))
            throw new IllegalComponentException("Entity doesn't have a "+ExaltUtilities.getClassName(component)+" component");
        components.remove(component);
    }

    public void removeComponent(String name) throws IllegalComponentException
    {
        if(!hasComponent(name))
            throw new IllegalComponentException("Entity doesn't have a "+name+" component");
        components.remove(getComponent(name));
    }

    public void update(float delta) throws IllegalComponentException
    {
        if(hasComponent("ControlComponent"))
            ((ControlComponent) getComponent("ControlComponent")).update(delta);
    }

    public void render(Matrix4f scale, Camera camera) throws IllegalComponentException
    {
        if(hasComponent("SpriteComponent"))
            ((SpriteComponent) getComponent("SpriteComponent")).render(scale, camera);
        if(hasComponent("AnimComponent"))
            ((AnimComponent) getComponent("AnimComponent")).render(scale, camera);
        if(hasComponent("ShapeComponent"))
            ((ShapeComponent) getComponent("ShapeComponent")).render(scale, camera);
    }

}
