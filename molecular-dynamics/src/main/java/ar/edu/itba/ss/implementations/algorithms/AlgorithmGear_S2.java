package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.models.Particle_S2;

import java.util.Map;
import java.util.stream.LongStream;


public class AlgorithmGear_S2 extends AlgorithmBase implements Algorithm_S2 {

    private static final double[] COEFFICIENTS = {3.0/20, 251.0/360, 1.0, 11.0/18, 1.0/6, 1.0/60};
    private final double[] currentParameters;
    private final double[] predictedParameters;
    private static final double REACTION_TIME = 1.0;
    private static final double K = 2500;
    private static final double R = 21.49;
    private static final double minRad = 0.0;
    private static final double maxRad = 2 * Math.PI;

    public AlgorithmGear_S2() {
        super(0, 0, AlgorithmType.GEAR_PREDICTOR_CORRECTOR);
        this.currentParameters = new double[] {0, 0, 0, 0, 0, 0};
        this.predictedParameters = new double[] {0, 0, 0, 0, 0, 0};
    }

    private double predictByTaylor(double deltaTime, int num) {
        double ret = 0.0;
        for (int i = num; i < 6; i++) {
            ret += currentParameters[i] * Math.pow(deltaTime, i - num) / factorial(i - num);
        }
        return ret;
    }

    private double getCorrected(int num, double deltaAcceleration, double deltaTime) {
        return predictedParameters[num] + COEFFICIENTS[num] * deltaAcceleration * factorial(num)/Math.pow(deltaTime, num);
    }

    private double factorial(int num) {
        return (double) LongStream.rangeClosed(1, num).reduce(1, (long x, long y) -> x * y);
    }

    private double deltaAcceleration(Map<Integer, Particle_S2> previousMap, Particle_S2 current, double deltaTime) {
        double Fi = (current.getPosition()/R - current.getVelocity())/REACTION_TIME;
        double Fij = 0.0;
        double prevArc;
        double currentArc = current.getPosition() * R;
        for(Particle_S2 p : previousMap.values()) {
            if(p.getId() == current.getId())
                continue;
            prevArc = p.getPosition() * R;
            if(Math.min(Math.abs(currentArc-prevArc), (maxRad*R) - Math.abs(currentArc-prevArc)) <= 2*p.getRadius())
                Fij += K * (Math.abs(p.getPosition()-current.getPosition()) - (2*p.getRadius())/R) * Math.signum(p.getPosition()-current.getPosition());
        }
        double nextAcc = (Fi + Fij) / current.getMass();
        return (nextAcc - predictedParameters[2]) * Math.pow(deltaTime, 2)/2;
    }

    @Override
    public Particle_S2 update(Map<Integer, Particle_S2> previousMap, Particle_S2 current, double deltaTime, double currentTime) {
        if (current == null)
            throw new NullPointerException("This can't happen");
        Particle_S2 next = new Particle_S2.Builder().cloneParticle(current).build();
        currentParameters[0] = current.getPosition();
        currentParameters[1] = current.getVelocity();
        currentParameters[2] = current.getAcceleration();

        for (int i = 0; i < 6; i++) {
            predictedParameters[i] = predictByTaylor(deltaTime, i);
        }

        double deltaAcceleration = deltaAcceleration(previousMap, current, deltaTime);
        for (int i = 0; i < 6; i++) {
            currentParameters[i] = getCorrected(i, deltaAcceleration, deltaTime);
        }

        next.setPosition(currentParameters[0]);
        next.setVelocity(currentParameters[1]);
        next.setAcceleration(currentParameters[2]);
        return next;
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        // DO NOT USE
        return null;
    }
}
