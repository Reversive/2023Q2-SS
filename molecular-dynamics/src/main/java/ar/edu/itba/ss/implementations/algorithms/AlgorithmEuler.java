package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmEuler extends AlgorithmBase implements Algorithm {

    public AlgorithmEuler(double k, double gamma, AlgorithmType type) {
        super(k, gamma, type);
    }

    public AlgorithmEuler(double k, double gamma) {
        super(k, gamma, AlgorithmType.EULER);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        if(current == null) throw new NullPointerException("This can't happen");
        Particle next = new Particle.Builder()
                .cloneParticle(current)
                .build();
        updateEuler(current, next, deltaTime, false);
        return next;
    }

    protected void updateEuler(Particle current, Particle next, double deltaTime, boolean isModified) {
        updatePosition(current, next, deltaTime, false);
        updateVelocityAndForce(current, next, deltaTime);
    }

    private void updateVelocityAndForce(Particle current, Particle next, double deltaTime) {
        next.getVelocity().setVx(current.getVelocity().getVx() + deltaTime * current.getAcceleration().getAx());
        next.getVelocity().setVy(current.getVelocity().getVy() + deltaTime * current.getAcceleration().getAy());
        next.getAcceleration().setAx(((-K * next.getPosition().getX() - gamma * next.getVelocity().getVx())/ next.getMass()));
        next.getAcceleration().setAy((-K * next.getPosition().getY() - gamma * next.getVelocity().getVy())/ next.getMass());
    }

    private void updatePosition(Particle current, Particle next, double deltaTime, boolean isModified) {
        double vx = isModified ? next.getVelocity().getVx() : current.getVelocity().getVx();
        double vy = isModified ? next.getVelocity().getVy() : current.getVelocity().getVy();
        next.getPosition().setX(current.getPosition().getX() + deltaTime * vx + Math.pow(deltaTime, 2) * current.getAcceleration().getAx() / 2);
        next.getPosition().setY(current.getPosition().getY() + deltaTime * vy + Math.pow(deltaTime, 2) * current.getAcceleration().getAy() / 2);
    }


}
