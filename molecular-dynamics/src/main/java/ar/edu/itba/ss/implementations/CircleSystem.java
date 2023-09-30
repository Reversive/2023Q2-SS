package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.Particle_S2;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleSystem {
    private final Algorithm_S2 algorithm;
    private final double tf;

    public CircleSystem(Algorithm_S2 algorithm, double tf) {
        this.algorithm = algorithm;
        this.tf = tf;
    }

    public void simulate(double dt, List<Particle_S2> particles, double steps) {
        File file = new File("circle/data/" + algorithm.getName() + "_" + BigDecimal.valueOf(dt) + ".txt");
        try(FileWriter data = new FileWriter(file)) {

            double t = 0;
            double i = 0;

            Map<Integer, Particle_S2> previousMap = new HashMap<>();
            Map<Integer, Particle_S2> currentMap = new HashMap<>();

            for (Particle_S2 particle : particles) {
                currentMap.put(particle.getId(), particle);
                previousMap.put(particle.getId(), particle);
            }
            Particle_S2 next;

            data.write(0 + "\n");
            for(Particle_S2 p : particles) {
                data.write( p.getId() +  " " + p.getPosition() + "\n");
            }
            
            while(t < tf) {
                if(i % steps == 0) {
                    data.write(t + "\n");
                }
                for(Particle_S2 current : previousMap.values()) {
                    next = algorithm.update(previousMap, current, dt, t);
                    if(i % steps == 0) {
                        data.write( next.getId() +  " " + next.getPosition() + "\n");
                    }
                    currentMap.put(next.getId(), next);
                }
                previousMap.putAll(currentMap);
                i++;
                t += dt;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Algorithm_S2 getAlgorithm() {
        return algorithm;
    }

    public double getTf() {
        return tf;
    }

}
