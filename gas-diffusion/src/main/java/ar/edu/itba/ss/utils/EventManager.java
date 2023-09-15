package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Particle;

import java.util.List;

import static ar.edu.itba.ss.utils.MathUtils.dotProduct;

public class EventManager {

    private Boolean wallCollision;
    private Boolean isHorizontalCollision;

    private Particle wallCollisionParticle;
    private Particle firstCollisionParticle;
    private Particle secondCollisionParticle;
    private Particle auxCollisionParticle;

    private double leftSideImpulse = 0.0;
    private double rightSideImpulse = 0.0;

    public double getNextEventTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = getWallCollisionTime(particles, L, sideLength);
        double auxEventTime = getParticlesCollisionTime(particles);

        if(auxEventTime < nextEventTime) {
            nextEventTime = auxEventTime;
            this.wallCollision = false;
        } else {
            this.wallCollision = true;
        }

        return nextEventTime;
    }

    private boolean isInsideLeftSquare(double x, double sideLength) {
        return x < sideLength;
    }

    private boolean checkCrossSlit(Particle p, double sideLength, double L) {

        if((isInsideLeftSquare(p.getX(), sideLength) && p.getVx() <= 0)
                ||
                (!isInsideLeftSquare(p.getX(), sideLength) && p.getVx() >= 0)
                    )
            return false;

        double intersectionTime = (sideLength - p.getX()) / p.getVx();

        double intersectionY = p.getY() + p.getVy() * intersectionTime;

        if((intersectionY > sideLength / 2 - L / 2) && (intersectionY < sideLength / 2 + L / 2))
            return true;

        return false;
    }

    private double getWallCollisionTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = Double.MAX_VALUE;
        double currentTime;
        boolean willCrossSlit;
        for(Particle p : particles) {
            willCrossSlit = checkCrossSlit(p, sideLength, L);
            if(!willCrossSlit) {
                if (p.getVx() > 0) {
                    if(isInsideLeftSquare(p.getX(), sideLength))
                        currentTime = (sideLength - p.getRadius() - p.getX()) / p.getVx();
                    else
                        currentTime = (2 * sideLength - p.getRadius() - p.getX()) / p.getVx();
                } else
                    currentTime = (0 + p.getRadius() - p.getX()) / p.getVx();


                if (currentTime < nextEventTime) {
                    nextEventTime = currentTime;
                    this.isHorizontalCollision = false;
                    this.wallCollisionParticle = p;
                }

                if (isInsideLeftSquare(p.getX(), sideLength)) {
                    if (p.getVy() > 0)
                        currentTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
                    else
                        currentTime = (0 + p.getRadius() - p.getY()) / p.getVy();
                } else {
                    if (p.getVy() > 0)
                        currentTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();
                    else
                        currentTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();
                }

                if (currentTime < nextEventTime) {
                    nextEventTime = currentTime;
                    this.isHorizontalCollision = true;
                    this.wallCollisionParticle = p;
                }

            } else {
                if (p.getVx() > 0)
                    currentTime = (2 * sideLength - p.getRadius() - p.getX()) / p.getVx();
                else
                    currentTime = (0 + p.getRadius() - p.getX()) / p.getVx();

                if (currentTime < nextEventTime) {
                    nextEventTime = currentTime;
                    this.isHorizontalCollision = false;
                    this.wallCollisionParticle = p;
                }

                if(isInsideLeftSquare(p.getX(), sideLength)) {
                    if (p.getVy() > 0)
                        currentTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();
                    else
                        currentTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();
                } else {
                    if (p.getVy() > 0)
                        currentTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
                    else
                        currentTime = (0 + p.getRadius() - p.getY()) / p.getVy();
                }

                if (currentTime < nextEventTime) {
                    nextEventTime = currentTime;
                    this.isHorizontalCollision = true;
                    this.wallCollisionParticle = p;
                }
            }
        }
        return nextEventTime;
    }

    private double getParticlesCollisionTime(List<Particle> particles) {
        double nextEventTime = Double.MAX_VALUE;
        double currentTime;
        for(Particle p : particles) {
            currentTime = getMinParticleCollisionTime(p, particles);
            if(currentTime < nextEventTime) {
                nextEventTime = currentTime;
                this.firstCollisionParticle = p;
                this.secondCollisionParticle = auxCollisionParticle;
            }
        }
        return nextEventTime;
    }

    private double getMinParticleCollisionTime(Particle currentParticle, List<Particle> particles) {
        double nextEventTime = Double.MAX_VALUE;
        double currentTime;
        double sigma;
        double[] dr;
        double[] dv;
        double dvdr;
        double drdr;
        double dvdv;
        double d;

        for(Particle p : particles) {
            if(p.getId() == currentParticle.getId())
                continue;

            sigma = currentParticle.getRadius() + p.getRadius();
            dr = new double[] {p.getX() - currentParticle.getX(), p.getY() - currentParticle.getY()};
            dv = new double[] {p.getVx() - currentParticle.getVx(), p.getVy() - currentParticle.getVy()};
            dvdr = dotProduct(dv, dr);

            if(dvdr >= 0)
                continue;

            drdr = dotProduct(dr, dr);
            dvdv = dotProduct(dv, dv);
            d = dvdr * dvdr - (dvdv) * (drdr - sigma * sigma);

            if(d < 0)
                continue;

            currentTime = - (dvdr + Math.sqrt(d)) / dvdv;

            if(currentTime < nextEventTime) {
                auxCollisionParticle = p;
                nextEventTime = currentTime;
            }

        }
        return nextEventTime;
    }

    public void evolveTillEvent(List<Particle> particles, double nextEvent) {
        for(Particle p : particles) {
            p.setX(p.getX() + nextEvent*p.getVx());
            p.setY(p.getY() + nextEvent*p.getVy());
        }
    }

    public void resolveCollisionAndAddImpulse(double sideLength) {
        double impulseVelocity;
        if(this.wallCollision) {
            //TODO EL IMPUSLO PUEDE SER NEGATIVO????????
            if(isHorizontalCollision) {
                this.wallCollisionParticle.setVy(-wallCollisionParticle.getVy());
                impulseVelocity = wallCollisionParticle.getVy();
            }
            else {
                this.wallCollisionParticle.setVx(-wallCollisionParticle.getVx());
                impulseVelocity = wallCollisionParticle.getVx();
            }
            if(isInsideLeftSquare(wallCollisionParticle.getX(), sideLength)) {
                this.leftSideImpulse += 2 * Math.abs(impulseVelocity) * wallCollisionParticle.getMass();
            } else {
                this.rightSideImpulse += 2 * Math.abs(impulseVelocity) * wallCollisionParticle.getMass();
            }
            wallCollisionParticle.setDirection(Math.atan2(wallCollisionParticle.getVy(), wallCollisionParticle.getVx()));
        } else {

            final double sigma = secondCollisionParticle.getRadius() + firstCollisionParticle.getRadius();

            final double[] dr = new double[] {this.secondCollisionParticle.getX() - this.firstCollisionParticle.getX(),
                    this.secondCollisionParticle.getY() - this.firstCollisionParticle.getY()};
            final double[] dv = new double[]{this.secondCollisionParticle.getVx() - this.firstCollisionParticle.getVx(),
                    this.secondCollisionParticle.getVy() - this.firstCollisionParticle.getVy()};

            final double J = (2 * secondCollisionParticle.getMass() * firstCollisionParticle.getMass() * dotProduct(dv, dr)) /
                    (sigma * (secondCollisionParticle.getMass() + firstCollisionParticle.getMass()));

            final double Jx = (J * dr[0]) / sigma;
            final double Jy = (J * dr[1]) / sigma;

            secondCollisionParticle.setVx(secondCollisionParticle.getVx() - Jx / secondCollisionParticle.getMass());
            secondCollisionParticle.setVy(secondCollisionParticle.getVy() - Jy / secondCollisionParticle.getMass());
            secondCollisionParticle.setDirection(Math.atan2(secondCollisionParticle.getVy(), secondCollisionParticle.getVx()));


            firstCollisionParticle.setVx(firstCollisionParticle.getVx() + Jx / firstCollisionParticle.getMass());
            firstCollisionParticle.setVy(firstCollisionParticle.getVy() + Jy / firstCollisionParticle.getMass());
            firstCollisionParticle.setDirection(Math.atan2(firstCollisionParticle.getVy(), firstCollisionParticle.getVx()));
        }
    }

    public double getLeftSideImpulse() {
        return leftSideImpulse;
    }

    public double getRightSideImpulse() {
        return rightSideImpulse;
    }
}
