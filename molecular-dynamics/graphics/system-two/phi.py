import numpy as np
import matplotlib.pyplot as plt

phi_total = []
for k in range(4):
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
            summation += min(abs(np.array(first_positions[t][i]) - np.array(second_positions[t][i])),abs(2*np.pi - abs(np.array(second_positions[t][i]) - np.array(first_positions[t][i]))))
        phi.append(summation)
    phi.pop()
    phi_total.append(phi)
    

# plot all the phi values from 0 to 180 seconds with a time step of 0.1 seconds, for each of the 8 trials, each one with different colors
# plot phi vs time for each trial

# print phi_total lengths
print(len(phi_total[0]))
print(len(phi_total[1]))
print(len(phi_total[2]))
print(len(phi_total[3]))

legend_labels = ['k = {}'.format(i+1) for i in range(len(phi_total))]

colors = []
for i in range(4):
    colors.append(np.random.rand(3,))


for i in range(len(phi_total)):
    # plot with different colors
    plt.plot(np.arange(0,180,0.1),phi_total[i],color=colors[i], label=legend_labels[i])

plt.xlabel('Tiempo (s)',  fontsize = 15)
plt.ylabel(''r'$\phi$', fontsize = 15)
plt.xticks(fontsize = 15)
plt.yticks(fontsize = 15)
plt.legend(fontsize = 15)
plt.show()


