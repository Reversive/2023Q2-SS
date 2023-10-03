
package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.CircleSystem;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmVerlet_s2;
import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle_S2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SecondSystem {

    private static final int TF = 180;
    private static final int STEPS = 1;
    private static final int N = 25;
    private static final double r = 2.25;
    private static final double m = 25;
    private static final double R = 21.49;
    private static final double minPos = 0.0;
    private static final double maxPos = 135;
    private static final double minUi = 9.0;
    private static final double maxUi = 12.0;
    static BigDecimal MIN_DT = BigDecimal.valueOf(0.00001);
    static BigDecimal MAX_DT = BigDecimal.valueOf(0.1);

    public static void main(String[] args) {
        // TODO En el 2.2 hay que hacer un grafico donde se vean las 3 distribuciones y con N grande hace picos, pero con N bajo esta mas distribuida

        List<Particle_S2> immutableParticles = new ArrayList<>();

        double nextPosition;
        double ui;
            for (int i = 0; i < N; i++) {
                do {
                    if(N <= 22) {
                        nextPosition = (Math.random() * (maxPos - minPos));
                    } else {
                        nextPosition = maxPos * i / N ;
                    }
                    if (nextPosition == maxPos)
                        nextPosition = 0;
                } while (isColliding(nextPosition, immutableParticles));
                ui = minUi + (Math.random() * (maxUi - minUi));
                immutableParticles.add(new Particle_S2.Builder()
                        .withId(i)
                        .withUi(ui)
                        .withMass(m)
                        .withRadius(r)
                        .withVelocity(ui)
                        .withPosition(nextPosition)
                        .withAngle(nextPosition)
                        .withAcceleration(0)
                        .build());
        }
        System.out.println("posicionadas");
        List<Particle_S2> particles;

        AlgorithmType currentAlgorithm = AlgorithmType.GEAR_PREDICTOR_CORRECTOR_S2;
        Algorithm_S2 algorithm = AlgorithmFactory.buildAlgorithmS2(currentAlgorithm, 0,0);
        CircleSystem circleSystem = new CircleSystem(algorithm, TF);
        int i = 4;
        //for(BigDecimal dt = MIN_DT; dt.compareTo(MAX_DT) <= 0; dt = dt.multiply(BigDecimal.TEN)) {
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.00001, particles, STEPS*Math.pow(10,4));
        particles = new ArrayList<>(immutableParticles);
        circleSystem.simulate(0.0001, particles, STEPS*Math.pow(10,3));
        particles = new ArrayList<>(immutableParticles);
        circleSystem.simulate(0.001, particles, STEPS*Math.pow(10,2));
        particles = new ArrayList<>(immutableParticles);
        circleSystem.simulate(0.01, particles, STEPS*Math.pow(10,1));
        particles = new ArrayList<>(immutableParticles);
        circleSystem.simulate(0.1, particles, STEPS*Math.pow(10,0));
        //}

    }

    private static boolean isColliding(double position, List<Particle_S2> particles) {
        for(Particle_S2 p : particles) {
            double distance = Math.min(maxPos - Math.abs(p.getAngle() - position) , Math.abs(p.getAngle() - position));
            if(distance < 2*r)
                return true;
        }
        return false;
    }

}
