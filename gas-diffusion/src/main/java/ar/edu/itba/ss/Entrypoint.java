package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.EventManager;
import ar.edu.itba.ss.utils.ParticleGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Entrypoint {

    private static final Integer PARTICLE_AMOUNT = 500;
    private static final Double INITIAL_SQUARE_SIDE_LENGTH = 0.09;
    private static final Double L = 0.5;
    private static final Integer ITERATIONS = 500;

    public static void main(String[] args) throws IOException {
        List<Particle> particles = ParticleGenerator.generateParticles(PARTICLE_AMOUNT, INITIAL_SQUARE_SIDE_LENGTH);

        StringBuilder outputBuilder = new StringBuilder();

        double nextEventTime;
        for(int i = 0; i < ITERATIONS; i++) {
            nextEventTime = EventManager.getNextEventTime(particles, L, INITIAL_SQUARE_SIDE_LENGTH);
            //MOVER
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            writer.println(outputBuilder);
        }

        return;
    }
}
