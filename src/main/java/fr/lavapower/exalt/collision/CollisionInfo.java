package fr.lavapower.exalt.collision;

import fr.lavapower.exalt.entity.Entity;

public class CollisionInfo
{
    private Entity myEntity;
    private Entity otherEntity;

    public CollisionInfo(Entity myEntity, Entity otherEntity) {
        this.myEntity = myEntity;
        this.otherEntity = otherEntity;
    }

    public Entity getMyEntity() { return myEntity; }
    public Entity getOtherEntity() { return otherEntity; }

    @Override
    public String toString()
    {
        return "CollisionInfo{" + "myEntity=" + myEntity.getId() + ", otherEntity=" + otherEntity.getId() + '}';
    }
}
