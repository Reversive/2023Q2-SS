package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.EventManager;
import ar.edu.itba.ss.utils.ParticleGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Entrypoint {

    private static final Integer PARTICLE_AMOUNT = 10;
    private static final Double INITIAL_SQUARE_SIDE_LENGTH = 0.09;
    private static final Double L = 0.03;
    private static final Integer ITERATIONS = 100;

    public static void main(String[] args) throws IOException {
        List<Particle> particles = ParticleGenerator.generateParticles(PARTICLE_AMOUNT, INITIAL_SQUARE_SIDE_LENGTH);

        StringBuilder outputBuilder = new StringBuilder();
        EventManager eventManager = new EventManager();
        outputBuilder.append(PARTICLE_AMOUNT).append("\n");
        outputBuilder.append(INITIAL_SQUARE_SIDE_LENGTH).append("\n");
        outputBuilder.append(L).append("\n");
        outputBuilder.append(0).append("\n");
        for(Particle p : particles) {
            outputBuilder.append(p.getId())
                    .append(" ")
                    .append(p.getX())
                    .append(" ")
                    .append(p.getY())
                    .append(" ")
                    .append(p.getDirection())
                    .append("\n");
        }

        StringBuilder pressureOutputBuilder = new StringBuilder();
        double area = Math.pow(INITIAL_SQUARE_SIDE_LENGTH, 2) + INITIAL_SQUARE_SIDE_LENGTH * L;
        pressureOutputBuilder.append(area).append('\n');
        pressureOutputBuilder.append("0\n").append("0 ").append("0\n");

        double nextEventTime;
//        int eventCounter = 1;
        for(int i = 1; i < ITERATIONS + 1; i++) {
            nextEventTime = eventManager.getNextEventTime(particles, L, INITIAL_SQUARE_SIDE_LENGTH);
            eventManager.evolveTillEvent(particles, nextEventTime);
//            if(eventCounter == 10) {
                outputBuilder.append(i).append("\n");
                for(Particle p : particles) {
                    outputBuilder.append(p.getId())
                            .append(" ")
                            .append(p.getX())
                            .append(" ")
                            .append(p.getY())
                            .append(" ")
                            .append(p.getDirection())
                            .append("\n");
                }
//                eventCounter = 1;
//            } else {
//                eventCounter++;
//            }
            eventManager.resolveCollisionAndAddImpulse(INITIAL_SQUARE_SIDE_LENGTH);
            pressureOutputBuilder.append(i).append("\n")
                    .append(eventManager.getLeftSideImpulse() / (nextEventTime * (4 * INITIAL_SQUARE_SIDE_LENGTH - L)))
                    .append(" ")
                    .append(eventManager.getRightSideImpulse() / (nextEventTime * (2 * INITIAL_SQUARE_SIDE_LENGTH + L)))
                    .append("\n");
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            writer.println(outputBuilder);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("pressure.txt"))) {
            writer.println(pressureOutputBuilder);
        }

        return;
    }
}
