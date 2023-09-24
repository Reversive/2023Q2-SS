package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmVerlet implements Algorithm {
    private final Algorithm algorithm;
    private final double K;
    private final double gamma;

    public AlgorithmVerlet(Algorithm algorithm, double k, double gamma) {
        this.algorithm = algorithm;
        K = k;
        this.gamma = gamma;
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        if (current == null) {
            throw new NullPointerException();
        }

        if (previous == null) {
            previous = algorithm.update(null, current, -deltaTime,  currentTime);
        }

        double mass = current.getMass();
        double newX = 2 * current.getPosition().getX() - previous.getPosition().getX() + Math.pow(deltaTime, 2) * current.getAcceleration().getAx();
        double newY = 2 * current.getPosition().getY() - previous.getPosition().getY() + Math.pow(deltaTime, 2) * current.getAcceleration().getAy();
        double newVx = (newX - previous.getPosition().getX()) / (2 * deltaTime);
        double newVy = (newY - previous.getPosition().getY()) / (2 * deltaTime);
        current.getVelocity().setVx(newVx);
        current.getVelocity().setVy(newVy);

        Particle next = new Particle.Builder().cloneParticle(current).build();
        next.getPosition().setX(newX);
        next.getPosition().setY(newY);
        next.getAcceleration().setAx((-K * next.getPosition().getX() - gamma * next.getVelocity().getVx()) / mass);
        next.getAcceleration().setAy((-K * next.getPosition().getY() - gamma * next.getVelocity().getVy()) / mass);

        return next;
    }

    @Override
    public String getName() {
        return "Verlet";
    }
}
