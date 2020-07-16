package fr.lavapower.exalt.render;

import fr.lavapower.exalt.render.models.Model;
import fr.lavapower.exalt.utils.Timer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Animation
{
    private Texture[] frames;
    private int pointer;

    private double elapsedTime;
    private double currentTime;
    private double lastTime;
    private double fps;

    public Animation(int fps, String[] textures) {
        this.pointer = 0;
        this.elapsedTime = 0;
        this.currentTime = 0;
        this.lastTime = Timer.getTime();
        this.fps = 1./(double) fps;

        this.frames = new Texture[textures.length];
        for(int i = 0; i < textures.length; i++) {
            this.frames[i] = new Texture(textures[i]);
        }
    }

    public void setFps(int fps) { this.fps = 1./(double) fps;}
    public int getFps() { return (int)(1./fps); }

    public void setTextures(String[] textures) {
        this.frames = new Texture[textures.length];
        for(int i = 0; i < textures.length; i++) {
            this.frames[i] = new Texture(textures[i]);
        }
    }
    public String[] getTextures() {
        String[] textures = new String[frames.length];
        for(int i = 0; i < frames.length; i++) {
            textures[i] = frames[i].getFilename();
        }
        return textures;
    }

    public Texture getCurrentTexture() { return frames[pointer]; }

    public void bind(int sampler) {
        this.currentTime = Timer.getTime();
        this.elapsedTime += currentTime - lastTime;
        lastTime = currentTime;

        if(this.elapsedTime >=  fps) {
            elapsedTime -= fps;
            pointer ++;
        }

        if(pointer >= frames.length) pointer = 0;

        frames[pointer].bind(sampler);
    }

    public void render(Matrix4f world, Camera camera, Shader shader, float x, float y, int scale, boolean flipX, boolean flipY, int rotation, Model model)
    {
        shader.bind();
        bind(0);

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

    public void delete() {
        for(Texture texture: frames)
            texture.delete();
    }

}
