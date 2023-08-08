package ar.edu.itba.ss.util;

import ar.edu.itba.ss.model.Context;
import ar.edu.itba.ss.model.Particle;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Context parseContext(
            String staticInput,
            String dynamicInput,
            double icRadius,
            int matrixSize,
            boolean usePeriodicContour,
            boolean useBruteForce
    ) {
        String[] staticTokens = staticInput.split("\n");
        String[] dynamicTokens = dynamicInput.split("\n");
        int particleAmount = Integer.parseInt(staticTokens[0]);
        int sideLength = Integer.parseInt(staticTokens[1]);
        List<Particle> particles = new ArrayList<>();
        for(int i = 0; i < particleAmount; i++) {
            String[] currentStaticLine = staticTokens[2 + i].split(" ");
            String[] currentDynamicLine = dynamicTokens[1 + i].split(" ");
            double radius = Double.parseDouble(currentStaticLine[0]);
            int property = Integer.parseInt(currentStaticLine[1]);
            double x = Double.parseDouble(currentDynamicLine[0]);
            double y = Double.parseDouble(currentDynamicLine[1]);
            particles.add(new Particle(radius, x, y, property));
        }
        return new Context(particleAmount, sideLength, particles, icRadius, matrixSize, usePeriodicContour, useBruteForce);
    }
}
