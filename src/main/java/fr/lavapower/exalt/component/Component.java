package fr.lavapower.exalt.component;

import fr.lavapower.exalt.entity.Entity;
import fr.lavapower.exalt.exceptions.IllegalComponentException;

public abstract class Component
{
    public Entity e = null;

    public String[] getIncompatibilities() {
        return new String[]{};
    }

    public String[] getDependancies() {
        return new String[]{};
    }

    public void initAfterEntitySetting() throws IllegalComponentException
    {}
}
