package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.Oscillator;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;

import java.math.BigDecimal;

public class DampedPointOscillator {
    static final double M = 70.;
    static final double K = Math.pow(10,4);
    static final double GAMMA = 100.0;
    static final int TF = 5;
    static final int STEPS = 2;
    static BigDecimal DT = BigDecimal.valueOf(0.0001);
    static BigDecimal MIN_DT = BigDecimal.valueOf(0.00001);
    static BigDecimal MAX_DT = BigDecimal.valueOf(0.01);
    public static void main(String[] args) {
        AlgorithmType currentAlgorithm = AlgorithmType.BEEMAN;
        Algorithm algorithm = AlgorithmFactory.buildAlgorithm(currentAlgorithm, K, GAMMA);
        Oscillator dampedPointOscillator = new Oscillator(algorithm, TF, STEPS);

        for(BigDecimal dt = MIN_DT; dt.compareTo(MAX_DT) <= 0; dt = dt.add(DT)) {
            dampedPointOscillator.simulate(dt.doubleValue(), GAMMA, K, M);
        }

    }
}
