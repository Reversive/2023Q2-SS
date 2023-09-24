package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmBeeman extends AlgorithmBase implements Algorithm {
    public AlgorithmBeeman(double k, double gamma, AlgorithmType type) {
        super(k, gamma, type);
    }

    public AlgorithmBeeman(double K, double gamma) {
        super(K, gamma, AlgorithmType.BEEMAN);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        return null;
    }
}
