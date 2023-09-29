package ar.edu.itba.ss.models;

public class Particle_S2 {
    private int id;
    private double mass;
    private double radius;
    private double position;
    private double angularVelocity;
    private double angularAcceleration;
    private double ui;

    private Particle_S2(Builder builder) {
        this.id = builder.id;
        this.mass = builder.mass;
        this.radius = builder.radius;
        this.position = builder.position;
        this.angularVelocity = builder.angularVelocity;
        this.angularAcceleration = builder.angularAcceleration;
        this.ui = builder.ui;
    }

    public static class Builder {
        private int id;
        private double mass;
        private double radius;
        private double position;
        private double angularVelocity;
        private double angularAcceleration;
        private Double ui;
        double minUi = 9.0;
        double maxUi = 12.0;

        public Builder withUi(double ui) {
            this.ui = ui;
            return this;
        }

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

        public Builder withPosition(double pos) {
            this.position = pos;
            return this;
        }

        public Builder withVelocity(double vel) {
            this.angularVelocity = vel;
            return this;
        }

        public Builder withAcceleration(double acc) {
            this.angularAcceleration = acc;
            return this;
        }

        public Builder cloneParticle(Particle_S2 particle) {
            this.id = particle.id;
            this.ui = particle.ui;
            this.angularAcceleration = particle.angularAcceleration;
            this.position = particle.position;
            this.angularVelocity = particle.angularVelocity;
            this.mass = particle.mass;
            this.radius = particle.radius;
            return this;
        }

        public Particle_S2 build() {
            return new Particle_S2(this);
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

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getVelocity() {
        return angularVelocity;
    }

    public void setVelocity(double velocity) {
        this.angularVelocity = velocity;
    }

    public double getAcceleration() {
        return angularAcceleration;
    }

    public void setAcceleration(double acceleration) {
        this.angularAcceleration = acceleration;
    }
}
