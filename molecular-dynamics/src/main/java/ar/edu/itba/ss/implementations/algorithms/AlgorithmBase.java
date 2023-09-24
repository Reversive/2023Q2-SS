package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;

public abstract class AlgorithmBase implements Algorithm {
    protected final double K;
    protected final double gamma;
    protected AlgorithmType type;

    public AlgorithmBase(double k, double gamma, AlgorithmType type) {
        this.K = k;
        this.gamma = gamma;
        this.type = type;
    }

    @Override
    public String getName() {
        return type.name();
    }
}
