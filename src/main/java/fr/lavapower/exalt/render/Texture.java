package fr.lavapower.exalt.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import fr.lavapower.exalt.utils.Size;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture
{
    private int id;
    private String filename;
    private int width;
    private int height;

    public Texture(String filename) {
        this.filename = filename;
        BufferedImage bi;
        try {
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();

            int[] pixels_raw = bi.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for(int i = 0; i < width; i++)
            {
                for(int j = 0; j < height; j++)
                {
                    int pixel = pixels_raw[i*width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
                    pixels.put((byte) (pixel & 0xFF)); // BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                }
            }

            pixels.flip();

            id = glGenTextures();

            glBindTexture(GL_TEXTURE_2D, id);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilename() { return filename; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Size getSize() { return new Size(width, height); }

    protected void finalize() throws Throwable
    {
        glDeleteTextures(id);
        super.finalize();
    }

    public void bind(int sampler) {
        if(sampler >= 0 && sampler <= 31)
        {
            glActiveTexture(GL_TEXTURE0 + sampler);
            glBindTexture(GL_TEXTURE_2D, id);
        }
    }
}
