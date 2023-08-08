package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;

public class DistanceMethodFactory {
    public static DistanceMethod buildMethod(boolean shouldBruteForce, boolean periodicContour, Context context) {
        if(!shouldBruteForce) return new CellIndexMethod(context, periodicContour);
        return new BruteForceMethod(context, periodicContour);
    }
}
