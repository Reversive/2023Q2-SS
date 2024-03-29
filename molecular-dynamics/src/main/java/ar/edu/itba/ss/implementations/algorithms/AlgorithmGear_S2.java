package ar.edu.itba.ss.implementations.algorithms;

import ar.edu.itba.ss.interfaces.Algorithm_S2;
import ar.edu.itba.ss.models.AlgorithmType;
import ar.edu.itba.ss.models.Particle;
import ar.edu.itba.ss.models.Particle_S2;

import java.util.Map;
import java.util.stream.LongStream;


public class AlgorithmGear_S2 extends AlgorithmBase implements Algorithm_S2 {

    private static final double[] COEFFICIENTS = {3.0/16, 251.0/360, 1.0, 11.0/18, 1.0/6.0, 1.0/60.0};

    private final double[] currentParameters;
    private final double[] predictedParameters;
    private static final double REACTION_TIME = 1.0;
    private static final double K = 2500;
    private static final double R = 21.49;
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
        double Fi = (current.getUi()/R - predictedParameters[1])/REACTION_TIME;
        double Fij = 0.0;
        double currentAngle = ((predictedParameters[0] % (2*Math.PI)) + 2*Math.PI) % (2 * Math.PI);

        for(Particle_S2 p : previousMap.values()) {
            if(p.getId() == current.getId())
                continue;

            double angularDistance = Math.min(maxRad - Math.abs(p.getAngle() - currentAngle) , Math.abs(p.getAngle() - currentAngle));
            if(R*angularDistance <= 2*p.getRadius()) {
                if(p.getAngle() <= 1 && currentAngle >= 5)
                    Fij += K * (angularDistance - (2*p.getRadius()/R)) * Math.signum((p.getAngle() + 2*Math.PI) - currentAngle);
                else if(currentAngle  <= 1 && p.getAngle() >= 5)
                    Fij += K * (angularDistance - (2*p.getRadius()/R)) * Math.signum(p.getAngle() - (currentAngle + 2*Math.PI));
                else
                    Fij += K * (angularDistance - (2*p.getRadius()/R)) * Math.signum(p.getAngle() - currentAngle);
            }
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
        currentParameters[3] = current.getX3();
        currentParameters[4] = current.getX4();
        currentParameters[5] = current.getX5();

        for (int i = 0; i < 6; i++) {
            predictedParameters[i] = predictByTaylor(deltaTime, i);
        }

        double deltaAcceleration = deltaAcceleration(previousMap, current, deltaTime);
        for (int i = 0; i < 6; i++) {
            currentParameters[i] = getCorrected(i, deltaAcceleration, deltaTime);
        }

        next.setPosition(currentParameters[0]);
        next.setAngle(((currentParameters[0] % (2*Math.PI)) + 2*Math.PI) % (2 * Math.PI));
        next.setVelocity(currentParameters[1]);
        next.setAcceleration(currentParameters[2]);
        next.setX3(currentParameters[3]);
        next.setX4(currentParameters[4]);
        next.setX5(currentParameters[5]);

        return next;
    }

    @Override
    public Particle update(Particle previous, Particle current, double deltaTime, double currentTime) {
        // DO NOT USE
        return null;
    }
}

