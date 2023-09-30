
package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.CircleSystem;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle_S2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SecondSystem {

    private static final int TF = 180;
    private static final int STEPS = 1; // TODO
    private static final int N = 25;
    private static final double r = 2.25;
    private static final double m = 25;
    private static final double R = 21.49;
    private static final double minRad = 0.0;
    private static final double maxRad = 2 * Math.PI;
    private static final double minUi = 9.0;
    private static final double maxUi = 12.0;
//    static BigDecimal MIN_DT = BigDecimal.valueOf(0.00001);
    static BigDecimal MIN_DT = BigDecimal.valueOf(0.1); // CAMBIAR ESTO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    static BigDecimal MAX_DT = BigDecimal.valueOf(0.1);
    static BigDecimal DT = BigDecimal.valueOf(0.1);

    public static void main(String[] args) {

        // TODO REVISAR CUANDO LA COLISION ENTRE DOS PARTICULAS SE DA CON EL 0 ENTRE MEDIO

        // TODO Punto 2.1, 30 particulas entran justas, hay que hardcodearlas
        // TODO En el 2.2 hay que hacer un grafico donde se vean las 3 distribuciones y con N grande hace picos, pero con N bajo esta mas distribuida

        List<Particle_S2> immutableParticles = new ArrayList<>();

        double nextPosition;
        double ui;
        for(int i = 0; i < N; i++) {
            do {
                nextPosition = minRad + (Math.random() * (maxRad - minRad));
                if(nextPosition == maxRad)
                    nextPosition = 0;
            } while(isColliding(nextPosition, immutableParticles));
            ui = minUi + (Math.random() * (maxUi - minUi));
            immutableParticles.add(new Particle_S2.Builder()
                            .withId(i)
                            .withUi(ui)
                            .withMass(m)
                            .withRadius(r)
                            .withVelocity(ui/R)
                            .withPosition(nextPosition)
                            .withAcceleration(0)
                    .build());
        }
        List<Particle_S2> particles;

        AlgorithmType currentAlgorithm = AlgorithmType.GEAR_PREDICTOR_CORRECTOR_S2;
        Algorithm_S2 algorithm = AlgorithmFactory.buildAlgorithmS2(currentAlgorithm, 0,0);
        CircleSystem circleSystem = new CircleSystem(algorithm, TF);
        int i = 0;
        for(BigDecimal dt = MIN_DT; dt.compareTo(MAX_DT) <= 0; dt = dt.multiply(DT)) {
            particles = new ArrayList<>(immutableParticles);
            circleSystem.simulate(dt.doubleValue(), particles, STEPS*Math.pow(10,i++));
        }



    }

    private static boolean isColliding(double position, List<Particle_S2> particles) {
        double newArc = position * R;
        double currentArc;
        for(Particle_S2 p : particles) {
            currentArc = p.getPosition() * R;
            if(Math.min(Math.abs(currentArc-newArc), (maxRad*R) - Math.abs(currentArc-newArc)) <= 2*r)
                return true;
        }
        return false;
    }

}
