import numpy as np
import matplotlib.pyplot as plt
import math
particle_quantity = [10, 20, 30]
color = ['red', 'blue', 'green']

for k in range(3):
    first_file = format("velocities_{}.txt".format(particle_quantity[k]))
    with open(first_file) as f:
        content = f.readlines()
        content = [x.strip() for x in content]
        speeds = []
        for i in range(0,len(content),particle_quantity[k] + 1):
            for j in range(1,particle_quantity[k] + 1):
                velocity = content[i+j].split(' ')[1]
                speeds.append(float(velocity))
        num_particles = len(speeds)
        bins = 60
        p, x = np.histogram(speeds, bins = bins)
        x = x[:-1] + (x[1] - x[0])/2
        plt.plot(x, [i / ((x[1] - x[0]) * num_particles) for i in p], label = 'N = {}'.format(particle_quantity[k]), color = color[k])
        plt.scatter(x, [i / ((x[1] - x[0]) * num_particles) for i in p], color = color[k], marker='o', s=20)

# plot a uniform distribution from 9/21.49 to 12/21.49 with area = 1
x = np.arange(9/21.49, 12/21.49, 0.01)
y = [1 for i in x]
plt.plot(x, y, color = 'black', linestyle = '--', label = 'Distribución uniforme')
plt.xlabel('Velocidad ($\\frac{{\mathrm{rad}}}{{\mathrm{s}}})$', fontsize = 15)
plt.ylabel('Densidad', fontsize = 15)
plt.xticks(fontsize = 15)
plt.yticks(fontsize = 15)
plt.legend(fontsize = 15)
plt.legend()
plt.show()