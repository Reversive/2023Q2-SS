package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.EventTime;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

import static ar.edu.itba.ss.utils.MathUtils.dotProduct;

public class EventManager {

    private Boolean wallCollision;
    private Boolean isHorizontalCollision;
    private Boolean isSlitCollision;
    private Boolean isLeftSideCollision;

    private Particle wallCollisionParticle;
    private Particle firstCollisionParticle;
    private Particle secondCollisionParticle;
    private Particle auxCollisionParticle;

    private double leftSideImpulse = 0.0;
    private double rightSideImpulse = 0.0;

    private static double EPSILON = 0.000000001;

    public double getNextEventTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = getWallCollisionTime(particles, L, sideLength);
        if(nextEventTime < 0)
            System.out.println(nextEventTime);
        if(nextEventTime == 0.0)
            System.out.println("AAA");
        double auxEventTime = getParticlesCollisionTime(particles);
        if(auxEventTime < 0)
            System.out.println("AAA");

        if(auxEventTime < nextEventTime) {
            nextEventTime = auxEventTime;
            this.wallCollision = false;
            this.isSlitCollision = false;
            this.isLeftSideCollision = false;
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
            nextEventTime.setLeftSideCollision(false);
        }
    }

    private void setTimeRightSquareTopSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();

        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setLeftSideCollision(false);
        }
    }

    private void setTimeRightSquareBottomSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();

        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setLeftSideCollision(false);
        }
    }

    private void setTimeLeftSquareLeftSide(Particle p, EventTime nextEventTime) {
        double nextVxTime = (0 + p.getRadius() - p.getX()) / p.getVx();

        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setHorizontal(false);
            nextEventTime.setLeftSideCollision(true);
        }
    }

    private void setTimeLeftSquareRightSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double auxVxTime = (sideLength - p.getX()) / p.getVx();
        double wallAuxTime = (sideLength - p.getRadius() - p.getX()) / p.getVx();
        double wallIntersectionY = p.getY() + (p.getVy() * wallAuxTime);
        boolean interceptsWallFirst = false;
        if(wallIntersectionY >= sideLength / 2 + L / 2 - p.getRadius() || wallIntersectionY <= sideLength / 2 - L / 2 + p.getRadius()) {
            interceptsWallFirst = true;
        }
        double intersectionY = p.getY() + (p.getVy() * auxVxTime);
        boolean isSlit = false;
        double nextVxTime = Double.MAX_VALUE;
        if(!interceptsWallFirst && (intersectionY > sideLength / 2 - L / 2 + p.getRadius()) && (intersectionY < sideLength / 2 + L / 2 - p.getRadius())) {
            nextVxTime = auxVxTime;
            isSlit = true;
        } else if(!(p.getX() + p.getRadius() >= sideLength && (p.getY() <= sideLength / 2 + L / 2 - p.getRadius()) && (p.getY() >= sideLength / 2 - L / 2 + p.getRadius()))) {
            nextVxTime = (sideLength - p.getRadius() - p.getX()) / p.getVx();
            isSlit = false;
        }

        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
            nextEventTime.setSlitCollision(isSlit);
            nextEventTime.setLeftSideCollision(true);
        }
    }

    private void setTimeLeftSquareTopSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double nextVyTime;
        double intersectYTime;
        double intersectionX;
        boolean leftSide;
        if(p.getX() + p.getRadius() >= sideLength && (p.getY() <= sideLength / 2 + L / 2 - p.getRadius()) && (p.getY() >= sideLength / 2 - L / 2 + p.getRadius())) {
            intersectYTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();
            intersectionX = p.getX() + (p.getVx() * intersectYTime);
            if(intersectionX + p.getRadius() >= sideLength) {
                nextVyTime = intersectYTime;
                leftSide = false;
            }
            else {
                nextVyTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
                leftSide = true;
            }
        } else {
            nextVyTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
            leftSide = true;
        }

        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setLeftSideCollision(leftSide);
        }
    }

    private void setTimeLeftSquareBottomSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double nextVyTime;
        double intersectYTime;
        double intersectionX;
        boolean leftSide;

        if(p.getX() + p.getRadius() >= sideLength && (p.getY() <= sideLength / 2 + L / 2 - p.getRadius()) && (p.getY() >= sideLength / 2 - L / 2 + p.getRadius())) {
            intersectYTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();
            intersectionX = p.getX() + (p.getVx() * intersectYTime);
            if(intersectionX + p.getRadius() >= sideLength) {
                nextVyTime = intersectYTime;
                leftSide = false;
            }
            else {
                nextVyTime = (0 + p.getRadius() - p.getY()) / p.getVy();
                leftSide = true;
            }
        } else {
            nextVyTime = (0 + p.getRadius() - p.getY()) / p.getVy();
            leftSide = true;
        }

        if(nextVyTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
            nextEventTime.setSlitCollision(false);
            nextEventTime.setLeftSideCollision(leftSide);
        }
    }

    private void setTimeRightSquareLeftSide(Particle p, double sideLength, EventTime nextEventTime, double L) {
        double nextVxTime = (sideLength - p.getX()) / p.getVx();
        double intersectionY = p.getY() + (p.getVy() * nextVxTime);
        boolean isSlit;

        if((intersectionY > sideLength / 2 - L / 2 + p.getRadius()) && (intersectionY < sideLength / 2 + L / 2 - p.getRadius())) {
            isSlit = true;
        } else {
            nextVxTime = Double.MAX_VALUE;
            isSlit = false;
        }

        if(nextVxTime == 0) {
            isSlit = false;
            nextVxTime = Double.MAX_VALUE;
        }
        if(nextVxTime < nextEventTime.getTime()) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
            nextEventTime.setSlitCollision(isSlit);
            nextEventTime.setLeftSideCollision(false);
        }
    }

    private double getWallCollisionTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = Double.MAX_VALUE;
        EventTime currentEventTime = new EventTime(Double.MAX_VALUE, false, false, false);
        for(Particle p : particles) {
            if(p.isLeft()) {
                if(p.getVx() > 0) {
                    setTimeLeftSquareRightSide(p,sideLength,currentEventTime, L);
                } else {
                    setTimeLeftSquareLeftSide(p, currentEventTime);
                }
                if(p.getVy() > 0) {
                    setTimeLeftSquareTopSide(p, sideLength, currentEventTime, L);
                } else {
                    setTimeLeftSquareBottomSide(p, sideLength, currentEventTime, L);
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
                this.isLeftSideCollision = currentEventTime.isLeftSideCollision();
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

    public void evolveTillEvent(List<Particle> particles, double nextEvent, double L, double sideLength) {
        double newX;
        double newY;
        for(Particle p : particles) {
            newX = p.getX() + nextEvent*p.getVx();
            newY = p.getY() + nextEvent*p.getVy();
            if(Math.abs(newY-(0 + p.getRadius())) < EPSILON)
                newY = 0 + p.getRadius();
            if(Math.abs(newY-(sideLength-p.getRadius())) < EPSILON)
                newY = sideLength-p.getRadius();
            if(Math.abs(newX-(sideLength-p.getRadius())) < EPSILON && !(newY >= sideLength/2 - L/2 + p.getRadius() && newY <= sideLength/2 + L/2 - p.getRadius()))
                newX = sideLength - p.getRadius();
            if(Math.abs(newX-(0+p.getRadius())) < EPSILON)
                newX = 0 + p.getRadius();
            p.setX(newX);
            p.setY(newY);
        }
    }

    public void resolveCollisionAndAddImpulse(double sideLength) {
        double impulseVelocity;
        if(!this.isSlitCollision) {
            if(this.wallCollision) {
                if(isHorizontalCollision) {
                    this.wallCollisionParticle.setVy(-this.wallCollisionParticle.getVy());
                    impulseVelocity = this.wallCollisionParticle.getVy();
                }
                else {
                    this.wallCollisionParticle.setVx(-this.wallCollisionParticle.getVx());
                    impulseVelocity = this.wallCollisionParticle.getVx();
                }
                if(this.isLeftSideCollision) {
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
