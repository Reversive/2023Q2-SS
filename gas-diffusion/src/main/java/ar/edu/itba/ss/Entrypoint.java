package ar.edu.itba.ss;

import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.EventManager;
import ar.edu.itba.ss.utils.ParticleGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Entrypoint {


    private static final Integer PARTICLE_AMOUNT = 280;
    private static final Double INITIAL_SQUARE_SIDE_LENGTH = 0.09;
    private static final Double L = 0.09;
    private static final Integer ITERATIONS = 250000;
    private static final Integer EVENT_STEP = 1000;
    private static final Double TIME_STEP = 2.0;


    public static void main(String[] args) throws IOException {
        List<Particle> particles = ParticleGenerator.generateParticles(PARTICLE_AMOUNT, INITIAL_SQUARE_SIDE_LENGTH);

        StringBuilder outputBuilder = new StringBuilder();
        EventManager eventManager = new EventManager();
//        outputBuilder.append(PARTICLE_AMOUNT).append("\n");
//        outputBuilder.append(INITIAL_SQUARE_SIDE_LENGTH).append("\n");
//        outputBuilder.append(L).append("\n");
//        outputBuilder.append(0).append("\n");
//        for(Particle p : particles) {
//            outputBuilder.append(p.getId())
//                    .append(" ")
//                    .append(p.getX())
//                    .append(" ")
//                    .append(p.getY())
//                    .append(" ")
//                    .append("\n");
//        }

        StringBuilder pressureOutputBuilder = new StringBuilder();
        double area = Math.pow(INITIAL_SQUARE_SIDE_LENGTH, 2) + INITIAL_SQUARE_SIDE_LENGTH * L;
        pressureOutputBuilder.append(area).append('\n');
        pressureOutputBuilder.append("0 ").append("0 ").append("0\n");

        double nextEventTime;
        double totalTime = 0.0;
        int eventCounter = 1;
        double pressureStepTime = 0.0;
        for(int i = 1; i < ITERATIONS + 1; i++) {
            nextEventTime = eventManager.getNextEventTime(particles, L, INITIAL_SQUARE_SIDE_LENGTH);
            eventManager.evolveTillEvent(particles, nextEventTime, L, INITIAL_SQUARE_SIDE_LENGTH);
            totalTime += nextEventTime;
            pressureStepTime += nextEventTime;
            // PARA ANIMACION
//            outputBuilder.append(i).append("\n");
//            for(Particle p : particles) {
//                outputBuilder.append(p.getId())
//                        .append(" ")
//                        .append(p.getX())
//                        .append(" ")
//                        .append(p.getY())
//                        .append("\n");
//            }
            eventManager.resolveCollisionAndAddImpulse(INITIAL_SQUARE_SIDE_LENGTH);
//            if(eventCounter == EVENT_STEP) {
//                pressureOutputBuilder.append(totalTime).append(" ")
//                        .append(eventManager.getLeftSideImpulse() / (pressureStepTime * (4 * INITIAL_SQUARE_SIDE_LENGTH - L)))
//                        .append(" ")
//                        .append(eventManager.getRightSideImpulse() / (pressureStepTime * (2 * INITIAL_SQUARE_SIDE_LENGTH + L)))
//                        .append("\n");
//                eventCounter = 1;
//                pressureStepTime = 0.0;
//                eventManager.resetImpulse();
//            } else {
//                eventCounter++;
//            }

            if(pressureStepTime > TIME_STEP) {
                if(totalTime >= 190.0) {
                    //outputBuilder.append(i).append("\n");
                    for(Particle p : particles) {
                        outputBuilder.append(p.getId())
                                .append(" ")
                                .append(p.getX())
                                .append(" ")
                                .append(p.getY())
                                .append("\n");
                    }
                }
//                pressureOutputBuilder.append(totalTime).append(" ")
//                        .append(eventManager.getLeftSideImpulse() / (pressureStepTime * ((4 * INITIAL_SQUARE_SIDE_LENGTH) - L)))
//                        .append(" ")
//                        .append(eventManager.getRightSideImpulse() / (pressureStepTime * ((2 * INITIAL_SQUARE_SIDE_LENGTH) + L)))
//                        .append("\n");
                pressureStepTime = 0.0;
                eventManager.resetImpulse();
            }

        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("output_v3.txt"))) {
            writer.println(outputBuilder);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("pressure_0.09v3.txt"))) {
            writer.println(pressureOutputBuilder);
        }

        return;
    }
}
