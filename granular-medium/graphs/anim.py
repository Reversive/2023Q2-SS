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

plt.plot([0, silo_width], [silo_height, silo_height], 'k-', lw=2)
plt.plot([0, 0], [0, silo_height], 'k-', lw=2)
plt.plot([silo_width, silo_width], [0, silo_height], 'k-', lw=2)
plt.plot([0, (silo_width - silo_slit_width)/2], [0, 0], 'k-', lw=2)
plt.plot([(silo_width + silo_slit_width)/2, silo_width], [0, 0], 'k-', lw=2)

plt.axis('off')
plt.gca().set_aspect('equal', adjustable='box')


plt.show()