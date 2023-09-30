package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;

public class AlgorithmFactory {
    public static Algorithm buildBeeman(double K, double gamma) {
        return new AlgorithmBeeman(K, gamma);
    }

    public static Algorithm buildGear(double K, double gamma) {
        return new AlgorithmGear(K, gamma);
    }

    public static Algorithm_S2 buildGearS2() {
        return new AlgorithmGear_S2();
    }

    public static Algorithm buildVerlet(double K, double gamma) {
        return new AlgorithmVerlet(K, gamma);
    }

    public static Algorithm buildAlgorithm(AlgorithmType type, double k, double gamma) {
        Algorithm algorithm = null;
        switch (type) {
            case BEEMAN:
                algorithm = AlgorithmFactory.buildBeeman(k, gamma);
                break;
            case VERLET:
                algorithm = AlgorithmFactory.buildVerlet(k, gamma);
                break;
            case GEAR_PREDICTOR_CORRECTOR:
                algorithm = AlgorithmFactory.buildGear(k, gamma);
                break;
        }
        return algorithm;
    }

    public static Algorithm_S2 buildAlgorithmS2(AlgorithmType type, double k, double gamma) {
        return AlgorithmFactory.buildGearS2();
    }
}
