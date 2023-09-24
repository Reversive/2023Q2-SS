package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.Oscillator;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;

public class DampedPointOscillator {
    static final double M = 70.;
    static final double K = Math.pow(10,4);
    static final double GAMMA = 100.0;
    static final int TF = 5;
    static final int STEPS = 2;
    public static void main(String[] args) {
        Algorithm algorithm = AlgorithmFactory.buildAlgorithm(AlgorithmType.VERLET, K, GAMMA);
        Oscillator dampedPointOscillator = new Oscillator(algorithm, TF, STEPS);
        // change dt
        double dt = 0.01;
        dampedPointOscillator.simulate(dt, GAMMA, K, M);
    }
}
