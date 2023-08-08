package ar.edu.itba.ss.util;
import org.apache.commons.cli.*;
public class CliParser {
    private String staticFilePath;
    private String dynamicFilePath;
    private double icRadius;
    private int matrixSize;
    private boolean usePeriodicContour;
    private boolean useBruteForce;

    private Options buildOptions() {
        Options options = new Options();
        options.addOption("m", "matrix", true, "Size of the matrix");
        options.addOption("rc", "radius", true, "Radius of the interaction");
        options.addOption("sf", "static-file", true, "Static file path");
        options.addOption("df", "dynamic-file", true, "Dynamic file path");
        options.addOption("pc",  "periodic-contour", false, "Toggles periodic contour condition");
        options.addOption("bf", "brute-force", false, "Toggles brute force");
        return options;
    }

    public boolean parseOptions(String[] args) {
        Options options = buildOptions();
        CommandLineParser clp = new BasicParser();
        try {
            CommandLine cmd = clp.parse(options, args);
            if(!cmd.hasOption("df") || !cmd.hasOption("sf")) {
                System.out.println("You must specify static and dynamic file paths");
                return false;
            }
            this.dynamicFilePath = cmd.getOptionValue("df");
            this.staticFilePath = cmd.getOptionValue("sf");
            if(cmd.hasOption("m")) {
                this.matrixSize = Integer.parseInt(cmd.getOptionValue("m"));
            }
            if(cmd.hasOption("rc")) {
                this.icRadius = Double.parseDouble(cmd.getOptionValue("rc"));
            }
            if(cmd.hasOption("pc")) {
                this.usePeriodicContour = true;
            }
            if(cmd.hasOption("bf")) {
                this.useBruteForce = true;
            }
        } catch (ParseException p) {
            System.out.println("Unrecognized command");
            return false;
        }
        return true;
    }

    public String getStaticFilePath() {
        return staticFilePath;
    }

    public String getDynamicFilePath() {
        return dynamicFilePath;
    }

    public int getMatrixSize() {
        return matrixSize;
    }

    public boolean shouldUsePeriodicContour() {
        return usePeriodicContour;
    }

    public boolean shouldUseBruteForce() {
        return useBruteForce;
    }

    public double getIcRadius() {
        return icRadius;
    }
}
