package utils;

import models.Vector;
import models.Particle;

import java.util.ArrayList;
import java.util.List;

public class ParticleUtils {

    private static final double MAXRAD = 1.15;
    private static final double MINRAD = 0.85;
    private static final double MASS = 1;
    private static final double N = 200;
    private static final double L = 70;
    private static final double MIN_RESET_Y = 40;
    private static final double W = 20;

    public static List<Particle> generateParticles(double dt) {
        List<Particle> particles = new ArrayList<>();
        double x, y, radius;

        for (int i = 0; i < N; i++) {
            radius = MINRAD + Math.random() * (MAXRAD - MINRAD);

            do {
                x = radius + Math.random() * (W - 2 * radius);
                y = radius + Math.random() * (L - 2 * radius);
            } while(isColliding(x, y, radius, particles));

            particles.add(new Particle(i, new Vector(x, y), radius, MASS, dt));
        }
        return particles;
    }

    public static List<Particle> regenerateParticle(List<Particle> particles, Particle current) {
        double x, y;

        do {
            x = current.getRadius() + Math.random() * (W - 2 * current.getRadius());
            y = current.getRadius() + MIN_RESET_Y + Math.random() * (L - MIN_RESET_Y - current.getRadius());
        } while(isColliding(x, y, current.getRadius(), particles));

        current.getPosition().setX(x);
        current.getPosition().setY(y);

        return particles;
    }


    private static boolean isColliding(double x, double y, double radius, List<Particle> particles) {
        for(Particle p : particles) {
            double distance = getDistance(x, y, p);
            if(distance < radius + p.getRadius())
                return true;
        }
        return false;
    }

    private static double getDistance(Particle p1, Particle p2) {
        return Math.sqrt(Math.pow(p2.getPosition().getX() - p1.getPosition().getX(), 2) + Math.pow(p2.getPosition().getY() - p1.getPosition().getY(), 2));
    }

    private static double getDistance(double x, double y, Particle p2) {
        return Math.sqrt(Math.pow(p2.getPosition().getX() - x, 2) + Math.pow(p2.getPosition().getY() - y, 2));
    }
}