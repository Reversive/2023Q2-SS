package ar.edu.itba.ss.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ParticleGenerator {

    private static Double MIN_RADIUS = 0.15;

    public static void main(String[] args) {
        int[] particleN = { 100,200,300,400,500,600,700,800,900,1000 };
        int L = 20;
        for(int n : particleN) {
            generateStaticFile(n, L);
            generateDynamicFile(n, L);
        }
    }

    private static void generateStaticFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Static" + N + ".txt"))) {
            writer.write(Integer.toString(N));
            writer.newLine();
            writer.write(Double.toString(L));
            writer.newLine();
            for (int i = 0; i < N; i++) {
                double radius = 0.25;
                double property = 1;
                writer.write(radius + " " + property);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateDynamicFile(int N, int L) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Dynamic" + N + ".txt"))) {
            writer.write("0");
            writer.newLine();

            Random random = new Random();
            for (int i = 0; i < N; i++) {
                double x = random.nextDouble() * L;
                double y = random.nextDouble() * L;
                if(x < MIN_RADIUS)
                    x += MIN_RADIUS;
                if(x > L - MIN_RADIUS)
                    x -= MIN_RADIUS;
                if(y < MIN_RADIUS)
                    y += MIN_RADIUS;
                if(y > L - MIN_RADIUS)
                    y -= MIN_RADIUS;
                writer.write(x + " " + y);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
