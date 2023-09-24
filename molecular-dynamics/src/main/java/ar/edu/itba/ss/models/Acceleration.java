package ar.edu.itba.ss.models;

public class Acceleration {
    private double ax;
    private double ay;

    public Acceleration(double ax, double ay) {
        this.ax = ax;
        this.ay = ay;
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }
}
