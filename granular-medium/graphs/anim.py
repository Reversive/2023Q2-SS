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
A = 0.15  
w = 15    
t_step = 0.1 

def animate(i):
    plt.cla()
    t = i * t_step
    y_bottom = A * math.sin(w * t) 
    plt.plot([0, silo_width], [silo_height, silo_height], 'k-')
    plt.plot([0, 0], [0, silo_height], 'k-')
    plt.plot([silo_width, silo_width], [0, silo_height], 'k-')
    plt.plot([0, (silo_width - silo_slit_width)/2], [y_bottom, y_bottom], 'k-')
    plt.plot([(silo_width + silo_slit_width)/2, silo_width], [y_bottom, y_bottom], 'k-')

    plt.axis('off')
    plt.gca().set_aspect('equal', adjustable='box')
    for particle in particle_list[i]:
        circle = plt.Circle((particle[1], particle[2]), particle[3], color='r', fill=False)
        plt.gca().add_patch(circle)
    return circle, plt.title

anim = animation.FuncAnimation(plt.gcf(), animate, frames=len(particle_list), interval=1, repeat=False)
anim.save('anim_w_5_d_3_u_0.3.gif', writer='pillow', fps=60)



# plt.show()