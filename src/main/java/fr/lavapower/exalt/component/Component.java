package fr.lavapower.exalt.component;

import fr.lavapower.exalt.entity.Entity;

public abstract class Component
{
    public Entity e = null;

    public String[] getIncompatibilities() {
        return new String[]{};
    }

    public String[] getDependancies() {
        return new String[]{};
    }
}
