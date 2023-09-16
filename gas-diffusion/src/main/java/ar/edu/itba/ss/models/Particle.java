package ar.edu.itba.ss.models;

import java.util.Objects;

public class Particle {
    private int id;
    private double radius, x, y, vx, vy, direction, mass;

    public Particle(int id, double radius, double x, double y, double vx, double vy, double mass) {
        this.id = id;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.direction = Math.atan2(vy, vx);
        this.mass = mass;
    }

    public boolean isLeftSide(double sideLength) {
        return this.x < sideLength;
    }

    public double getRadius() {
        return radius;
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

    public int getId() {
        return id;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Double.compare(radius, particle.radius) == 0 && Double.compare(x, particle.x) == 0 && Double.compare(y, particle.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, x, y);
    }
}
