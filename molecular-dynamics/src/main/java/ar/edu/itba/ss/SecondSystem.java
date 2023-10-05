
package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.CircleSystem;
import ar.edu.itba.ss.implementations.algorithms.AlgorithmFactory;
import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle_S2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SecondSystem {

    private static final int TF = 800;
    private static final int STEPS = 1;
    private static final int N = 5;
    private static final double r = 2.25;
    private static final double m = 25;
    private static final double R = 21.49;
    private static final double minRad = 0.0;
    private static final double maxRad = 2 * Math.PI;
    private static final double minUi = 9.0;
    private static final double maxUi = 12.0;
    static BigDecimal MIN_DT = BigDecimal.valueOf(0.00001);
    static BigDecimal MAX_DT = BigDecimal.valueOf(0.1);
    private static final boolean ORDERED = false;

    public static void main(String[] args) {

        List<Particle_S2> immutableParticles = new ArrayList<>();

        double nextPosition;
        double ui;
        if(!ORDERED) {

            if(N <= 22) {
                for (int i = 0; i < N; i++) {
                    do {
                        nextPosition = minRad + (Math.random() * (maxRad - minRad));
                        if (nextPosition == maxRad)
                            nextPosition = 0;
                    } while (isColliding(nextPosition, immutableParticles));
                    ui = minUi + (Math.random() * (maxUi - minUi));
                    immutableParticles.add(new Particle_S2.Builder()
                            .withId(i)
                            .withUi(ui)
                            .withMass(m)
                            .withRadius(r)
                            .withVelocity(ui / R)
                            .withPosition(nextPosition)
                            .withAngle(nextPosition)
                            .withAcceleration(0)
                            .withX3(0)
                            .withX4(0)
                            .withX5(0)
                            .build());
                }
            } else {
                for (int i = 0; i < N; i++) {
                    nextPosition = maxRad * i / N ;
                    ui = minUi + (Math.random() * (maxUi - minUi));
                    immutableParticles.add(new Particle_S2.Builder()
                            .withId(i)
                            .withUi(ui)
                            .withMass(m)
                            .withRadius(r)
                            .withVelocity(ui / R)
                            .withPosition(nextPosition)
                            .withAngle(nextPosition)
                            .withAcceleration(0)
                            .withX3(0)
                            .withX4(0)
                            .withX5(0)
                            .build());
                }
            }

        } else {

            for (int i = 0; i < N; i++) {
                ui = minUi + (Math.random() * (maxUi - minUi));
                immutableParticles.add(new Particle_S2.Builder()
                        .withId(i)
                        .withUi(ui)
                        .withMass(m)
                        .withRadius(r)
                        .withVelocity(ui / R)
                        .withPosition(0)
                        .withAngle(0)
                        .withAcceleration(0)
                        .withX3(0)
                        .withX4(0)
                        .withX5(0)
                        .build());
            }

            immutableParticles = immutableParticles.stream()
                    .sorted(Comparator.comparingDouble(Particle_S2::getUi))
                    .collect(Collectors.toList());

            List<Double> positions = new ArrayList<>();

            for(int i = 0; i < immutableParticles.size(); i++) {
                if(N <= 22) {
                    do {
                        nextPosition = minRad + (Math.random() * (maxRad - minRad));
                        if (nextPosition == maxRad)
                            nextPosition = 0;
                    } while (isCollidingOrdered(nextPosition, positions));
                } else {
                    nextPosition = maxRad * i / N;
                }
                positions.add(nextPosition);
            }

            positions = positions.stream().sorted().collect(Collectors.toList());

            for(int i = 0; i < immutableParticles.size(); i++) {
                immutableParticles.get(i).setPosition(positions.get(i));
                immutableParticles.get(i).setAngle(positions.get(i));
            }

        }

        List<Particle_S2> particles;

        AlgorithmType currentAlgorithm = AlgorithmType.GEAR_PREDICTOR_CORRECTOR_S2;
        Algorithm_S2 algorithm = AlgorithmFactory.buildAlgorithmS2(currentAlgorithm, 0,0);
        CircleSystem circleSystem = new CircleSystem(algorithm, TF);

//        int i = 4;
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.00001, particles, STEPS*Math.pow(10,i--));
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.0001, particles, STEPS*Math.pow(10,i--));
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.001, particles, STEPS*Math.pow(10,i--));
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.01, particles, STEPS*Math.pow(10,i--));
//        particles = new ArrayList<>(immutableParticles);
//        circleSystem.simulate(0.1, particles, STEPS*Math.pow(10,i--));


        particles = new ArrayList<>(immutableParticles);
        circleSystem.simulate(0.0001, particles, STEPS*Math.pow(10,3));

    }

    private static boolean isColliding(double position, List<Particle_S2> particles) {
        for(Particle_S2 p : particles) {
            double angularDistance = Math.min(maxRad - Math.abs(p.getAngle() - position) , Math.abs(p.getAngle() - position));
            if(angularDistance * R <= 2*r)
                return true;
        }
        return false;
    }

    private static boolean isCollidingOrdered(double position, List<Double> positions) {
        for(Double pos : positions) {
            double angularDistance = Math.min(maxRad - Math.abs(pos - position) , Math.abs(pos - position));
            if(angularDistance * R <= 2*r)
                return true;
        }
        return false;
    }

}
