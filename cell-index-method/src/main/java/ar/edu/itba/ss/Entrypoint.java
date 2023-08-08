package ar.edu.itba.ss;

import ar.edu.itba.ss.implementations.DistanceMethodFactory;
import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.utils.CliParser;
import ar.edu.itba.ss.utils.Parser;
import ar.edu.itba.ss.utils.Reader;

import java.io.IOException;
import java.util.List;

public class Entrypoint {
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

        List<Particle> neighbours = method.findNeighbours();
        for(Particle neighbour : neighbours) {
            System.out.println(neighbour.neighboursToString());
        }

    }
}
