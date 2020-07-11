package fr.lavapower.exalt.utils;

public enum Colors
{
    WHITE(new Color(255, 255, 255)),
    BLACK(new Color(0, 0, 0)),
    GRAY(new Color(128, 128, 128)),
    RED(new Color(255, 0, 0)),
    GREEN(new Color(0, 255, 0)),
    BLUE(new Color(0, 0, 255)),
    FUCHSIA(new Color(255, 0, 255)),
    YELLOW(new Color(255, 255, 0)),
    CYAN(new Color(0, 255, 255)),
    LIME(new Color(0, 128, 0)),
    BROWN(new Color(128, 0, 0)),
    NAVY_BLUE(new Color(0, 0, 128)),
    OLIVE(new Color(128, 128, 0)),
    PURPLE(new Color(128, 0, 128)),
    TEAL(new Color(0, 128, 128)),
    SILVER(new Color(192, 192, 192)),
    ORANGE(new Color(255, 128, 0));

    private final Color value;

    Colors(Color value) {
        this.value = value;
    }

    public Color get() { return value; }
}
