package models;

import utils.ForcesUtils;

import java.util.Objects;

public class Particle {

    private final int id;
    private final double r;
    private final double m;
    private final Vector force;
    private boolean leftSilo = false;
    private boolean reset = false;

    private final double dt;
    private Vector position;

    private Vector velocity;
    private Vector realVelocity;

    private Vector previousAcceleration;
    private Vector realAcceleration;

    public Particle(int id, Vector position, double r, double m, double dt) {
        this.id = id;
        this.position = position;
        this.r = r;
        this.m = m;
        this.force = new Vector(0.0, 0.0);
        this.velocity = new Vector(0.0, 0.0);
        this.dt = dt;
        realAcceleration = new Vector(0.0, 0.0);
        previousAcceleration = new Vector(0.0, ForcesUtils.G);
    }

    public void resetParticle() {
        reset = true;
    }

    public Double getRadius() {
        return r;
    }

    public Double getMass() {
        return m;
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    //TODO TOCAR ESTE METODO
    public void predictor() {

        realAcceleration = this.getAcceleration();
        this.position = position.sum(
                velocity.byScalarProduct(dt).sum(
                        realAcceleration.byScalarProduct(2.0 / 3.0).sum(
                                previousAcceleration.byScalarProduct(-1.0 / 6.0)
                        ).byScalarProduct(Math.pow(dt, 2))
                )
        );

        this.realVelocity = velocity;

        this.velocity = this.realVelocity.sum(
                this.realAcceleration.byScalarProduct(1.5 * dt).sum(
                        previousAcceleration.byScalarProduct(-0.5 * dt)
                )
        );

    }

    //TODO TOCAR ESTE METODO
    public void corrector(){
        if (reset) {
            this.reset = false;
            this.velocity = new Vector(0, 0);
            previousAcceleration = new Vector(0, ForcesUtils.G);
        } else {
            this.velocity = realVelocity.sum(
                    this.getAcceleration().byScalarProduct((1.0 / 3.0) * dt).sum(
                            realAcceleration.byScalarProduct((5.0 / 6.0) * dt).sum(
                                    previousAcceleration.byScalarProduct(-(1.0 / 6.0) * dt)
                            )
                    )
            );
            previousAcceleration = realAcceleration;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Particle)) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean leftSilo() {
        return leftSilo;
    }

    public void setLeftSilo(boolean leftSilo) {
        this.leftSilo = leftSilo;
    }

    public void resetParticleForce() {
        force.setX(0);
        force.setY(0);
    }

    public void addForce(Vector additionalForce) {
        force.setX(force.getX() + additionalForce.getX());
        force.setY(force.getY() + additionalForce.getY());
    }

    public Vector getAcceleration() {
        return force.byScalarProduct(1.0 / m);
    }

    public int getId() {
        return id;
    }
}
