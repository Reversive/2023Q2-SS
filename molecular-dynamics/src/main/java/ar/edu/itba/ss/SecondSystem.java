package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle_S2;

import java.util.ArrayList;
import java.util.List;

public class SecondSystem {

    private static final int N = 25;
    private static final double r = 2.25;
    private static final double m = 25;
    private static final double R = 21.49;
    private static final int REACTION_TIME = 1;
    private static final double minRad = 0.0;
    private static final double maxRad = 2 * Math.PI;
    private static final double minUi = 9.0;
    private static final double maxUi = 12.0;

    public static void main(String[] args) {

        List<Particle_S2> immutableParticles = new ArrayList<>();

        double nextPosition;
        double ui;
        for(int i = 0; i < N; i++) {
            do {
                nextPosition = minRad + (Math.random() * (maxRad - minRad));
                if(nextPosition == maxRad)
                    nextPosition = 0;
            } while(isColliding(nextPosition, immutableParticles));
            ui = minUi + (Math.random() * (maxUi - minUi));;
            immutableParticles.add(new Particle_S2.Builder()
                            .withUi(ui)
                            .withMass(m)
                            .withRadius(r)
                            .withVelocity(ui/R)
                            .withPosition(nextPosition)
//                            .withAcceleration() TODO ESTO
                    .build());
        }

        AlgorithmType currentAlgorithm = AlgorithmType.GEAR_PREDICTOR_CORRECTOR_S2;
        Algorithm algorithm = AlgorithmFactory.buildAlgorithm(currentAlgorithm, 0,0);

    }

    private static boolean isColliding(double position, List<Particle_S2> particles) {
        double newArc = position * R;
        double currentArc;
        for(Particle_S2 p : particles) {
            currentArc = p.getPosition() * R;
            if(Math.min(Math.abs(currentArc-newArc), maxRad - Math.abs(currentArc-newArc)) < 2*r)
                return false;
        }
        return true;
    }
}
