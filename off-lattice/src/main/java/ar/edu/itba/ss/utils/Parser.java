package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static boolean parseInputContext(
            String staticInput,
            String dynamicInput,
            Context context
    ) {
        String[] staticTokens = staticInput.split("\n");
        String[] dynamicTokens = dynamicInput.split("\n");
        if(staticTokens.length < 2 || dynamicTokens.length < 1) return false;
        int particleAmount = Integer.parseInt(staticTokens[0]);
        if(staticTokens.length - 2 != particleAmount || dynamicTokens.length - 1 != particleAmount) return false;
        int sideLength = Integer.parseInt(staticTokens[1]);
        List<Particle> particles = new ArrayList<>();
        for(int i = 0; i < particleAmount; i++) {
            String[] currentStaticLine = staticTokens[2 + i].split(" ");
            String[] currentDynamicLine = dynamicTokens[1 + i].split(" ");
            double radius = Double.parseDouble(currentStaticLine[0]);
            double property = Double.parseDouble(currentStaticLine[1]);
            double x = Double.parseDouble(currentDynamicLine[0]);
            double y = Double.parseDouble(currentDynamicLine[1]);
            double dx = Double.parseDouble(currentDynamicLine[2]);
            double dy = Double.parseDouble(currentDynamicLine[3]);
            particles.add(new Particle(i, radius, x, y, dx, dy, property));
        }
        context.setParticleAmount(particleAmount);
        context.setSideLength(sideLength);
        context.setParticles(particles);
        return true;
    }
}
