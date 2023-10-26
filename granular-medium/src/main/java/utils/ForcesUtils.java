package utils;

import models.Vector;

public class ForcesUtils {

    public static final double KN = 250;
    public static final double KT = 2 * KN;
    public static final double Y = 2.5;
    public static final double U = 0.7;
    public static final double G = -5;

    private static double getNormalForceValue(double superposition, double normalRelativeVelocity) {
        return (-KN * superposition)  - Y * normalRelativeVelocity;
    }

    public static Vector getNormalForce(double superposition, Vector normalVersor, Vector relativeVelocity) {

        double normalRelativeVelocity = relativeVelocity.scalarProduct(normalVersor);

        double force = getNormalForceValue(superposition, normalRelativeVelocity);

        return normalVersor.byScalarProduct(force);
    }

    public static double getNormalForceMagnitude(double superposition, Vector normalVersor, Vector relativeVelocity) {
        double normalRelativeVelocity = relativeVelocity.scalarProduct(normalVersor);
        return Math.abs((-KN * superposition)  - Y * normalRelativeVelocity);
    }

    private static double getTangencialForceValue(double superposition, double relativeTangencialVelocity, double normalForceMagnitude) {

        double possibleForce1 = -U * normalForceMagnitude * Math.signum(relativeTangencialVelocity);

        double possibleForce2 = -KT * superposition * relativeTangencialVelocity;

        return Math.min(possibleForce1, possibleForce2);
    }

    public static Vector getTangencialForce(double superposition, Vector relativeVelocity, Vector normalVersor, double normalForceMagnitude) {

        Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());

        double force = getTangencialForceValue(superposition, relativeVelocity.scalarProduct(tangencialVersor), normalForceMagnitude);

        return tangencialVersor.byScalarProduct(force);
    }

    public static Vector getWallForce(double superposition, Vector particleVelocity, Vector normalVersor) {
        double normalRelativeVelocity = normalVersor.scalarProduct(particleVelocity);
        Vector tangencialVersor = new Vector(-normalVersor.getY(), normalVersor.getX());
        double forceN = getNormalForceValue(superposition, normalRelativeVelocity);
        double forceT = getTangencialForceValue(superposition, particleVelocity.scalarProduct(tangencialVersor), forceN);
        return normalVersor.byScalarProduct(forceN).sum(tangencialVersor.byScalarProduct(forceT));
    }

}
