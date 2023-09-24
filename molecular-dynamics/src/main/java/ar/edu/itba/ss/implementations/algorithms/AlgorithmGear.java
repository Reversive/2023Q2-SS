package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmGear extends AlgorithmBase implements Algorithm {
    public AlgorithmGear(double k, double gamma, AlgorithmType type) {
        super(k, gamma, type);
    }

    public AlgorithmGear(double k, double gamma) {
        super(k, gamma, AlgorithmType.GEAR_PREDICTOR_CORRECTOR);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        return null;
    }
}
