package ar.edu.itba.ss.models;

public class Particle {
    private double radius;
    private double x;
    private double y;
    private int property;

    public Particle(double radius, double x, double y, int property) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.property = property;
    }

    public Particle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }
}
