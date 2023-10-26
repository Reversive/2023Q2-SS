package models;

import utils.ParticleUtils;

import java.util.List;

import static utils.ForcesUtils.*;

public class Silo {
    private static final double A = 0.15;
    private static final double W = 20;
    private static final double L = 70;

    private double D;
    private double baseYCoordinate = 0;
    private List<Particle> particles;


    public Silo(final double D, final List<Particle> particles) {
        this.D = D;
        this.particles = particles;
    }

    public double vibrate(double t, double omega) {
        baseYCoordinate = A * Math.sin(omega * t);
        return baseYCoordinate;
    }

    public void updateForces() {
        double distance;
        double totalRadius;
        double normalForceMagnitude;
        for (Particle current : particles) {
            current.addForce(new Vector(0, current.getMass() * G));
            for (Particle other : particles) {
                if (current.equals(other))
                    continue;
                distance = current.getPosition().distance(other.getPosition());
                totalRadius = current.getRadius() + other.getRadius();

                if (distance < totalRadius) {
                    Vector normalVersor = other.getPosition().difference(current.getPosition()).byScalarProduct(1.0 / distance);
                    Vector relativeVelocity = current.getVelocity().difference(other.getVelocity());
                    current.addForce(getNormalForce(totalRadius - distance, normalVersor, relativeVelocity));
                    normalForceMagnitude = getNormalForceMagnitude(totalRadius - distance, normalVersor, relativeVelocity);
                    current.addForce(getTangencialForce(totalRadius - distance, relativeVelocity, normalVersor, normalForceMagnitude));
                }
            }

            double superposition;

            //abajo
            if (!witihinHole(current) && !current.leftSilo()) {
                superposition = current.getRadius() - (current.getPosition().getY() - baseYCoordinate);
                if (superposition > 0)
                    current.addForce(
                            getWallForce(superposition, current.getVelocity(), new Vector(0, -1.0))
                    );
            }

            //arriba
            superposition = current.getRadius() - (L - current.getPosition().getY());
            if (superposition > 0)
                current.addForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(0, 1.0))
                );

            //izquierda
            superposition = current.getRadius() - (current.getPosition().getX() - 0);
            if (superposition > 0)
                current.addForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(-1.0, 0))
                );

            //derecha
            superposition = current.getRadius() - (W - current.getPosition().getX());
            if (superposition > 0)
                current.addForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(1.0, 0))
                );
        }
    }

    public int resetParticles() {
        int particlesLeft = 0;

        for(Particle p : particles) {
            if(!p.leftSilo() && witihinHole(p) && p.getPosition().getY() < baseYCoordinate) {
                p.setLeftSilo(true);
                particlesLeft++;
            }
            if(p.getPosition().getY() <= -(L/10)) {
                p.resetParticle();
                ParticleUtils.regenerateParticle(particles, p);
            }
        }
        return particlesLeft;
    }

    public boolean witihinHole(Particle current) {
        return (current.getPosition().getX() < W / 2 + D / 2) && (current.getPosition().getX() > W / 2 - D / 2);
    }
}
