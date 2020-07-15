package fr.lavapower.exalt.component;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.render.Shader;
import fr.lavapower.exalt.render.Texture;
import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.render.models.QuadModel;
import fr.lavapower.exalt.utils.Size;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SpriteComponent extends Component
{
    private Model model;
    private Texture texture;
    private int scale = 1;
    private boolean visible = true;
    private boolean flipX = false;
    private boolean flipY = false;
    private int rotation = 0;
    private final Shader shader = new Shader("texture");

    public SpriteComponent(String texture) {
        this.texture = new Texture(texture);
        model = new QuadModel(this.texture.getWidth(), this.texture.getHeight());
    }

    @Override
    public String[] getDependancies()
    {
        return new String[] { "PositionComponent" };
    }

    @Override
    public String[] getIncompatibilities() { return new String[] { "AnimComponent", "ShapeComponent" }; }

    public String getTexture() { return texture.getFilename(); }
    public void setTexture(String texture) {
        this.texture = new Texture(texture);
        model = new QuadModel(this.texture.getWidth(), this.texture.getHeight());
    }
    public SpriteComponent texture(String texture) { setTexture(texture); return this; }

    public Size getTextureSize() { return texture.getSize(); }

    public int getScale() { return scale; }
    public void setScale(int scale) { this.scale = scale;}
    public SpriteComponent scale(int scale) { setScale(scale); return this; }

    public int getRotation() { return rotation; }
    public void setRotation(int rotation) { this.rotation = rotation; }
    public SpriteComponent rotation(int rotation) { setRotation(rotation); return this; }

    public boolean isVisible() { return visible; }
    public void setVisible(boolean visible) { this.visible = visible; }
    public SpriteComponent visible(boolean visible) { setVisible(visible); return this; }

    public boolean isFlipX() { return flipX; }
    public void setFlipX(boolean flipX) { this.flipX = flipX; }
    public SpriteComponent flipX(boolean flipX) { setFlipX(flipX); return this; }

    public boolean isFlipY() { return flipY; }
    public void setFlipY(boolean flipY) { this.flipY = flipY; }
    public SpriteComponent flipY(boolean flipY) { setFlipY(flipY); return this; }

    public void render(Matrix4f world, Camera camera) throws IllegalComponentException
    {
        if(!visible)
            return;

        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");

        shader.bind();
        texture.bind(0);

        Matrix4f entityPos = new Matrix4f().translate(new Vector3f(positionComponent.x, positionComponent.y, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(entityPos);
        target.scale(scale);

        if(flipX)
            target.scale(-1, 1, 1);
        if(flipY)
            target.scale(1, -1, 1);

        target.rotate(-(float)(rotation * Math.PI / 180), new Vector3f(0, 0, 1));

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        model.render();

    }
}
