package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Particle;

public interface Algorithm {
    Particle update(Particle previous, Particle current, double deltaTime, double currentTime);
    String getName();
}
