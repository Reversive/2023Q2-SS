import models.Particle;
import models.Silo;
import utils.ParticleUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EntryPoint {

    private static final double D = 3.0; // 3,4,5,6
    private static final double OMEGA = 5; // 5,10,15,20,30,50

    private static final double STEP = 100;

    private static final int TF = 1000;
    private static final double DT = 0.001;

    public static void main(String[] args) throws IOException {
        List<Particle> particles = ParticleUtils.generateParticles();

        Silo silo = new Silo(D, particles);

        File file = new File("results_D" + D + "_OMEGA" + OMEGA + ".txt");

        FileWriter data = new FileWriter(file);

        int leftParticles = 0;
        StringBuilder builder = new StringBuilder();

        for(int t = 0, j = 0; t < TF; t += DT, j++) {
            silo.vibrate(t, OMEGA);
            particles.forEach(Particle::predictor);
            leftParticles += silo.resetParticles();
            particles.forEach(Particle::resetParticleForce);
            silo.updateForces();
            particles.forEach(Particle::corrector);
            particles.forEach(Particle::resetParticleForce);
            silo.updateForces();

            if (j % STEP == 0) {
                builder.setLength(0);
//                for(Particle p : particles) {
//                    builder.append(p.getPosition().getX())
//                            .append(' ').append(p.getPosition().getY())
//                            .append(' ').append(p.getVelocity().getX())
//                            .append(' ').append(p.getVelocity().getY())
//                            .append(' ').append(p.getRadius()).append('\n');
//                }
                builder.append(t).append(' ').append(leftParticles).append('\n');
                data.write(builder.toString());
            }
        }

        data.close();
    }
}
