package org.example.hexgame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    private boolean isFilled = false;
    private Color fillColor = Color.WHITE;
    private int x, y;

    public Tile(double x, double y, int boardX, int boardY, double r, double n, double TILE_WIDTH) {
        this.x = boardX;
        this.y = boardY;
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + TILE_WIDTH, y + r,
                x + TILE_WIDTH, y,
                x + n, y - r * 0.5
        );

        setFill(Color.WHITE);
        setStrokeWidth(1);
        setStroke(Color.BLACK);
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
