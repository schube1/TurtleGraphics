package model;

import java.awt.*;
import java.io.Serializable;

public class Point implements Serializable {
    private final int x;
    private final int y;
    private final boolean isPenDown;
    private final Color color;

    public Point(int x, int y, Color color, boolean isPenDown) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isPenDown = isPenDown;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Color getColor() {
        return color;
    }
    public boolean isPenDown() {
        return isPenDown;
    }
}
