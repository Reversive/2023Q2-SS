package ar.edu.itba.ss;


import ar.edu.itba.ss.implementations.DistanceMethodFactory;
import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.CliParser;
import ar.edu.itba.ss.utils.Parser;
import ar.edu.itba.ss.utils.Reader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Entrypoint {
    final static int ITERATIONS = 100;
    final static double DEFAULT_SPEED = 0.03; //TODO CAMBIAR DEFAULT SPEED
    public static void main(String[] args) throws IOException {
        CliParser cli = new CliParser();
        if(!cli.parseOptions(args)) return;
        Context context = new Context(
                cli.getIcRadius(),
                cli.getMatrixSize()
        );

        if(!Parser.parseInputContext(
                Reader.readInputStream(cli.getStaticFilePath()),
                Reader.readInputStream(cli.getDynamicFilePath()),
                context)
        ) {
            System.out.println("Problem while parsing input files");
            return;
        }
        DistanceMethod method = DistanceMethodFactory.buildMethod(
                cli.shouldUseBruteForce(),
                cli.shouldUsePeriodicContour(),
                context
        );
        StringBuilder builder = new StringBuilder();
        builder.append(context.getParticleAmount()).append("\n");
        for(int i = 0; i < ITERATIONS; i++) {
            double va = 1 / (context.getParticleAmount() * DEFAULT_SPEED);
            builder.append(i).append("\n");
            for(Particle p : context.getParticles()) {
                builder.append(p.getId())
                        .append(" ")
                        .append(p.getX())
                        .append(" ")
                        .append(p.getY())
                        .append(" ")
                        .append(p.getDirection())
                        .append("\n");
            }
            method.placeParticles();
            method.setNeighbours();
            context.getParticles().forEach(p -> p.move(cli.getEta(), context.getSideLength()));
            double sumVx = context.getParticles().stream().mapToDouble(Particle::getVx).sum();
            double sumVy = context.getParticles().stream().mapToDouble(Particle::getVy).sum();
            double modulus = Math.sqrt(sumVx * sumVx + sumVy * sumVy);
            va *= modulus;
            //builder.append("va: ").append(va).append('\n');
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
                writer.println(builder);
        }
    }
}
