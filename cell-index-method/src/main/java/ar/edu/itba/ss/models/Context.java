package ar.edu.itba.ss.models;

import java.util.List;

public class Context {
    private int particleAmount;
    private double sideLength;
    private List<Particle> particles;
    private double icRadius;
    private int matrixSize;

    public Context(double icRadius, int matrixSize) {
        this.icRadius = icRadius;
        this.matrixSize = matrixSize;
    }

    public int getParticleAmount() {
        return particleAmount;
    }

    public double getSideLength() {
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

    public void setParticleAmount(int particleAmount) {
        this.particleAmount = particleAmount;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public void setParticles(List<Particle> particles) {
        this.particles = particles;
    }

    public void setIcRadius(double icRadius) {
        this.icRadius = icRadius;
    }

    public void setMatrixSize(int matrixSize) {
        this.matrixSize = matrixSize;
    }

}
