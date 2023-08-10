package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.DistanceMethodFactory;
import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.CliParser;
import ar.edu.itba.ss.utils.Parser;
import ar.edu.itba.ss.utils.Reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.time.Duration;
import java.time.Instant;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Entrypoint {
    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
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

        List<Particle> neighbours = method.findNeighbours();
        Instant end = Instant.now();
        System.out.println("Method: " + method.getName() + " Duration: " + Duration.between(start, end));

        File positionsFile = new File("positions.csv");
        FileWriter positionsFileWriter = new FileWriter(positionsFile);

        File neighboursFile = new File("neighbours.csv");
        FileWriter neighboursFileWriter = new FileWriter(neighboursFile);

        for (Particle p : context.getParticles()) {
            positionsFileWriter.write(p.getX() + "," + p.getY() + "," + p.getRadius() + "\n");
            StringBuilder neighboursString = new StringBuilder();
            if(p.getNeighbours() != null && !p.getNeighbours().isEmpty()) {
                neighboursString.append(p.getId()).append(",");
                for(Particle n : p.getNeighbours())
                    neighboursString.append(n.getId()).append(",");
                neighboursString.deleteCharAt(neighboursString.length() - 1).append('\n');
                neighboursFileWriter.write(neighboursString.toString());
            }
        }

        positionsFileWriter.close();
        neighboursFileWriter.close();

        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            for (Particle neighbour : neighbours) {
                writer.println(neighbour.neighboursToString());
            }
        }

    }
}
