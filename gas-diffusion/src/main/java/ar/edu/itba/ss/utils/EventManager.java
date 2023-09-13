package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Particle;

import java.util.List;

public class EventManager {

    public static double getNextEventTime(List<Particle> particles, double L, double sideLength) {
        double nextEventTime = getWallCollisionTime(particles, L, sideLength);
        double auxEventTime = getParticlesCollisionTime(particles);

        if(auxEventTime < nextEventTime)
            nextEventTime = auxEventTime;

        return nextEventTime;
    }

    private static boolean isInsideLeftSquare(double x, double sideLength) {
        return x < sideLength;
    }

    private static boolean checkCrossSlit(Particle p, double sideLength, double L) {

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

    private static double getWallCollisionTime(List<Particle> particles, double L, double sideLength) {
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
                } else {
                    currentTime = (0 + p.getRadius() - p.getX()) / p.getVx();
                }

                if (currentTime < nextEventTime)
                    nextEventTime = currentTime;

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

                if (currentTime < nextEventTime)
                    nextEventTime = currentTime;

            } else {
                if (p.getVx() > 0) {
                    currentTime = (2 * sideLength - p.getRadius() - p.getX()) / p.getVx();
                } else {
                    currentTime = (0 + p.getRadius() - p.getX()) / p.getVx();
                }

                if (currentTime < nextEventTime)
                    nextEventTime = currentTime;

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

                if (currentTime < nextEventTime)
                    nextEventTime = currentTime;
            }
        }
        return nextEventTime;
    }

    private static double getParticlesCollisionTime(List<Particle> particles) {
        double nextEventTime = Double.MAX_VALUE;
        double currentTime;
        //TODO: ver si podemos hace run cell index method para el cuadrado principal
        for(Particle p : particles) {


        }

        return nextEventTime;
    }


}
