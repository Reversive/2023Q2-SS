package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;

public class AlgorithmFactory {
    public static Algorithm buildBeeman(double K, double gamma) {
        return new AlgorithmBeeman(K, gamma);
    }

    public static Algorithm buildGear(double K, double gamma) {
        return new AlgorithmGear(K, gamma);
    }

    public static Algorithm buildVerlet(Algorithm algorithm, double K, double gamma) {
        return new AlgorithmVerlet(algorithm, K, gamma);
    }

    public static Algorithm buildAlgorithm(AlgorithmType type, double k, double gamma, Algorithm euler) {
        Algorithm algorithm = null;
        switch (type) {
            case BEEMAN:
                algorithm = AlgorithmFactory.buildBeeman(k, gamma);
                break;
            case VERLET:
                algorithm = AlgorithmFactory.buildVerlet(euler, k, gamma);
                break;
            case GEAR_PREDICTOR_CORRECTOR:
                algorithm = AlgorithmFactory.buildGear(k, gamma);
                break;
        }
        return algorithm;
    }
}
