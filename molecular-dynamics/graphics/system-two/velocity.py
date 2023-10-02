import numpy as np
import matplotlib.pyplot as plt
particle_quantity = [5, 10, 15, 20, 25, 30]


for k in range(6):
    first_file = format("velocities_{}.txt".format(particle_quantity[k]))
    with open(first_file) as f:
        content = f.readlines()
        content = [x.strip() for x in content]
        average_speeds = []
        for i in range(0,len(content),particle_quantity[k] + 1):
            velocities = []
            for j in range(1,particle_quantity[k] + 1):
                velocity = content[i+j].split(' ')[1]
                velocities.append(float(velocity))
            average_speeds.append(np.mean(velocities))
        average_speeds.pop()
        plt.plot(np.arange(0,180,0.1), average_speeds, label = 'N = {}'.format(particle_quantity[k]))

plt.xlabel('Tiempo (s)',  fontsize = 20)
plt.ylabel('Velocidad promedio (rad/s)', fontsize = 20)
plt.xticks(fontsize = 15)
plt.yticks(fontsize = 15)
plt.legend(fontsize = 15)
plt.show()


