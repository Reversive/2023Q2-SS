import models.Particle;
import models.Silo;
import utils.ParticleUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VaryD {

    private static final double D = 3.0; // 3,4,5,6
    private static final double OMEGA = 15.0; // 5,10,15,20,30,50
    private static final List<Double> D_VECTOR = Arrays.asList(6.0);

    private static final double STEP = 100;

    private static final double TF = 20;
    private static final double DT = 0.001;

    public static void main(String[] args) throws IOException {
        for(Double d_i : D_VECTOR) {
        List<Particle> particles = ParticleUtils.generateParticles(DT);

        Silo silo = new Silo(d_i, particles);

//            File file = new File("results_D" + D + "_OMEGA" + omega_i + ".txt");
        File file = new File("resultsD" + d_i + ".txt");


        FileWriter data = new FileWriter(file);

        int leftParticles = 0;
        double baseY = 0;
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for(double t = 0; t < TF; t += DT) {
            baseY = silo.vibrate(t, OMEGA);
            particles.forEach(Particle::predictor);
            leftParticles += silo.resetParticles();
            particles.forEach(Particle::resetParticleForce);
            silo.updateForces();
            particles.forEach(Particle::corrector);
            particles.forEach(Particle::resetParticleForce);
            silo.updateForces();

            if (i % STEP == 0) {

                //CLEAR
                builder.setLength(0);
                //END

                //TO-PRINT
//                builder.append(baseY).append('\n');
                    for(Particle p : particles) {
                        builder.append(p.getId()).append(' ')
                                .append(p.getPosition().getX())
                                .append(' ').append(p.getPosition().getY())
//                            .append(' ').append(p.getVelocity().getX())
//                            .append(' ').append(p.getVelocity().getY())
                                .append(' ').append(p.getRadius()).append('\n');
                    }
                //builder.append(t).append(' ').append(leftParticles).append('\n');
                //TO-PRINT

                //WRITE
                data.write(builder.toString());
                //WRITE
            }
            i++;
        }

        data.close();
        }
    }
}
