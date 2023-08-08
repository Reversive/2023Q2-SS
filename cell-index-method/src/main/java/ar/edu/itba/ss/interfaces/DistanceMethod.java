package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

public interface DistanceMethod {
    List<Particle> findNeighbours();
}
