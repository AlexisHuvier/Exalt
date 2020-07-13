package fr.lavapower.exalt.utils;

public class Color
{
    private int red;
    private int green;
    private int blue;
    private int alpha;

    public Color(int red, int green, int blue) {
        setRGB(red, green, blue);
    }

    public Color(float red, float green, float blue) {
        setRGB(red, green, blue);
    }

    public Color(int red, int green, int blue, int alpha) {
        setRGBA(red, green, blue, alpha);
    }

    public Color(float red, float green, float blue, float alpha) {
        setRGBA(red, green, blue, alpha);
    }

    public void setRed(int red) {
        this.red = ExaltUtilities.getInRange(red, 0, 255);
    }

    public void setRed(float red) {
        this.red = (int) ExaltUtilities.getInRange(red, 0f, 255f);
    }

    public int getRed() {
        return red;
    }

    public float getFloatRed() {
        return red / 255f;
    }

    public void setGreen(int green) {
        this.green = ExaltUtilities.getInRange(green, 0, 255);
    }

    public void setGreen(float green) {
        this.green = (int) ExaltUtilities.getInRange(green, 0f, 255f);
    }

    public int getGreen() {
        return green;
    }

    public float getFloatGreen() {
        return green / 255f;
    }

    public void setBlue(int blue) {
        this.blue = ExaltUtilities.getInRange(blue, 0, 255);
    }

    public void setBlue(float blue) {
        this.blue = (int) ExaltUtilities.getInRange(blue, 0f, 255f);
    }

    public int getBlue() {
        return blue;
    }

    public float getFloatBlue() {
        return blue / 255f;
    }

    public void setAlpha(int alpha) {
        this.alpha = ExaltUtilities.getInRange(alpha, 0, 255);
    }

    public void setAlpha(float red) {
        this.alpha = (int) ExaltUtilities.getInRange(alpha, 0f, 255f);
    }

    public int getAlpha() {
        return alpha;
    }

    public float getFloatAlpha() {
        return alpha / 255f;
    }

    public void setRGBA(int red, int green, int blue, int alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public void setRGBA(float red, float green, float blue, float alpha) {
        setRed(red);
        setGreen(green);
        setBlue(blue);
        setAlpha(alpha);
    }

    public void setRGB(int red, int green, int blue) {
        setRGBA(red, green, blue, 255);
    }

    public void setRGB(float red, float green, float blue) {
        setRGBA(red, green, blue, 1f);
    }

    public int[] getRGB() {
        return new int[] {getRed(), getGreen(), getBlue()};
    }

    public float[] getFloatRGB() {
        return new float[] {getFloatRed(), getFloatGreen(), getFloatBlue()};
    }

    public int[] getRGBA() {
        return new int[] {getRed(), getGreen(), getBlue(), getAlpha()};
    }

    public float[] getFloatRGBA() {
        return new float[] {getFloatRed(), getFloatGreen(), getFloatBlue(), getFloatAlpha()};
    }

    public Color darker(int time) { setRGB(getRed() + 10*time, getGreen() + 10*time, getBlue() + 10*time); return this; }
    public Color darker() { return darker(1);}

    public Color lighter(int time) { return darker(-time); }
    public Color lighter() { return lighter(1);}
}
