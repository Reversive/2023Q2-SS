package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.Algorithm;

public class Oscillator {
    private final Algorithm algorithm;
    private final double tf;
    private final int steps;

    public Oscillator(Algorithm algorithm, double tf, int steps) {
        this.algorithm = algorithm;
        this.tf = tf;
        this.steps = steps;
    }

    public void simulate() {
        // to-do
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public double getTf() {
        return tf;
    }

    public int getSteps() {
        return steps;
    }
}
