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
ax = plt.axes(xlim=(-2.5, 2.5), ylim=(-2.5, 2.5))
particles = []
for i in range(len(positions[0])):
    particle, = ax.plot([], [], 'bo', ms=6)
    particles.append(particle)

def init():
    for i in range(len(positions[0])):
        particles[i].set_data([], [])
    return particles

def animate(i):
    for j in range(len(positions[0])):
        x = 2.25*math.cos(positions[i][j])
        y = 2.25*math.sin(positions[i][j])
        particles[j].set_data(x, y)
    return particles

# cambiar el interval si queres que vaya mas lento/rapido
anim = animation.FuncAnimation(fig, animate, init_func=init,
                                    frames=len(positions), interval=50, blit=True)

plt.show()

