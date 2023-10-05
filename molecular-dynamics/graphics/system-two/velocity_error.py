import numpy as np
import matplotlib.pyplot as plt
particle_quantity = [5, 10, 15, 20, 25, 30]


line = []
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
            average_speeds.append(np.average(velocities))
        line.append(np.average(average_speeds))
        if k == 2:
            plt.errorbar(particle_quantity[k], np.average(average_speeds), yerr = np.std(average_speeds) * 4, label = 'N = {}'.format(particle_quantity[k]), fmt = 'o', capsize = 3, capthick = 1, elinewidth = 1, markeredgewidth = 1, markersize = 5, markeredgecolor = 'black', color = 'black')
        else:
            plt.errorbar(particle_quantity[k], np.average(average_speeds), yerr = np.std(average_speeds) * 2, label = 'N = {}'.format(particle_quantity[k]), fmt = 'o', capsize = 3, capthick = 1, elinewidth = 1, markeredgewidth = 1, markersize = 5, markeredgecolor = 'black', color = 'black')

plt.plot(particle_quantity, line, color = 'purple', linestyle = '-', label = 'Línea de tendencia')
plt.xlabel('N', fontsize = 20)
plt.ylabel('Velocidad promedio ($\\frac{{\mathrm{rad}}}{{\mathrm{s}}})$', fontsize = 20)
plt.xticks(fontsize = 15)
plt.yticks(fontsize = 15)
plt.show()