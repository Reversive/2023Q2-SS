package ar.edu.itba.ss.models;

public class Particle {
    private int id;
    private double mass;
    private double radius;
    private Position position;
    private Velocity velocity;
    private Acceleration acceleration;

    private Particle(Builder builder) {
        this.id = builder.id;
        this.mass = builder.mass;
        this.radius = builder.radius;
        this.position = builder.position;
        this.velocity = builder.velocity;
        this.acceleration = builder.acceleration;
    }

    public static class Builder {
        private int id;
        private double mass;
        private double radius;
        private Position position;
        private Velocity velocity;
        private Acceleration acceleration;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withMass(double mass) {
            this.mass = mass;
            return this;
        }

        public Builder withRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder withPosition(double x, double y) {
            this.position = new Position(x, y);
            return this;
        }

        public Builder withVelocity(double vx, double vy) {
            this.velocity = new Velocity(vx, vy);
            return this;
        }

        public Builder withAcceleration(double ax, double ay) {
            this.acceleration = new Acceleration(ax, ay);
            return this;
        }

        public Builder cloneParticle(Particle particle) {
            this.id = particle.id;
            this.acceleration = particle.acceleration;
            this.position = particle.position;
            this.velocity = particle.velocity;
            this.mass = particle.mass;
            this.radius = particle.radius;
            return this;
        }

        public Particle build() {
            return new Particle(this);
        }
    }

    public int getId() {
        return id;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    public Acceleration getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Acceleration acceleration) {
        this.acceleration = acceleration;
    }

    public double distanceTo(Particle other) {
        return Math.sqrt(Math.pow(this.position.getX() - other.position.getX(), 2) + Math.pow(this.position.getY() - other.position.getY(), 2));
    }

    public double distanceToWithRadius(Particle other) {
        return Math.sqrt(Math.pow(this.position.getX() - other.position.getX(), 2) + Math.pow(this.position.getY() - other.position.getY(), 2)) - this.radius - other.radius;
    }

    public boolean isOverlapping(Particle other) {
        return this.distanceTo(other) <= this.radius + other.radius;
    }
}
