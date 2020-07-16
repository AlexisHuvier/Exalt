package fr.lavapower.exalt;

import fr.lavapower.exalt.exceptions.IllegalComponentException;
import fr.lavapower.exalt.render.Camera;
import fr.lavapower.exalt.input.Input;
import fr.lavapower.exalt.input.Key;
import fr.lavapower.exalt.utils.*;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Exalt
{
    private static final GLFWErrorCallback errorCallback = GLFWErrorCallback.createThrow();
    private final GLFWWindowSizeCallback windowSizeCallback = new GLFWWindowSizeCallback()
    {
        @Override
        public void invoke(long argWindow, int argWidth, int argHeight)
        {
            width = argWidth;
            height = argHeight;
            hasResized = true;
        }
    };

    private long window;
    private float width;
    private float height;
    private double fps_time = 1/60.;
    private int fps = 0;
    private boolean debug = false;
    private boolean exitOnEsc = true;
    private boolean vSync = true;
    private Input input;
    private World world;
    private Camera camera;
    private boolean hasResized;
    private Color backgroundColor;

    private void init(String title, float width, float height, boolean fullscreen, Color backgroundColor) {
        glfwSetErrorCallback(errorCallback);

        if(!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        window = glfwCreateWindow((int) width, (int) height, title, fullscreen ? glfwGetPrimaryMonitor() : 0, 0);
        if(window == 0)
        {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }
        glfwShowWindow(window);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        input = new Input(window);
        world = new World();
        world.exalt = this;
        hasResized = false;
        camera = new Camera(width, height);
        this.width = width;
        this.height = height;
        this.backgroundColor = backgroundColor;
        glClearColor(backgroundColor.getFloatRed(), backgroundColor.getFloatGreen(), backgroundColor.getFloatBlue(), backgroundColor.getFloatAlpha());
        glfwSetWindowSizeCallback(window, windowSizeCallback);
    }

    // Constructors
    public Exalt() { init("Exalt Window", 640, 480, false, Colors.WHITE.get()); }
    public Exalt(String title) { init(title, 640, 480, false, Colors.WHITE.get()); }
    public Exalt(String title, boolean fullscreen) { init(title, 640, 480, fullscreen, Colors.WHITE.get()); }
    public Exalt(String title, Color backgroundColor) { init(title, 640, 480, false, backgroundColor); }
    public Exalt(String title, Size size) { init(title, size.width, size.height, false, Colors.WHITE.get()); }
    public Exalt(String title, float width, float height) { init(title, width, height, false, Colors.WHITE.get()); }
    public Exalt(String title, boolean fullscreen, Color backgroundColor) { init(title, 640, 480, fullscreen, backgroundColor); }
    public Exalt(String title, Size size, boolean fullscreen) { init(title, size.width, size.height, fullscreen, Colors.WHITE.get()); }
    public Exalt(String title, Size size, boolean fullscreen, Color backgroundColor) { init(title, size.width, size.height, fullscreen, backgroundColor); }
    public Exalt(String title, float width, float height, boolean fullscreen) { init(title, width, height, fullscreen, Colors.WHITE.get()); }
    public Exalt(String title, float width, float height, boolean fullscreen, Color backgroundColor) { init(title, width, height, fullscreen, backgroundColor); }

    //Size methods
    public Size getSize() { return new Size(width, height); }

    public void setSize(float width, float height) {
        glfwSetWindowSize(window, (int) width, (int) height);
        this.width = width;
        this.height = height;
    }
    public void setSize(Size size) { setSize(size.width, size.height); }
    public Exalt size(float width, float height) { setSize(width, height); return this; }
    public Exalt size(Size size) { setSize(size); return this; }

    //Pos method
    public Position getPosition() {
        IntBuffer xBuff = MemoryUtil.memAllocInt(1);
        IntBuffer yBuff = MemoryUtil.memAllocInt(1);

        glfwGetWindowPos(window, xBuff, yBuff);

        int x = xBuff.get();
        int y = yBuff.get();

        MemoryUtil.memFree(xBuff);
        MemoryUtil.memFree(yBuff);

        return new Position(x, y);
    }

    public void setPosition(int x, int y) { glfwSetWindowPos(window, x, y); }
    public void setPosition(Position pos) { glfwSetWindowPos(window, (int) pos.x, (int) pos.y); }
    public void setCenter() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        Size size = getSize();
        if(vidMode != null)
            glfwSetWindowPos(window, (int)(vidMode.width() - size.width) / 2, (int)(vidMode.height() - size.height) / 2);
    }
    public Exalt position(int x, int y) { setPosition(x, y); return this; }
    public Exalt position(Position pos) { setPosition(pos); return this; }
    public Exalt center() { setCenter(); return this; }

    //FPS
    public int getFPSMax() { return (int)(1/fps_time); }
    public int getCurrentFPS() { return fps; }
    public void setFPSMax(int fpsMax) { fps_time = 1./fpsMax; }
    public Exalt fpsMax(int fpsMax) { setFPSMax(fpsMax); return this; }

    //Debug
    public boolean isDebug() { return debug; }
    public void setDebug(boolean debug) { this.debug = debug; }
    public Exalt debug(boolean debug) { setDebug(debug); return this; }

    //ExitOnEsc
    public boolean isExitOnEsc() { return exitOnEsc; }
    public void setExitOnEsc(boolean exitOnEsc) { this.exitOnEsc = exitOnEsc; }
    public Exalt exitOnEsc(boolean exitOnEsc) {setExitOnEsc(exitOnEsc); return this; }

    //vSync
    public boolean isvSync() { return vSync; }
    public void setvSync(boolean vSync) { this.vSync = vSync; }
    public Exalt vSync(boolean vSync) { setvSync(vSync); return this; }

    //World
    public World getWorld() { return world; }
    public void setWorld(World world) {
        world.exalt = this;
        this.world = world;
    }
    public Exalt world(World world) { setWorld(world); return this; }

    //BackgroundColor
    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        glClearColor(backgroundColor.getFloatRed(), backgroundColor.getFloatGreen(), backgroundColor.getFloatBlue(), backgroundColor.getFloatAlpha());
    }
    public Exalt backgroundColor(Color backgroundColor) { setBackgroundColor(backgroundColor); return this; }

    //Clipboard
    public String getClipboard() { return glfwGetClipboardString(window); }
    public void setClipboard(String text) { glfwSetClipboardString(window, text);}
    public Exalt clipboard(String text) { setClipboard(text); return this; }

    //Others publics mathods
    public Input getInput() { return input; }
    public void stop() { glfwSetWindowShouldClose(window, true); }
    public boolean hasResized() { return hasResized; }
    public Camera getCamera() { return camera; }

    //RUN
    public void run() throws IllegalComponentException
    {
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if(vSync)
            glfwSwapInterval(1);

        double frame_time = 0;
        int frames = 0;
        double time = Timer.getTime();
        double unprocessed = 0;
        double passed = 0;

        while(!glfwWindowShouldClose(window)) {
            passed = Timer.getTime() - time;
            time = Timer.getTime();
            unprocessed += passed;
            frame_time += passed;
            if (unprocessed >= fps_time) {
                if(hasResized()) {
                    camera.setProjection(width, height);
                    glViewport(0, 0, (int)width, (int)height);
                }
                unprocessed-=fps_time;

                if(input.isKeyPressed(Key.ESCAPE) && exitOnEsc) {
                    stop();
                }

                world.update((float)(fps_time));
                input.update();
                hasResized = false;
                glfwPollEvents();

                if(frame_time >= 1.0) {
                    frame_time = 0;
                    fps = frames;
                    frames = 0;
                    if(debug)
                        System.out.println("FPS : "+fps);
                }

                glClear(GL_COLOR_BUFFER_BIT);

                world.render(camera);

                glfwSwapBuffers(window);
                frames++;
            }
        }
        world.delete();
        glfwDestroyWindow(window);
        Callbacks.glfwFreeCallbacks(window);
        glfwTerminate();
        errorCallback.free();
        windowSizeCallback.free();
    }
}
