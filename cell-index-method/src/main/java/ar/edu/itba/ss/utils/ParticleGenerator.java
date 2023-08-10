package ar.edu.itba.ss.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
public class ParticleGenerator {

    private static final double MAX_PARTICLE_RADIUS = 1.0;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ParticleGenerator <N> <L>");
            return;
        }

        int N = Integer.parseInt(args[0]);
        int L = Integer.parseInt(args[1]);

        generateStaticFile(N,L);
        generateDynamicFile(N,L);
    }

    private static void generateStaticFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/StaticN.txt"))) {
            writer.write(Integer.toString(N));
            writer.newLine();
            writer.write(Double.toString(L));
            writer.newLine();

            Random random = new Random();
            for (int i = 0; i < N; i++) {
                double radius = random.nextDouble() * MAX_PARTICLE_RADIUS;
                writer.write("r" + (i + 1) + " " + radius);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateDynamicFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/DynamicN.txt"))) {
            writer.write("0");
            writer.newLine();

            Random random = new Random();
            for (int i = 0; i < N; i++) {
                double x = random.nextDouble() * L;
                double y = random.nextDouble() * L;
                writer.write(x + " " + y);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
