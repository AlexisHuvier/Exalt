package fr.lavapower.exalt.component;

import fr.lavapower.exalt.utils.Position;

public class PositionComponent extends Component
{
    public float x;
    public float y;

    private void init(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PositionComponent() { init(0, 0); }
    public PositionComponent(Position pos) { init(pos.x, pos.y); }
    public PositionComponent(float x, float y) { init(x, y); }

    public Position getPosition() { return new Position(x, y); }

    public void setPosition(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    @Override
    public String toString()
    {
        return "PositionComponent{" + "x=" + x + ", y=" + y + '}';
    }
}
