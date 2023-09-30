package ar.edu.itba.ss.interfaces;

import ar.edu.itba.ss.models.Particle_S2;

import java.util.Map;

public interface Algorithm_S2 {
    Particle_S2 update(Map<Integer, Particle_S2> previousMap, Particle_S2 current, double deltaTime, double currentTime);
    String getName();
}
