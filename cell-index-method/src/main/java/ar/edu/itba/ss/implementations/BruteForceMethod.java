package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

public class BruteForceMethod implements DistanceMethod {
    private final Context context;
    private final boolean shouldUsePeriodicContour;

    public BruteForceMethod(Context context, boolean shouldUsePeriodicContour) {
        this.context = context;
        this.shouldUsePeriodicContour = shouldUsePeriodicContour;
    }

    @Override
    public List<Particle> findNeighbours() {
        List<Particle> particles = context.getParticles();
        for (Particle p1 : particles) {
            for (Particle p2 : particles) {
                if (p1.equals(p2) || p1.getNeighbours().contains(p2)) continue;
                double distance = shouldUsePeriodicContour ? p1.getContourDistanceTo(p2, context.getSideLength()) : p1.getDistanceTo(p2);
                if (distance < context.getIcRadius()) {
                    p1.addNeighbour(p2);
                    p2.addNeighbour(p1);
                }
            }
        }
        return particles;
    }
    @Override
    public String getName() {
        return "Brute Force";
    }
}
