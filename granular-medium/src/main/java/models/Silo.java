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

    public void vibrate(double t, double omega) {
        baseYCoordinate = A * Math.sin(omega * t);
    }

    public void updateForces() {
        double distance;
        double totalRadius;
        for (Particle current : particles) {
            current.addForce(new Vector(0, current.getMass() * G));
            for (Particle other : particles) {
                if (current.equals(other))
                    continue;
                distance = current.getPosition().distance(other.getPosition());
                totalRadius = current.getRadius() + other.getRadius();

                if (distance < totalRadius) {
                    Vector normalVersor = other.getPosition().difference(current.getPosition()).scalarProduct(1.0 / distance);
                    current.addForce(getNormalForce(totalRadius - distance, normalVersor));

                    Vector relativeVelocity = current.getVelocity().difference(other.getVelocity());
                    current.addToForce(getTangencialForce(totalRadius - distance, relativeVelocity, normalVersor));
                }
            }

            double superposition;

            //abajo
            if (withinHole(current) && !current.leftSilo()) {
                superposition = current.getRadius() - (current.getPosition().getY() - 0);
                if (superposition > 0)
                    current.addToForce(
                            getWallForce(superposition, current.getVelocity(), new Vector(0, -1.0))
                    );
            }

            //arriba
            superposition = current.getRadius() - (L - current.getPosition().getY());
            if (superposition > 0)
                current.addToForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(0, 1.0))
                );

            //izquierda
            superposition = current.getRadius() - (current.getPosition().getX() - 0);
            if (superposition > 0)
                current.addToForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(-1.0, 0))
                );

            //derecha
            superposition = current.getRadius() - (W - current.getPosition().getX());
            if (superposition > 0)
                current.addToForce(
                        getWallForce(superposition, current.getVelocity(), new Vector(1.0, 0))
                );
        }
    }

    public int resetParticles() {
        int particlesLeft = 0;

        for(Particle p : particles) {
            if(!p.leftSilo() && withinHole(p) && p.getPosition().getY() < baseYCoordinate) {
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

    public boolean withinHole(Particle current) {
        return (current.getPosition().getX() > W / 2 + D / 2) || (current.getPosition().getX() < W / 2 - D / 2);
    }
}
