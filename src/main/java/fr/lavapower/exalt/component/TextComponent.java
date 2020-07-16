package fr.lavapower.exalt.component;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.render.Font;
import fr.lavapower.exalt.render.Shader;
import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.render.models.QuadModel;
import fr.lavapower.exalt.utils.Size;
import org.joml.Matrix4f;

public class TextComponent extends Component
{
    private Model model;
    private Font font;
    private int scale = 1;
    private boolean visible = true;
    private boolean flipX = false;
    private boolean flipY = false;
    private int rotation = 0;
    private final Shader shader = new Shader("texture");

    public TextComponent(Font font) {
        this.font = font;
        Size size = font.getSize();
        model = new QuadModel((int)size.width, (int)size.height);
    }

    @Override
    public String[] getDependancies()
    {
        return new String[] { "PositionComponent" };
    }

    @Override
    public String[] getIncompatibilities() { return new String[] { "AnimComponent", "ShapeComponent", "SpriteComponent" }; }

    public Font getFont() { return font; }
    public void setFont(Font font) {
        this.font = font;
        Size size = font.getSize();
        model = new QuadModel((int)size.width, (int)size.height);
    }
    public TextComponent font(Font font) { setFont(font); return this; }

    public Size getFontSize() { return font.getSize(); }

    public int getScale() { return scale; }
    public void setScale(int scale) { this.scale = scale;}
    public TextComponent scale(int scale) { setScale(scale); return this; }

    public int getRotation() { return rotation; }
    public void setRotation(int rotation) { this.rotation = rotation; }
    public TextComponent rotation(int rotation) { setRotation(rotation); return this; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public TextComponent visible(boolean visible) { setVisible(visible); return this; }

    public boolean isFlipX() { return flipX; }
    public void setFlipX(boolean flipX) { this.flipX = flipX; }
    public TextComponent flipX(boolean flipX) { setFlipX(flipX); return this; }

    public boolean isFlipY() { return flipY; }
    public void setFlipY(boolean flipY) { this.flipY = flipY; }
    public TextComponent flipY(boolean flipY) { setFlipY(flipY); return this; }

    public void render(Matrix4f world, Camera camera) throws IllegalComponentException
    {
        if(!visible)
            return;

        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");

        font.render(world, camera, shader, positionComponent.x, positionComponent.y, scale, flipX, flipY, rotation, model);

    }
}
