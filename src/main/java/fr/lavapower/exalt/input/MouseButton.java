package fr.lavapower.exalt.input;

import static org.lwjgl.glfw.GLFW.*;

public enum MouseButton
{
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    LAST(GLFW_MOUSE_BUTTON_LAST),

    B1(GLFW_MOUSE_BUTTON_1),
    B2(GLFW_MOUSE_BUTTON_2),
    B3(GLFW_MOUSE_BUTTON_3),
    B4(GLFW_MOUSE_BUTTON_4),
    B5(GLFW_MOUSE_BUTTON_5),
    B6(GLFW_MOUSE_BUTTON_6),
    B7(GLFW_MOUSE_BUTTON_7),
    B8(GLFW_MOUSE_BUTTON_8);

    public final int value;

    MouseButton(int value) {
        this.value = value;
    }
}
