package ar.edu.itba.ss.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    public double getRadius() {
        return radius;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        if(x == 0.09)
            System.out.println("raro");
        if(x >= (0.09 - radius) && (this.y < 0.03 || this.y > 0.06))
            System.out.println("imposible");
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

    public void move(double eta, int sideLength) {
        double DEFAULT_SPEED = 0.03;
        double accumulativeSin = Math.sin(this.direction);
        double accumulativeCos = Math.cos(this.direction);
        for(Particle neighbour : this.neighbours) {
            accumulativeCos += Math.cos(neighbour.direction);
            accumulativeSin += Math.sin(neighbour.direction);
        }
        double quantity = this.neighbours.size() + 1;
        double sinAverage = accumulativeSin / quantity;
        double cosAverage = accumulativeCos / quantity;
        double noise = Math.random() * eta - eta / 2;
        double nextDir = Math.atan2(sinAverage, cosAverage) + noise;
//        this.setDirection((nextDir + 2 * Math.PI) % (2 * Math.PI));
        this.setX((sideLength + this.x + this.vx) % sideLength);
        this.setY((sideLength + this.y + this.vy) % sideLength);
        this.setVx(DEFAULT_SPEED * Math.cos(this.direction));
        this.setVy(DEFAULT_SPEED * Math.sin(this.direction));
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
