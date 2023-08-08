package ar.edu.itba.ss.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Particle {
    private int id;
    private double radius;
    private double x;
    private double y;
    private int property;
    private Set<Particle> neighbours;

    public Particle(int id, double radius, double x, double y, int property) {
        this.id = id;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.property = property;
        this.neighbours = new HashSet<>();
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

    public void setNeighbours(Set<Particle> neighbours) {
        this.neighbours = neighbours;
    }

    public int getId() {
        return id;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public double getDistanceTo(Particle other) {
        return Math.sqrt(
                Math.pow(this.x - other.getX(), 2) +
                Math.pow(this.y - other.getY(), 2)
        ) - this.radius - other.getRadius();
    }

    public double getContourDistanceTo(Particle other, double dim) {
        double dx = Math.abs(this.x - other.getX());
        if(dx > dim / 2) {
            dx = dim - dx;
        }
        double dy = Math.abs(this.y - other.getY());
        if(dy > dim / 2) {
            dy = dim - dy;
        }
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) - this.radius - other.getRadius();
    }

    public void addNeighbour(Particle neighbour) {
        this.neighbours.add(neighbour);
    }

    public String neighboursToString() {
        StringBuilder builder = new StringBuilder("[");
        builder.append(id);
        for(Particle neighbour : neighbours) {
            builder.append(" ");
            builder.append(neighbour.getId());
        }
        builder.append("]");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Double.compare(radius, particle.radius) == 0 && Double.compare(x, particle.x) == 0 && Double.compare(y, particle.y) == 0 && property == particle.property;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, x, y, property);
    }
}
