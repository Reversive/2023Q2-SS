package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.DistanceMethodFactory;
import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.CliParser;
import ar.edu.itba.ss.utils.ParticleGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MultipleRuns {
    final static int ITERATIONS = 200;
    final static int RUNS = 15;
    final static double DEFAULT_SPEED = 0.03; //TODO CAMBIAR DEFAULT SPEED
    public static void main(String[] args) throws IOException {
        CliParser cli = new CliParser();
        if(!cli.parseOptions(args)) return;
        Context context = new Context(
                cli.getIcRadius(),
                cli.getMatrixSize()
        );

        DistanceMethod method = DistanceMethodFactory.buildMethod(
                cli.shouldUseBruteForce(),
                cli.shouldUsePeriodicContour(),
                context
        );
        context.setSideLength(7);
        context.setParticleAmount(300);

        StringBuilder vaOutputBuilder = new StringBuilder();

        for(int j = 0; j < RUNS; j++) {
            context.setParticles(ParticleGenerator.generateParticles(300,7));
            vaOutputBuilder.append(j).append('\n');
            for(int i = 0; i < ITERATIONS; i++) {
                double va = 1 / (context.getParticleAmount() * DEFAULT_SPEED);
                double sumVx = context.getParticles().stream().mapToDouble(Particle::getVx).sum();
                double sumVy = context.getParticles().stream().mapToDouble(Particle::getVy).sum();
                double modulus = Math.sqrt(sumVx * sumVx + sumVy * sumVy);
                va *= modulus;
                vaOutputBuilder.append(va).append('\n');
                method.placeParticles();
                method.setNeighbours();
                context.getParticles().forEach(p -> p.move(cli.getEta(), context.getSideLength()));
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("mult_va_output" + cli.getEta() + ".txt"))) {
            writer.println(vaOutputBuilder);
        }
    }
}
