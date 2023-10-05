package ar.edu.itba.ss.models;

public class Particle_S2 {
    private int id;
    private double mass;
    private double radius;
    private double angle;
    private double position;
    private double angularVelocity;
    private double angularAcceleration;
    private double ui;
    private double X3;
    private double X4;
    private double X5;

    @Override
    public String toString() {
        return "Particle_S2{" +
                "id=" + id +
                ", position=" + position +
                ", ui=" + ui +
                '}';
    }

    private Particle_S2(Builder builder) {
        this.id = builder.id;
        this.mass = builder.mass;
        this.radius = builder.radius;
        this.angle = builder.angle;
        this.position = builder.position;
        this.angularVelocity = builder.angularVelocity;
        this.angularAcceleration = builder.angularAcceleration;
        this.ui = builder.ui;
        this.X3 = builder.X3;
        this.X4 = builder.X4;
        this.X5 = builder.X5;
    }

    public double getUi(Particle_S2 particle_s2) {
        return this.ui;
    }

    public static class Builder {
        private int id;
        private double mass;
        private double radius;
        private double angle;
        private double position;
        private double angularVelocity;
        private double angularAcceleration;
        private double ui;
        private double X3;
        private double X4;
        private double X5;

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

        public Builder withAngle(double ang) {
            this.angle = ang;
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

        public Builder withX3(double x3) {
            this.X3 = x3;
            return this;
        }

        public Builder withX4(double x4) {
            this.X4 = x4;
            return this;
        }

        public Builder withX5(double x5) {
            this.X5 = x5;
            return this;
        }

        public Builder cloneParticle(Particle_S2 particle) {
            this.id = particle.id;
            this.ui = particle.ui;
            this.angularAcceleration = particle.angularAcceleration;
            this.position = particle.position;
            this.angle = particle.angle;
            this.angularVelocity = particle.angularVelocity;
            this.mass = particle.mass;
            this.radius = particle.radius;
            this.X3 = particle.getX3();
            this.X4 = particle.getX4();
            this.X5 = particle.getX5();
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
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

    public double getUi() {
        return this.ui;
    }

    public void setUi(double ui) {
        this.ui = ui;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getX3() {
        return X3;
    }

    public void setX3(double x3) {
        X3 = x3;
    }

    public double getX4() {
        return X4;
    }

    public void setX4(double x4) {
        X4 = x4;
    }

    public double getX5() {
        return X5;
    }

    public void setX5(double x5) {
        X5 = x5;
    }
}
