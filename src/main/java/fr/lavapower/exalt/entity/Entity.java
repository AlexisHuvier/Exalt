package fr.lavapower.exalt.entity;

import fr.lavapower.exalt.component.Component;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.utils.ExaltUtilities;

import java.util.ArrayList;

public class Entity
{
    private final ArrayList<Component> components;
    int id;
    public EntitySystem entitySystem;

    public Entity() {
        id = -1;
        entitySystem = null;
        components = new ArrayList<Component>();
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
                if(ExaltUtilities.getClassName(comp).equals(depends))
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
                if(comp.getClass().getName().equals(incompatible))
                {
                    find = true;
                    break;
                }
            }
            if(find)
                throw new IllegalComponentException("Entity mustn't have "+incompatible+" to add "+ExaltUtilities.getClassName(component));
        }

        component.e = this;
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

}
