package ar.edu.itba.ss.models;

public class EventTime {
    private double time;
    private boolean isHorizontal;
    private boolean slitCollision;
    private boolean isLeftSideCollision;

    public EventTime(double time, boolean isHorizontal, boolean slitCollision, boolean isLeftSideCollision) {
        this.time = time;
        this.isHorizontal = isHorizontal;
        this.slitCollision = slitCollision;
        this.isLeftSideCollision = isLeftSideCollision;
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

    public boolean isLeftSideCollision() {
        return isLeftSideCollision;
    }

    public void setLeftSideCollision(boolean leftSideCollision) {
        isLeftSideCollision = leftSideCollision;
    }
}