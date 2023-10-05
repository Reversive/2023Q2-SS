package ar.edu.itba.ss.circle_system;

import ar.edu.itba.ss.circle_system.models.Particle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CircleSystemRunner {
    private static final double r = 2.25;
    private static final double K = 2500;
    private static final double MAX_DT = 0.1;
    private static final double MIN_DT = 0.00001;
    private static final double maxRad = 2*Math.PI, minRad = 0, minUi = 9, maxUi = 12;
    private static final double R = 21.49;
    private static final double mass = 25;
    private static final double tf = 500;

    private static final double[] coef = {3/16.0, 251/360.0, 1, 11/18.0, 1/6.0, 1/60.0};

    private static final double[] fact = {1, 2, 6, 24, 120};

    public static void main(String[] args) throws IOException {
        int[] Ns = {25};
        //int[] Ns = {25};
        double[] DTs = {Math.pow(10,-1), Math.pow(10,-2), Math.pow(10,-3), Math.pow(10,-4), Math.pow(10,-5)};
        //double[] DTs = {Math.pow(10,-4)};
        boolean printVelocity = false;

        for(int i = 0; i < Ns.length; i++) {
            List<Particle> immutableParticles = generateParticles(Ns[i]);
            for(int j = 0; j < DTs.length; j++) {
                List<Particle> particles = new ArrayList<>(immutableParticles);
                double t = 0;
                int step = (int) Math.ceil(MAX_DT / DTs[j]); // TODO cambiar algun nombre o algo
                System.out.println("El step quedo: " + step);
                int frames = 0;
                File file;
                if(printVelocity) {
                    file = new File("circle/data/velocities_" + Ns[i] + ".txt");
                } else {
                    file = new File("circle/data/particles_" + j + ".txt");
                }
                try (FileWriter data = new FileWriter(file)) {
                    while (t <= tf) {
                        if (frames % step == 0) {
                            data.write(t + "\n");
                            for (Particle current : particles) {
                                if(printVelocity)
                                    data.write(current.getId() + " " + current.getX1() + "\n");
                                else
                                    data.write(current.getId() + " " + current.getPosition() + "\n");
                            }
                        }
                        frames++;
                        particles = gearMethod(particles, DTs[j]);
                        t += DTs[j];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean isColliding(double position, List<Particle> particles) {
        for(Particle p : particles) {
            double angularDistance = Math.min(maxRad - Math.abs(p.getPosition() - position) , Math.abs(p.getPosition() - position));
            if(angularDistance * R <= 2*r)
                return true;
        }
        return false;
    }

    private static List<Particle> generateParticles(int N) {
        double nextPosition, ui;
        List<Particle> immutableParticles = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            do {
                if(N >= 22)
                    nextPosition = maxRad * i / N ;
                else
                    nextPosition = minRad + (Math.random() * (maxRad - minRad));

                if (nextPosition == maxRad)
                    nextPosition = 0;
            } while (isColliding(nextPosition, immutableParticles));
            ui = minUi + (Math.random() * (maxUi - minUi));
            immutableParticles.add(new Particle(
                    i,
                    nextPosition,
                    ui/R,
                    0,
                    0,
                    0,
                    0,
                    nextPosition,
                    r,
                    mass,
                    ui
            ));
        }
        return immutableParticles;
    }

    private static List<Particle> gearMethod(List<Particle> particles, double dt) {
        List<Particle> toReturn = new ArrayList<>();
        for(Particle p1 : particles) {
            Particle particle = new Particle(p1.getId(), p1.getX0(), p1.getX1(),
                    p1.getX2(), p1.getX3(), p1.getX4(), p1.getX5(), p1.getPosition(),
                    p1.getR(), p1.getM(), p1.getUi());
            double[] rp = predictByTaylor(particle.getX0()%maxRad, p1.getX1(), p1.getX2(), p1.getX3(), p1.getX4(), p1.getX5(), particle.getPosition(), dt);
            particle.setX0(rp[0]%maxRad);
            particle.setX1(rp[1]);
            particle.setPosition(rp[6]);

            double deltaA = calculateDeltaA(particle, particles, dt) - rp[2];
            double deltaR2 = deltaA * Math.pow(dt, 2) / 2;


            particle.setX0((rp[0] + coef[0] * deltaR2) % maxRad);
            particle.setPosition((rp[6] + coef[0] * deltaR2));
            particle.setX1(rp[1] + coef[1] * deltaR2 / dt);
            particle.setX2(rp[2] + coef[2] * deltaR2 * 2 / Math.pow(dt, 2));
            particle.setX3(rp[3] + coef[3] * deltaR2 * 6 / Math.pow(dt, 3));
            particle.setX4(rp[4] + coef[4] * deltaR2 * 24 / Math.pow(dt, 4));
            particle.setX5(rp[5] + coef[5] * deltaR2 * 120 / Math.pow(dt, 5));
            toReturn.add(particle);
        }

        return toReturn;
    }

    private static double collisionForce(Particle p1, Particle p2) {
        double angularDistance = Math.min(maxRad - Math.abs(p1.getX0() - p2.getX0()) , Math.abs(p1.getX0() - p2.getX0()));
        if(p1.getX0() <= 1 && p2.getX0() >= 5)
            return K * (angularDistance - (2*p1.getR()/R)) * Math.signum((p1.getX0() + 2*Math.PI) - p2.getX0());
        else if(p2.getX0()  <= 1 && p1.getX0() >= 5)
            return K * (angularDistance - (2*p2.getR()/R)) * Math.signum(p1.getX0() - (p2.getX0() + 2*Math.PI));
        return K * (angularDistance - (2*p1.getR()/R)) * Math.signum(p1.getX0() - p2.getX0());
    }

    private static double propulsionForce(Particle p) {
        return (p.getUi()/R - p.getX1());
    }
    private static double calculateDeltaA(Particle p, List<Particle> particles, double dt) {
        double sum = 0.0;
        for(Particle particle: particles) {
            if(!particle.equals(p) && p.collides(particle)) {
                sum += collisionForce(particle, p);
            }
        }
        return (propulsionForce(p) + sum) / p.getM();
    }

    private static double[] predictByTaylor(double r, double r1, double r2, double r3, double r4, double r5, double position, double dt) {
        // TODO: CAMBIAR ESTO
        double rp = r + r1 * dt + r2 * Math.pow(dt, 2) / 2 + r3 * Math.pow(dt, 3) / 6 + r4 * Math.pow(dt, 4) / 24 + r5 * Math.pow(dt, 5) / 120;
        double rpPosition = position + r1 * dt + r2 * Math.pow(dt, 2) / 2 + r3 * Math.pow(dt, 3) / 6 + r4 * Math.pow(dt, 4) / 24 + r5 * Math.pow(dt, 5) / 120;
        double r1p = r1 + r2 * dt + r3 * Math.pow(dt, 2) / 2 + r4 * Math.pow(dt, 3) /6 + r5 * Math.pow(dt, 4) / 24;
        double r2p = r2 + r3 * dt + r4 * Math.pow(dt, 2) / 2 + r5 * Math.pow(dt, 3) / 6;
        double r3p = r3 + r4 * dt + r5 * Math.pow(dt, 2) / 2;
        double r4p = r4 + r5 * dt;
        return new double[]{rp % maxRad, r1p, r2p, r3p, r4p, r5, rpPosition};
    }
}
