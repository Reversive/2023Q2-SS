package ar.edu.itba.ss.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final int particleAmount;
    private final int sideLength;
    private final List<Particle> particles;
    private final double icRadius;
    private final int matrixSize;
    private final boolean usePeriodicContour;
    private final boolean useBruteForce;

    public Context(int particleAmount, int sideLength, List<Particle> particles, double icRadius, int matrixSize, boolean usePeriodicContour, boolean useBruteForce) {
        this.particleAmount = particleAmount;
        this.sideLength = sideLength;
        this.particles = particles;
        this.icRadius = icRadius;
        this.matrixSize = matrixSize;
        this.usePeriodicContour = usePeriodicContour;
        this.useBruteForce = useBruteForce;
    }

    public int getParticleAmount() {
        return particleAmount;
    }

    public int getSideLength() {
        return sideLength;
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getIcRadius() {
        return icRadius;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public boolean isUsePeriodicContour() {
        return usePeriodicContour;
    }

    public boolean isUseBruteForce() {
        return useBruteForce;
    }
}
