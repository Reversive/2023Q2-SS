package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;

import java.util.stream.LongStream;


public class AlgorithmGear_S2 extends AlgorithmBase implements Algorithm {

    private static final double[] COEFFICIENTS = {3.0/20, 251.0/360, 1.0, 11.0/18, 1.0/6, 1.0/60};
    private final double[] currentParameters;
    private final double[] predictedParameters;

    public AlgorithmGear_S2() {
        super(0, 0, AlgorithmType.GEAR_PREDICTOR_CORRECTOR);
        this.currentParameters = new double[] {0, 0, 0, 0, 0, 0};
        this.predictedParameters = new double[] {0, 0, 0, 0, 0, 0};
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        if (current == null)
            throw new NullPointerException("This can't happen");
        Particle next = new Particle.Builder().cloneParticle(current).build();
        currentParameters[0] = current.getPosition().getX();
        currentParameters[1] = current.getVelocity().getVx();
        currentParameters[2] = current.getAcceleration().getAx();

        for (int i = 0; i < 6; i++) {
            predictedParameters[i] = predictByTaylor(deltaTime, i);
        }

        double deltaAcceleration = deltaAcceleration(deltaTime, current.getMass());
        for (int i = 0; i < 6; i++) {
            currentParameters[i] = getCorrected(i, deltaAcceleration, deltaTime);
        }

        next.getPosition().setX(currentParameters[0]);
        next.getVelocity().setVx(currentParameters[1]);
        next.getAcceleration().setAx(currentParameters[2]);
        return next;
    }

    private double predictByTaylor(double deltaTime, int num) {
        double ret = 0.0;
        for (int i = num; i < 6; i++) {
            ret += currentParameters[i] * Math.pow(deltaTime, i - num) / factorial(i - num);
        }
        return ret;
    }

    private double deltaAcceleration(double deltaTime, double mass) {
        //TODO ESTO
        double nextAcc = (-K * predictedParameters[0] - gamma * predictedParameters[1])/mass;
        return (nextAcc - predictedParameters[2]) * Math.pow(deltaTime, 2)/2;
    }

    private double getCorrected(int num, double deltaAcceleration, double deltaTime) {
        return predictedParameters[num] + COEFFICIENTS[num] * deltaAcceleration * factorial(num)/Math.pow(deltaTime, num);
    }

    private double factorial(int num) {
        return (double) LongStream.rangeClosed(1, num).reduce(1, (long x, long y) -> x * y);
    }
}

