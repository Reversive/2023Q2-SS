package ar.edu.itba.ss.utils;

public class MathUtils {
    public static double dotProduct(double[] v1, double[] v2) {
        if(v1.length != v2.length)
            throw new IllegalArgumentException("Vector sizes must be equal");

        double prod = 0.0;

        for (int i = 0; i < v1.length; i++) {
            prod += v1[i]*v2[i];
        }

        return prod;
    }
}
