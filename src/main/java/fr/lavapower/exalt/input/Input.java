package fr.lavapower.exalt.input;

import static org.lwjgl.glfw.GLFW.*;

import java.util.HashMap;

public class Input
{
    private final long window;
    private final HashMap<Key, Boolean> keys = new HashMap<Key, Boolean>();
    private final HashMap<MouseButton, Boolean> mouseButtons = new HashMap<MouseButton, Boolean>();

    public Input(long window) {
        this.window = window;
        for(Key key: Key.values())
        {
            keys.put(key, false);
        }
        for(MouseButton mouseButton: MouseButton.values())
        {
            mouseButtons.put(mouseButton, false);
        }
    }

    public void update() {
        for(Key key: Key.values())
        {
            keys.put(key, isKeyDown(key));
        }
        for(MouseButton mouseButton: MouseButton.values())
        {
            mouseButtons.put(mouseButton, isMouseButtonDown(mouseButton));
        }
    }

    public boolean isKeyDown(Key key) {
        return glfwGetKey(window, key.value) == 1;
    }

    public boolean isKeyPressed(Key key) {
        return isKeyDown(key) && !keys.get(key);
    }

    public boolean isKeyUp(Key key) {
        return !isKeyDown(key);
    }

    public boolean isKeyReleased(Key key) {
        return isKeyUp(key) && keys.get(key);
    }

    public boolean isMouseButtonDown(MouseButton mouseButton) {
        return glfwGetMouseButton(window, mouseButton.value) == 1;
    }

    public boolean isMouseButtonPressed(MouseButton mouseButton) {
        return isMouseButtonDown(mouseButton) && !mouseButtons.get(mouseButton);
    }

    public boolean isMouseButtonUp(MouseButton mouseButton) {
        return !isMouseButtonDown(mouseButton);
    }

    public boolean isMouseButtonReleased(MouseButton mouseButton) {
        return isMouseButtonUp(mouseButton) && mouseButtons.get(mouseButton);
    }
}
