package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.EventTime;
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

        if(auxEventTime < nextEventTime && auxEventTime > 0) {
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

        double intersectionTime = (sideLength - p.getX()) / p.getVx();

        if(Double.compare(intersectionTime, 0) < 0)
            return false;
        
        double intersectionY = p.getY() + (p.getVy() * intersectionTime);

        if((intersectionY > sideLength / 2 - L / 2 + p.getRadius()) && (intersectionY < sideLength / 2 + L / 2 - p.getRadius()))
            return true;

        return false;
    }

    private void setTimeRightSquareRightSide(Particle p, double sideLength, EventTime nextEventTime) {
        double nextVxTime = (2 * sideLength - p.getRadius() - p.getX()) / p.getVx();
        if(nextVxTime < nextEventTime.getTime() && nextVxTime > 0) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
        }
    }

    private void setTimeRightSquareTopSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 + L / 2) - p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime() && nextVyTime > 0) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
        }
    }

    private void setTimeRightSquareBottomSide(Particle p, double L, double sideLength, EventTime nextEventTime) {
        double nextVyTime = ((sideLength / 2 - L / 2) + p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime() && nextVyTime > 0) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
        }
    }

    private void setTimeLeftSquareLeftSide(Particle p, EventTime nextEventTime) {
        double nextVxTime = (0 + p.getRadius() - p.getX()) / p.getVx();
        if(nextVxTime < nextEventTime.getTime() && nextVxTime > 0) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
        }
    }

    private void setTimeLeftSquareRightSide(Particle p, double sideLength, EventTime nextEventTime) {
        double nextVxTime = (sideLength - p.getRadius() - p.getX()) / p.getVx();
        if(nextVxTime < nextEventTime.getTime() && nextVxTime > 0) {
            nextEventTime.setTime(nextVxTime);
            nextEventTime.setHorizontal(false);
        }
    }

    private void setTimeLeftSquareTopSide(Particle p, double sideLength, EventTime nextEventTime) {
        double nextVyTime = (sideLength - p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime() && nextVyTime > 0) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
        }
    }

    private void setTimeLeftSquareBottomSide(Particle p, EventTime nextEventTime) {
        double nextVyTime = (0 + p.getRadius() - p.getY()) / p.getVy();
        if(nextVyTime < nextEventTime.getTime() && nextVyTime > 0) {
            nextEventTime.setTime(nextVyTime);
            nextEventTime.setHorizontal(true);
        }
    }

    private double getWallCollisionTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = Double.MAX_VALUE;
        EventTime currentEventTime = new EventTime(Double.MAX_VALUE, false);
        for(Particle p : particles) {
            if(checkCrossSlit(p, sideLength, L)) {
                if(isInsideLeftSquare(p.getX(), sideLength)) {
                    if(p.getVx() > 0) {
                        setTimeRightSquareRightSide(p, sideLength, currentEventTime);
                    } else if(p.getVx() < 0) {
                        throw new RuntimeException("Esto no puede pasar");
                    }
                    if(p.getVy() > 0) {
                        setTimeRightSquareTopSide(p, L, sideLength, currentEventTime);
                    } else if(p.getVy() < 0) {
                        setTimeRightSquareBottomSide(p, L, sideLength, currentEventTime);
                    }
                } else {
                    if(p.getVx() > 0) {
                        throw new RuntimeException("Esto no puede pasar");
                    } else if(p.getVx() < 0) {
                        setTimeLeftSquareLeftSide(p, currentEventTime);
                    }
                    if(p.getVy() > 0) {
                        setTimeLeftSquareTopSide(p, sideLength, currentEventTime);
                    } else if(p.getVy() < 0) {
                        setTimeLeftSquareBottomSide(p, currentEventTime);
                    }
                }
            } else {
                if(isInsideLeftSquare(p.getX(), sideLength)) {
                    if(p.getVx() > 0) {
                        setTimeLeftSquareRightSide(p, sideLength, currentEventTime);
                    } else if(p.getVx() < 0) {
                        setTimeLeftSquareLeftSide(p, currentEventTime);
                    }
                    if(p.getVy() > 0) {
                        setTimeLeftSquareTopSide(p, sideLength, currentEventTime);
                    } else if(p.getVy() < 0) {
                        setTimeLeftSquareBottomSide(p, currentEventTime);
                    }
                } else {
                    if(p.getVx() > 0) {
                        setTimeRightSquareRightSide(p, sideLength, currentEventTime);
                    }
                    if(p.getVy() > 0) {
                        setTimeRightSquareTopSide(p, L, sideLength, currentEventTime);
                    } else if(p.getVy() < 0) {
                        setTimeRightSquareBottomSide(p, L, sideLength, currentEventTime);
                    }
                }
            }
            if(currentEventTime.getTime() < nextEventTime) {
                nextEventTime = currentEventTime.getTime();
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
            if(p.getX() < 0.0015)
                p.setX(0.0015);
            if(p.getX() > 0.09-0.0015 && (p.getY() <= 0.045 - L/2 || p.getY() >= 0.045 + L/2))
                p.setX(0.09-0.0015);
            p.setY(p.getY() + nextEvent*p.getVy());
        }
    }

    public void resolveCollisionAndAddImpulse(double sideLength) {
        double impulseVelocity;
        if(this.wallCollision) {
            if(isHorizontalCollision) {
                this.wallCollisionParticle.setVy(-this.wallCollisionParticle.getVy());
                impulseVelocity = this.wallCollisionParticle.getVy();
            }
            else {
                this.wallCollisionParticle.setVx(-this.wallCollisionParticle.getVx());
                impulseVelocity = this.wallCollisionParticle.getVx();
            }
            if(isInsideLeftSquare(this.wallCollisionParticle.getX(), sideLength)) {
                this.leftSideImpulse += 2 * Math.abs(impulseVelocity) * this.wallCollisionParticle.getMass();
            } else {
                this.rightSideImpulse += 2 * Math.abs(impulseVelocity) * this.wallCollisionParticle.getMass();
            }
//            wallCollisionParticle.setDirection(Math.atan2(wallCollisionParticle.getVy(), wallCollisionParticle.getVx()));
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
