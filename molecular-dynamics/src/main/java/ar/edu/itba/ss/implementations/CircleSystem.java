package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.models.Particle_S2;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleSystem {
    private final Algorithm algorithm;
    private final double tf;
    private final int steps;

    public CircleSystem(Algorithm algorithm, double tf, int steps) {
        this.algorithm = algorithm;
        this.tf = tf;
        this.steps = steps;
    }

    public void simulate(double dt, List<Particle_S2> particles) {
        File file = new File("circle/data/" + algorithm.getName() + "_" + BigDecimal.valueOf(dt) + ".txt");
        try(FileWriter data = new FileWriter(file)) {

            double t = 0;
            double i = 0;

            Map<Integer, Particle_S2> previousMap = new HashMap<>();
            Map<Integer, Particle_S2> currentMap = new HashMap<>();
            for (Particle_S2 particle : particles) {
                currentMap.put(particle.getId(), particle);
            }
            Particle_S2 next;

            for()

            while(t < tf) {
                for(Particle_S2 current : particles) {
                    next = algorithm.update(previousMap.get(current.getId()), current, dt, t);
                    if(i % steps == 0) {
                        data.write(t + " " + current.getId() +  " " + current.getPosition() + "\n");
                    }
                    previousMap.put(current.getId(), current);
                    currentMap.put(next.getId(), next);
                }
                i++;
                t += dt;

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
