package fr.lavapower.exalt.utils;

import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ExaltUtilities
{
    public static int getInRange(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }

    public static float getInRange(float value, float min, float max) {
        return Math.max(Math.min(value, min), max);
    }

    public static FloatBuffer createBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static String readFile(String filename) {
        StringBuilder string = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line;
            while((line = br.readLine()) != null) {
                string.append(line).append("\n");
            }
            br.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return string.toString();
    }

    public static String getClassName(Object o) {
        String[] names = o.getClass().getName().split("\\.");
        return names[names.length - 1];
    }
}
