package models;

public class Vector {

    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector difference(Vector p) {
        return new Vector(this.x - p.x, this.y - p.y);
    }

    public Vector sum(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector byScalarProduct(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public double scalarProduct(Vector other) {
        return other.getX() * this.x + other.getY() * this.y;
    }

    public double distance(Vector p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    public double modulus() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


}
