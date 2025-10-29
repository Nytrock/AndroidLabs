package com.example.lab8.Drawer;

public class Point {
    private float x, y;

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point add(Point anotherPoint) {
        return new Point(x + anotherPoint.x, y + anotherPoint.y);
    }

    public Point subtract(Point anotherPoint) {
        return new Point(x - anotherPoint.x, y - anotherPoint.y);
    }

    public Point rotateToAngle(float angle) {
        double new_x = x * Math.cos(angle) - y * Math.sin(angle);
        double new_y = x * Math.sin(angle) - y * Math.cos(angle);
        return new Point((float) new_x, (float) new_y);
    }

    public void addToX(float value){
        x += value;
    }

    public void addToY(float value){
        y += value;
    }
}
