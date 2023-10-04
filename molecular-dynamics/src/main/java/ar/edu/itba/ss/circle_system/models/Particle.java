package ar.edu.itba.ss.circle_system.models;

import ar.edu.itba.ss.models.Particle_S2;

import java.util.Objects;

public class Particle {
    private int id;
    private double x0, x1, x2, x3, x4, x5;
    private double position;
    private double r, m, ui;

    public Particle(int id, double x0, double x1, double x2, double x3, double x4,
                    double x5, double position, double r, double m, double ui) {
        this.id = id;
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
        this.position = position;
        this.r = r;
        this.m = m;
        this.ui = ui;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getX4() {
        return x4;
    }

    public void setX4(double x4) {
        this.x4 = x4;
    }

    public double getX5() {
        return x5;
    }

    public void setX5(double x5) {
        this.x5 = x5;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getUi() {
        return ui;
    }

    public void setUi(double ui) {
        this.ui = ui;
    }

    public boolean collides(Particle p) {
        double angularDistance = Math.min(2*Math.PI- Math.abs(p.getX0() - x0) , Math.abs(p.getX0() - x0));
        return angularDistance * 21.49 <= 2 * r;

//        double dr = this.x0 - p.x0;  // TODO: aca hay que multiplicar por R del circulo?
//        double dv = this.x1 - p.x1;
//
//        double drdv = (dr * dv);
//        if (drdv >= 0) {
//            return false;
//        }
//
//        double dv2 = (dv * dv);
//        double dr2 = (dr * dr);
//        double d = Math.pow(drdv, 2) - dv2 * (dr2 - Math.pow(this.r + p.r, 2));
//        if (d < 0) {
//            return false;
//        }
//
//        return (-(drdv + Math.sqrt(d)) / dv2 ) < dt;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " " + this.position;
    }

}
