package fr.lavapower.exalt.render;

import fr.lavapower.exalt.component.PositionComponent;
import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.utils.Color;
import fr.lavapower.exalt.utils.Colors;
import fr.lavapower.exalt.utils.Size;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Font {
    private Color color;
    private java.awt.Font font;
    private boolean antiAlias;
    private Texture texture;
    private String text;

    private void init(Color color, java.awt.Font font, boolean antiAlias, String text) {
        this.color = color;
        this.font = font;
        this.antiAlias = antiAlias;
        this.text = text;

        updateTexture();
    }

    public Font() { init(Colors.WHITE.get(), new java.awt.Font("Display.plain", 0, 13), true, "TESTING"); }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; updateTexture(); }
    public Font text(String text) { setText(text); return this; }

    public Size getSize() { return texture.getSize(); }

    private void updateTexture() {
        FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);
        TextLayout textLayout = new TextLayout(text, font, fontRenderContext);
        Rectangle2D r = textLayout.getBounds();
        r.add((-textLayout.getAscent() / 2), textLayout.getAscent());

        BufferedImage b = new BufferedImage((int) r.getWidth(), (int) r.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = b.createGraphics();
        g.setFont(font);
        g.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
        g.drawString(text, 0, b.getHeight() - (textLayout.getAscent()/2));

        texture = new Texture(b);
    }

    public void bind(int sampler) {
        texture.bind(sampler);
    }

    public void render(Matrix4f world, Camera camera, Shader shader, float x, float y, int scale, boolean flipX, boolean flipY, int rotation, Model model)
    {
        shader.bind();
        texture.bind(0);

        Matrix4f entityPos = new Matrix4f().translate(new Vector3f(x, y, 0));
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