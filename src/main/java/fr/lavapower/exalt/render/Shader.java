package fr.lavapower.exalt.render;

import fr.lavapower.exalt.utils.ExaltUtilities;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader
{
    private int program;
    private int vs;
    private int fs;

    public Shader(String filename) {
        program = glCreateProgram();

        vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, ExaltUtilities.readFile("resources/shaders/"+filename+".vs"));
        glCompileShader(vs);
        if(glGetShaderi(vs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(vs));
            System.exit(1);
        }

        fs = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fs, ExaltUtilities.readFile("resources/shaders/"+filename+".fs"));
        glCompileShader(fs);
        if(glGetShaderi(fs, GL_COMPILE_STATUS) != 1) {
            System.err.println(glGetShaderInfoLog(fs));
            System.exit(1);
        }

        glAttachShader(program, vs);
        glAttachShader(program, fs);

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "textures");

        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            System.err.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
    }

    protected void finalize() throws Throwable {
        glDetachShader(program, vs);
        glDetachShader(program, fs);
        glDeleteShader(vs);
        glDeleteShader(fs);
        glDeleteProgram(program);
        super.finalize();
    }

    public void setUniform(String name, int value) {
        int location = glGetUniformLocation(program, name);
        if(location != -1)
            glUniform1i(location, value);
    }

    public void setUniform(String name, Matrix4f value) {
        int location = glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if(location != -1)
            glUniformMatrix4fv(location, false, buffer);
    }

    public void setUniform(String name, float value) {
        int location = glGetUniformLocation(program, name);
        if(location != -1)
            glUniform1f(location, value);
    }

    public void bind() {
        glUseProgram(program);
    }
}
