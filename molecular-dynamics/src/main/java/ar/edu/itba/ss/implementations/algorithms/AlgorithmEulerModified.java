package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

public class AlgorithmEulerModified extends AlgorithmEuler {
    public AlgorithmEulerModified(double k, double gamma, AlgorithmType type) {
        super(k, gamma, type);
    }

    public AlgorithmEulerModified(double k, double gamma) {
        super(k, gamma, AlgorithmType.EULER_MODIFIED);
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        if(current == null) throw new NullPointerException("This can't happen");
        Particle next = new Particle.Builder()
                .cloneParticle(current)
                .build();
        updateEuler(current, next, deltaTime, true);
        return next;
    }
}
