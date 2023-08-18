package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Particle;

import java.util.List;

public interface DistanceMethod {
    void setNeighbours();
    void placeParticles();
    String getName();
}
