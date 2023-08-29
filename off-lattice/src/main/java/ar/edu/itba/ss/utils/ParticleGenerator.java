package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleGenerator {
    public static void main(String[] args) {
        int[] particleN = { 150/*,20,30,40,50,60,70,80,90,100,200,300,400,500,600,700,800,900,1000*/ };
        int L = 5;
        for(int n : particleN) {
            generateStaticFile(n, L);
            generateDynamicFile(n, L);
        }
    }

    public static void generate(int particleDensity, int sideSize) {
        generateStaticFile(particleDensity, sideSize);
        generateDynamicFile(particleDensity, sideSize);
    }

    private static void generateStaticFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/static" + N + ".txt"))) {
            writer.write(Integer.toString(N));
            writer.newLine();
            writer.write(Integer.toString(L));
            writer.newLine();
            for (int i = 0; i < N; i++) {
                double radius = 0.;
                double property = 1;
                writer.write(radius + " " + property);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateDynamicFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/dynamic" + N + ".txt"))) {
            writer.write("0");
            writer.newLine();
            Random random = new Random();
            double DEFAULT_SPEED = 0.03; //TODO CAMBIAR DEFAULT SPEED
            for (int i = 0; i < N; i++) {
                double direction = random.nextDouble() * 2 * Math.PI;
                double x = random.nextDouble() * L;
                double y = random.nextDouble() * L;
                double dx = DEFAULT_SPEED * Math.cos(direction);
                double dy = DEFAULT_SPEED * Math.sin(direction);
                writer.write(x + " " + y + " " + dx + " " + dy);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Particle> generateParticles(int N, int L) {
        Random random = new Random();
        List<Particle> particles = new ArrayList<>(N);
        double DEFAULT_SPEED = 0.03;
        for (int i = 0; i < N; i++) {
            double direction = random.nextDouble() * 2 * Math.PI;
            double x = random.nextDouble() * L;
            double y = random.nextDouble() * L;
            double dx = DEFAULT_SPEED * Math.cos(direction);
            double dy = DEFAULT_SPEED * Math.sin(direction);
            particles.add(new Particle(i,0,x,y,dx,dy,0));
        }
        return particles;
    }
}
