package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmVerlet extends AlgorithmBase implements Algorithm {
    public AlgorithmVerlet(double k, double gamma, AlgorithmType type) {
        super(k, gamma, type);
    }

    public AlgorithmVerlet(double k, double gamma) {
        super(k, gamma, AlgorithmType.VERLET);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        return null;
    }
}
