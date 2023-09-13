package ar.edu.itba.ss.utils;

import ar.edu.itba.ss.models.Particle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleGenerator {
    private static final double SPEED = 0.01;
    private static final double MASS = 1;
    private static final double RADIUS = 0.0015;
    public static List<Particle> generateParticles(int N, double L) {
        Random random = new Random();
        List<Particle> particles = new ArrayList<>(N);
        double x, y, direction, dx, dy;
        for (int i = 0; i < N; i++) {
            do {
                direction = random.nextDouble() * 2 * Math.PI;
                x = random.nextDouble() * L;
                if(x <= RADIUS)
                    x = RADIUS*2;
                else if(x >= L - RADIUS)
                    x = L - RADIUS*2;
                y = random.nextDouble() * L;
                if(y <= RADIUS)
                    y = RADIUS*2;
                else if(y >= L - RADIUS)
                    y = L - RADIUS*2;
                dx = SPEED * Math.cos(direction);
                dy = SPEED * Math.sin(direction);
            }while(!validPosition(x,y, particles));

            particles.add(new Particle(i,RADIUS,x,y,dx,dy, MASS));
        }
        return particles;
    }

    private static boolean validPosition(double x, double y, List<Particle> particles) {
        for(Particle p : particles) {
            if( Math.pow((p.getX() - x),2) + Math.pow((p.getY() - y),2) <= Math.pow(2*RADIUS,2))
                return false;
        }
        return true;
    }
}
