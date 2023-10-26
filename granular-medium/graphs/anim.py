import matplotlib.pyplot as plt
import matplotlib.animation as animation
import matplotlib.patches as patches
import numpy as np
import math
import sys

particle_list = []

with open('results.txt', 'r') as f:
    lines = f.readlines()
    for i in range(0, len(lines), 200):
        particle_list.append([list(map(float, line.split())) for line in lines[i:i+200]])


silo_width = 20
silo_height = 70
silo_slit_width = 3
A = 0.15  # Amplitude in cm
w = 5     # Angular frequency in rad/s
t_step = 0.1  # Time step

# now animate the particles, each particle is a circle with an id, a position and a radius
# you have for each time step, a list of particles that you need to animate

def animate(i):
    plt.cla()
    t = i * t_step
    y_bottom = A * math.sin(w * t)  # Calculate the y-position of the bottom side
    plt.plot([0, silo_width], [silo_height, silo_height], 'k-', lw=2)
    plt.plot([0, 0], [0, silo_height], 'k-', lw=2)
    plt.plot([silo_width, silo_width], [0, silo_height], 'k-', lw=2)
    plt.plot([0, (silo_width - silo_slit_width)/2], [y_bottom, y_bottom], 'k-', lw=2)
    plt.plot([(silo_width + silo_slit_width)/2, silo_width], [y_bottom, y_bottom], 'k-', lw=2)
    plt.axis('off')
    plt.gca().set_aspect('equal', adjustable='box')
    for particle in particle_list[i]:
        circle = plt.Circle((particle[1], particle[2]), particle[3], color='r')
        plt.gca().add_patch(circle)
    plt.title('Time: ' + str(i))
    return circle, plt.title

anim = animation.FuncAnimation(plt.gcf(), animate, frames=len(particle_list), interval=1, repeat=False)
anim.save('animation.gif', writer='pillow', fps=60)



#plt.show()