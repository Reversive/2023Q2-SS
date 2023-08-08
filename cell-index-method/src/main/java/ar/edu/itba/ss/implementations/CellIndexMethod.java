package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

public class CellIndexMethod implements DistanceMethod {
    private final Context context;
    private final boolean shouldUsePeriodicContour;

    public CellIndexMethod(Context context, boolean shouldUsePeriodicContour) {
        this.context = context;
        this.shouldUsePeriodicContour = shouldUsePeriodicContour;
    }

    @Override
    public List<Particle> findNeighbours() {
        return null;
    }
}
