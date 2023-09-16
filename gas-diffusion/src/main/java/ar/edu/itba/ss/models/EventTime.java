package ar.edu.itba.ss.models;

public class EventTime {
    private double time;
    private boolean isHorizontal;
    private boolean slitCollision;

    public EventTime(double time, boolean isHorizontal, boolean slitCollision) {
        this.time = time;
        this.isHorizontal = isHorizontal;
        this.slitCollision = slitCollision;
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

    public boolean isSlitCollision() {
        return slitCollision;
    }

    public void setSlitCollision(boolean slitCollision) {
        this.slitCollision = slitCollision;
    }
}