package fr.lavapower.exalt.component;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.render.Shader;
import fr.lavapower.exalt.render.Texture;
import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.render.models.Quad;
import fr.lavapower.exalt.utils.Position;
import fr.lavapower.exalt.utils.Size;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SpriteComponent extends Component
{
    private final Model model = new Quad();
    private Texture texture;
    private int scale = 1;

    public SpriteComponent(String texture) {
        this.texture = new Texture(texture);
    }

    public String getTexture() { return texture.getFilename(); }
    public void setTexture(String texture) { this.texture = new Texture(texture); }
    public SpriteComponent texture(String texture) { setTexture(texture); return this; }

    @Override
    public String[] getDependancies()
    {
        return new String[] { "PositionComponent" };
    }

    public int getScale() { return scale; }
    public void setScale(int scale) { this.scale = scale;}
    public SpriteComponent scale(int scale) { setScale(scale); return this; }

    public void render(Shader shader, Matrix4f world, Camera camera) throws IllegalComponentException
    {
        PositionComponent positionComponent = (PositionComponent) e.getComponent("PositionComponent");

        shader.bind();
        texture.bind(0);

        Matrix4f entityTilePos = new Matrix4f().translate(new Vector3f(positionComponent.x, positionComponent.y, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(entityTilePos);
        target.scale(scale);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        model.render();

    }
}
