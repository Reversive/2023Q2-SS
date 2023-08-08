package ar.edu.itba.ss;

import ar.edu.itba.ss.model.Context;
import ar.edu.itba.ss.util.CliParser;
import ar.edu.itba.ss.util.Parser;
import ar.edu.itba.ss.util.Reader;

import java.io.IOException;

public class Entrypoint {
    public static void main(String[] args) throws IOException {
        CliParser cli = new CliParser();
        if(!cli.parseOptions(args)) return;
        Context context = Parser.parseContext(
                Reader.readInputStream(cli.getStaticFilePath()),
                Reader.readInputStream(cli.getDynamicFilePath()),
                cli.getIcRadius(),
                cli.getMatrixSize(),
                cli.shouldUseBruteForce(),
                cli.shouldUsePeriodicContour()
        );
    }
}
