package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.EventTime;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

import static ar.edu.itba.ss.utils.MathUtils.dotProduct;

public class EventManager {

    private Boolean wallCollision;
    private Boolean isHorizontalCollision;
    private Boolean isSlitCollision;

    private Particle wallCollisionParticle;
    private Particle firstCollisionParticle;
    private Particle secondCollisionParticle;
    private Particle auxCollisionParticle;

    private double leftSideImpulse = 0.0;
    private double rightSideImpulse = 0.0;

    private final double DELTA = 0.00001;

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

    private void setTimeRightSquareRightSide(Particle p, double sideLength, EventTime nextEventTime) {
        double nextVxTime = (2 * sideLength - p.getRadius() - p.getX()) / p.getVx();
        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
            nextEventTime.setSlitCollision(false);
        }
    }

    private void setTimeRightSquareTopSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
        }
    }

    private void setTimeRightSquareBottomSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
        }
    }

    private void setTimeLeftSquareLeftSide(Particle p, EventTime nextEventTime) {
        double nextVxTime = (0 + p.getRadius() - p.getX()) / p.getVx();
        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setHorizontal(false);
        }
    }

    private void setTimeLeftSquareRightSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double auxVxTime = (sideLength - p.getX()) / p.getVx();
        double intersectionY = p.getY() + (p.getVy() * auxVxTime);
        boolean isSlit;
        double nextVxTime;
        if((intersectionY > sideLength / 2 - L / 2 + p.getRadius()) && (intersectionY < sideLength / 2 + L / 2 - p.getRadius())) {
            nextVxTime = auxVxTime;
            isSlit = true;
        } else {
            nextVxTime = (sideLength - p.getRadius() - p.getX()) / p.getVx();
            isSlit = false;
        }
        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
            nextEventTime.setSlitCollision(isSlit);
        }
    }

    private void setTimeLeftSquareTopSide(Particle p, double sideLength, EventTime nextEventTime) {
        double nextVyTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
        }
    }

    private void setTimeLeftSquareBottomSide(Particle p, EventTime nextEventTime) {
        double nextVyTime = (0 + p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
        }
    }

    private void setTimeRightSquareLeftSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double nextVxTime = (sideLength - DELTA - p.getX()) / p.getVx();
        double intersectionY = p.getY() + (p.getVy() * nextVxTime);
        boolean isSlit;

        if((intersectionY > sideLength / 2 - L / 2 + p.getRadius()) && (intersectionY < sideLength / 2 + L / 2 - p.getRadius())) {
            isSlit = true;
        } else {
            nextVxTime = Double.MAX_VALUE;
            isSlit = false;
        }

        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
            nextEventTime.setSlitCollision(isSlit);
        }
    }

    private double getWallCollisionTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = Double.MAX_VALUE;
        EventTime currentEventTime = new EventTime(Double.MAX_VALUE, false, false);
        for(Particle p : particles) {
            if(p.isLeftSide(sideLength)) {
                if(p.getVx() > 0) {
                    setTimeLeftSquareRightSide(p,sideLength,currentEventTime, L);
                } else {
                    setTimeLeftSquareLeftSide(p, currentEventTime);
                }
                if(p.getVy() > 0) {
                    setTimeLeftSquareTopSide(p, sideLength, currentEventTime);
                } else {
                    setTimeLeftSquareBottomSide(p, currentEventTime);
                }
            } else {
                if(p.getVx() > 0) {
                    setTimeRightSquareRightSide(p, sideLength, currentEventTime);
                } else {
                    setTimeRightSquareLeftSide(p, sideLength, currentEventTime, L);
                }
                if(p.getVy() > 0) {
                    setTimeRightSquareTopSide(p,L,sideLength,currentEventTime);
                } else {
                    setTimeRightSquareBottomSide(p,L,sideLength,currentEventTime);
                }
            }
            if(currentEventTime.getTime() < nextEventTime) {
                nextEventTime = currentEventTime.getTime();
                this.isSlitCollision = currentEventTime.isSlitCollision();
                this.isHorizontalCollision = currentEventTime.isHorizontal();
                this.wallCollisionParticle = p;
            }
        }
        return nextEventTime;
    }


    private double getParticlesCollisionTime(List<Particle> particles) {
        double nextEventTime = Double.MAX_VALUE;
        double currentTime;
        for(Particle p : particles) {
            currentTime = getMinParticleCollisionTime(p, particles);
            if(Double.compare(currentTime, nextEventTime) < 0) {
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

            if(Double.compare(dvdr, 0) >= 0)
                continue;

            drdr = dotProduct(dr, dr);
            dvdv = dotProduct(dv, dv);
            d = (dvdr * dvdr) - ((dvdv) * (drdr - (sigma * sigma)));

            if(Double.compare(d, 0) < 0)
                continue;

            currentTime = - ((dvdr + Math.sqrt(d)) / dvdv);

            if(currentTime < nextEventTime) {
                auxCollisionParticle = p;
                nextEventTime = currentTime;
            }

        }
        return nextEventTime;
    }

    public void evolveTillEvent(List<Particle> particles, double nextEvent, double L) {
        for(Particle p : particles) {
            p.setX(p.getX() + nextEvent*p.getVx());
            p.setY(p.getY() + nextEvent*p.getVy());
        }
    }

    public void resolveCollisionAndAddImpulse(double sideLength) {
        double impulseVelocity;
        if(!isSlitCollision) {
            if(this.wallCollision) {
                if(isHorizontalCollision) {
                    this.wallCollisionParticle.setVy(-this.wallCollisionParticle.getVy());
                    impulseVelocity = this.wallCollisionParticle.getVy();
                }
                else {
                    this.wallCollisionParticle.setVx(-this.wallCollisionParticle.getVx());
                    impulseVelocity = this.wallCollisionParticle.getVx();
                }
                if(this.wallCollisionParticle.getX() < sideLength) {
                    this.leftSideImpulse += 2 * Math.abs(impulseVelocity) * this.wallCollisionParticle.getMass();
                } else {
                    this.rightSideImpulse += 2 * Math.abs(impulseVelocity) * this.wallCollisionParticle.getMass();
                }
            } else {

                final double sigma = secondCollisionParticle.getRadius() + firstCollisionParticle.getRadius();

                final double[] dr = new double[] {this.secondCollisionParticle.getX() - this.firstCollisionParticle.getX(),
                        this.secondCollisionParticle.getY() - this.firstCollisionParticle.getY()};
                final double[] dv = new double[]{this.secondCollisionParticle.getVx() - this.firstCollisionParticle.getVx(),
                        this.secondCollisionParticle.getVy() - this.firstCollisionParticle.getVy()};

                final double J = (2 * this.secondCollisionParticle.getMass() * this.firstCollisionParticle.getMass() * dotProduct(dv, dr)) /
                        (sigma * (this.secondCollisionParticle.getMass() + this.firstCollisionParticle.getMass()));

                final double Jx = (J * dr[0]) / sigma;
                final double Jy = (J * dr[1]) / sigma;

                this.secondCollisionParticle.setVx(this.secondCollisionParticle.getVx() - Jx / this.secondCollisionParticle.getMass());
                this.secondCollisionParticle.setVy(this.secondCollisionParticle.getVy() - Jy / this.secondCollisionParticle.getMass());


                this.firstCollisionParticle.setVx(this.firstCollisionParticle.getVx() + Jx / this.firstCollisionParticle.getMass());
                this.firstCollisionParticle.setVy(this.firstCollisionParticle.getVy() + Jy / this.firstCollisionParticle.getMass());
            }
        }
    }

    public double getLeftSideImpulse() {
        return this.leftSideImpulse;
    }

    public double getRightSideImpulse() {
        return this.rightSideImpulse;
    }

    public void resetImpulse() {
        this.leftSideImpulse = this.rightSideImpulse = 0.0;
    }
}
