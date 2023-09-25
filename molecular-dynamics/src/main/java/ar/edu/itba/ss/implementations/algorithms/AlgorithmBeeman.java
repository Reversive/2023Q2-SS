package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.*;

import java.awt.geom.Point2D;

public class AlgorithmBeeman extends AlgorithmBase implements Algorithm {

    private final Algorithm algorithm;
    public AlgorithmBeeman(double K, double gamma) {
        super(K, gamma, AlgorithmType.BEEMAN);
        algorithm = new AlgorithmEuler(K, gamma);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        if (current == null) {
            throw new NullPointerException("This cannot happen");
        }
        if(previous == null) {
            previous = algorithm.update(null, current, -deltaTime, currentTime);
        }
        Particle next = new Particle.Builder().cloneParticle(current).build();
        Position nextPosition = calculateNextPosition(previous, current, deltaTime);
        Velocity predictedVelocity = calculatePredictedVelocity(previous, current, deltaTime);
        Acceleration predictedAcceleration = calculatePredictedAcceleration(nextPosition, predictedVelocity, current.getMass());
        Velocity correctedVelocity = calculateCorrectedVelocity(previous, current, deltaTime, predictedAcceleration);
        Acceleration correctedAcceleration = calculatePredictedAcceleration(nextPosition, correctedVelocity, current.getMass());
        next.getPosition().setX(nextPosition.getX());
        next.getPosition().setY(nextPosition.getY());
        next.getVelocity().setVx(correctedVelocity.getVx());
        next.getVelocity().setVy(correctedVelocity.getVy());
        next.getAcceleration().setAx(correctedAcceleration.getAx());
        next.getAcceleration().setAy(correctedAcceleration.getAy());
        return next;
    }

    private Velocity calculateCorrectedVelocity(Particle previous, Particle current, double deltaTime, Acceleration predictedAcceleration) {
        double vx = current.getVelocity().getVx() + (1.0/3) * predictedAcceleration.getAx() * deltaTime + (5.0/6) * current.getAcceleration().getAx() * deltaTime -
                (1.0/6) * previous.getAcceleration().getAx() * deltaTime;
        double vy = current.getVelocity().getVy() + (1.0/3) * predictedAcceleration.getAy() * deltaTime + (5.0/6) * current.getAcceleration().getAy() * deltaTime -
                (1.0/6) * previous.getAcceleration().getAy() * deltaTime;
        return new Velocity(vx, vy);
    }

    private Acceleration calculatePredictedAcceleration(Position nextPosition, Velocity predictedVelocity, double mass) {
        double ax = (-K * nextPosition.getX() - gamma * predictedVelocity.getVx()) / mass;
        double ay = (-K * nextPosition.getY() - gamma * predictedVelocity.getVy()) / mass;
        return new Acceleration(ax, ay);
    }

    private Velocity calculatePredictedVelocity(Particle previous, Particle current, double deltaTime) {
        double vx = current.getVelocity().getVx() + 1.5 * current.getAcceleration().getAx() * deltaTime - 0.5 * previous.getAcceleration().getAx() * deltaTime;
        double vy = current.getVelocity().getVy() + 1.5 * current.getAcceleration().getAy() * deltaTime - 0.5 * previous.getAcceleration().getAy() * deltaTime;
        return new Velocity(vx, vy);
    }

    private static Position calculateNextPosition(Particle previous, Particle current, double deltaTime) {
        double nextX = current.getPosition().getX() + current.getVelocity().getVx() * deltaTime +
                (2.0/3) * current.getAcceleration().getAx() * Math.pow(deltaTime, 2) -
                (1.0/6) * previous.getAcceleration().getAx() * Math.pow(deltaTime, 2);

        double nextY = current.getPosition().getY() + current.getVelocity().getVy() * deltaTime +
                (2.0/3) * current.getAcceleration().getAy() * Math.pow(deltaTime, 2) -
                (1.0/6) * previous.getAcceleration().getAy() * Math.pow(deltaTime, 2);

        return new Position(nextX, nextY);
    }


}
