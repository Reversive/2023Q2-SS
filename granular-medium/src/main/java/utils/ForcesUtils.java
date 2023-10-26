package utils;

import models.Vector;

public class ForcesUtils {

    public static final double KN = 250;
    public static final double KT = 2 * KN;
    public static final double Y = 2.5;
    public static final double U = 0.7;
    public static final double G = -5;

    private static double getNormalForceValue(double superposition) {
        return (-KN * superposition); //  - Y*superposition TODO SUPERPOSITION DERIVADO
    }

    public static Vector getNormalForce(double superposition, Vector normalVersor) {

        double force = getNormalForceValue(superposition);

        return normalVersor.scalarProduct(force);
    }

    private static double getTangencialForceValue(double superposition, double relativeTangencialVelocity) {
        return -KT * (superposition) * (relativeTangencialVelocity); //TODO HACERLO CON T1
    }

    public static Vector getTangencialForce(double superposition, Vector relativeTangencialVelocity, Vector normalVersor) {

        Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());

        double force = getTangencialForceValue(superposition, relativeTangencialVelocity.dotProduct(tangencialVersor));

        return tangencialVersor.scalarProduct(force);
    }

    public static Vector getWallForce(double superposition, Vector relativeTangencialVelocity, Vector normalVersor) {

        Vector tan = new Vector(-normalVersor.getY(), normalVersor.getX());

        double forceT = getTangencialForceValue(superposition, relativeTangencialVelocity.dotProduct(tan));
        double forceN = getNormalForceValue(superposition);
        return normalVersor.scalarProduct(forceN).sum(tan.scalarProduct(forceT));
    }

}
