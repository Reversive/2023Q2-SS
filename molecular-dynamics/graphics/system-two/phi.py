import numpy as np
import matplotlib.pyplot as plt

phi_total = []
for k in range(3):
    first_positions = []
    second_positions = []
    particle_quantity = 26

    # open particles_i.txt and particles_i+1.txt use format function to set i
    first_file = format("particles_{}.txt".format(k))
    with open(first_file) as f:
        content = f.readlines()
        content = [x.strip() for x in content]
        for i in range(0,len(content),particle_quantity):
            angles = []
            for j in range(1,particle_quantity):
                angle = content[i+j].split(' ')[1]
                angles.append(float(angle))
            first_positions.append(angles)

    second_file = format("particles_{}.txt".format(k+1))
    with open(second_file) as f:
        content = f.readlines()
        content = [x.strip() for x in content]
        for i in range(0,len(content),particle_quantity):
            angles = []
            for j in range(1,particle_quantity):
                angle = content[i+j].split(' ')[1]
                angles.append(float(angle))
            second_positions.append(angles)
    
    phi = []
    for t in range(len(first_positions)):
        summation = 0
        for i in range(len(first_positions[t])):
            # do the norm 2 of 
            summation += np.linalg.norm(min(np.array(first_positions[t][i]) - np.array(second_positions[t][i]),2*np.pi - abs(np.array(second_positions[t][i]) - np.array(first_positions[t][i]))))
        phi.append(summation)
    phi.pop()
    phi_total.append(phi)
    

legend_labels = ['k = {}'.format(i+1) for i in range(len(phi_total))]

colors = []
for i in range(4):
    colors.append(np.random.rand(3,))


for i in range(len(phi_total)):
    # plot with different colors, make y axis log scale, originally y axis went from 0 to 180 with 0.1 step size
    plt.plot(np.arange(0,180,0.1),phi_total[i], label = legend_labels[i], color = colors[i])
    plt.yscale('log')


plt.xlabel('Tiempo (s)',  fontsize = 20)
plt.ylabel(''r'$\phi$', fontsize = 20)
plt.xticks(fontsize = 15)
plt.yticks(fontsize = 15)
plt.legend(fontsize = 15)
plt.show()


