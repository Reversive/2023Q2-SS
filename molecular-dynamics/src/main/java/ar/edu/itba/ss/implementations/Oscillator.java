package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.Particle;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;

public class Oscillator {
    private final Algorithm algorithm;
    private final double tf;
    private final int steps;

    public Oscillator(Algorithm algorithm, double tf, int steps) {
        this.algorithm = algorithm;
        this.tf = tf;
        this.steps = steps;
    }

    public void simulate(double dt, double gamma, double k, double m) {
        File file = new File("/oscillator/data/" + algorithm.getName() + "_" + BigDecimal.valueOf(dt) + ".txt");
        try(FileWriter data = new FileWriter(file)) {
            final int r0 = 1;
            double v0 = -r0 * gamma/(2 * m);
            double f0 = -k * r0 - gamma * v0;
            Particle current = new Particle.Builder()
                    .withMass(m)
                    .withRadius(0)
                    .withPosition(r0, 0)
                    .withVelocity(v0, 0)
                    .withAcceleration(f0/m, 0)
                    .build();
            Particle next;
            Particle previous = null;
            double t = 0;
            double i = 0;

            while(t < tf) {
                next = algorithm.update(previous, current, dt, t);
                if(i % steps == 0) {
                    data.write(t + " " + current.getPosition().getX() + " " + current.getPosition().getY() + "\n");
                }
                previous = current;
                current = next;
                t += dt;
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public double getTf() {
        return tf;
    }

    public int getSteps() {
        return steps;
    }
}
