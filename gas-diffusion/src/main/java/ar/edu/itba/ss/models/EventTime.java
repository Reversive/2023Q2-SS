package ar.edu.itba.ss.models;

public class EventTime {
    private double time;
    private boolean isHorizontal;

    public EventTime(double time, boolean isHorizontal) {
        this.time = time;
        this.isHorizontal = isHorizontal;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }
}