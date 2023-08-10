package ar.edu.itba.ss.implementations;

import ar.edu.itba.ss.interfaces.DistanceMethod;
import ar.edu.itba.ss.models.Cell;
import ar.edu.itba.ss.models.Context;
import ar.edu.itba.ss.models.Particle;

import java.util.List;

public class CellIndexMethod implements DistanceMethod {
    private final Context context;
    private final boolean shouldUsePeriodicContour;
    private Cell[][] matrix;

    public CellIndexMethod(Context context, boolean shouldUsePeriodicContour) {
        this.context = context;
        this.shouldUsePeriodicContour = shouldUsePeriodicContour;
        int n = this.context.getMatrixSize();
        this.matrix = new Cell[n][n];
        for(int i = 0; i<n; i++) {
            for(int j = 0; j<n; j++) {
                matrix[i][j] = new Cell();
            }
        }
    }

    private void checkAdjacent(Particle p, int row, int col) {
        int n = this.context.getMatrixSize();
        if(this.shouldUsePeriodicContour) {
            if(row >= n) {
                row = 0;
            }
            if(col >= n) {
                col = 0;
            }
            if(row < 0) {
                row = n - 1;
            }
            if(col < 0) {
                col = n - 1;
            }
        } else {
            if(row >= n || row < 0 || col >= n || col < 0) {
                return;
            }
        }
        List<Particle> targetParticles = this.matrix[row][col].getParticles();
        for(Particle particle : targetParticles) {
            if(p.equals(particle) || p.getNeighbours().contains(particle)) continue;
            double distance = shouldUsePeriodicContour ? p.getContourDistanceTo(particle, context.getSideLength()) : p.getDistanceTo(particle);
            if(distance < context.getIcRadius()) {
                p.addNeighbour(particle);
                particle.addNeighbour(p);
            }
        }
    }

    @Override
    public List<Particle> findNeighbours() {
        List<Particle> particles = context.getParticles();
        int cellLength = (int)context.getSideLength() / context.getMatrixSize();
        for(Particle particle : particles) {
            int rowIndex = (int) Math.floor(particle.getX() / cellLength);
            int colIndex = (int) Math.floor(particle.getY() / cellLength);
            this.matrix[rowIndex][colIndex].addParticle(particle);
        }

        for(int i = 0; i < context.getMatrixSize(); i++) {
            for(int j = 0; j < context.getMatrixSize(); j++) {
                List<Particle> cellParticles = this.matrix[i][j].getParticles();
                for(Particle p: cellParticles) {
                    checkAdjacent(p, i, j);
                    checkAdjacent(p, i, j + 1);
                    checkAdjacent(p, i + 1, j + 1);
                    checkAdjacent(p, i + 1, j);
                    checkAdjacent(p, i - 1, j + 1);
                }
            }
        }

        return particles;
    }

    @Override
    public String getName() {
        return "Cell Index Method";
    }
}
