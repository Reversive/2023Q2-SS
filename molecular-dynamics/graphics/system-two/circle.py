# i have a circle of radius R
# i have N particles inside the circle, i have the angle of each particle
# i have for the N particles, for a time step, the new angle of each particle
# make the animation of the particles moving

import numpy as np
import matplotlib.pyplot as plt
import matplotlib.animation as animation
import math
import random

with open('particles.txt') as f:
    content = f.readlines()
    content = [x.strip() for x in content]
    positions = []
    for i in range(0,len(content),16):
        angles = []
        for j in range(1,16):
            angle = content[i+j].split(' ')[1]
            angles.append(float(angle))
        positions.append(angles)
        

fig = plt.figure()
ax = plt.axes(xlim=(-25, 25), ylim=(-25, 25))
particles = []
for i in range(len(positions[0])):
    particle, = ax.plot([], [], 'bo', ms=6)
    particles.append(particle)

def init():
    for i in range(len(positions[0])):
        particles[i].set_data([], [])
    return particles

def animate(i):
    plt.axis('equal')  # Set the aspect ratio to make it a perfect circle
    plt.xlim(-35, 35)  # Adjust the limits to match the circle's radius and center
    plt.ylim(-35, 35)
    plt.xticks([])  # Removes tick labels on the x-axis
    plt.yticks([])
    plt.xlabel(None)
    plt.ylabel(None)

    particle_radius = 2.25

    for j in range(len(positions[0])):
        x = 21.49*math.cos(positions[i][j])
        y = 21.49*math.sin(positions[i][j])
        particles[j].set_data(x, y)
        particles[j].set_markersize(particle_radius * 2)  # Set marker size based on particle radius

    circle = plt.Circle((0, 0), 21.49, color='black', fill=False, linestyle='-', linewidth=0.5)
    ax.add_artist(circle)
    return particles

plt.xlabel(None)
plt.ylabel(None)
plt.xticks([])
plt.yticks([])

# cambiar el interval si queres que vaya mas lento/rapido
anim = animation.FuncAnimation(fig, animate, init_func=init,
                                    frames=len(positions), interval=50, blit=True)

anim.save('circle.gif', writer='pillow', fps=60, dpi=300)

# plt.show()

