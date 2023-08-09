import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
from parseCSV import *


PARTICLE_SIZE = 50
FONT_SIZE = 10

positions = parse_positions()
neighbours = parse_neighbours()

plt.figure(figsize=(9, 9), dpi=80)


for i, position in enumerate(positions):
    if i == PARTICLE_QTY:
        break
    plt.plot(position[0], position[1], 'o', color='#76FF7B', markersize=position[2] * PARTICLE_SIZE)
    plt.text(position[0], position[1], str(i), fontsize=FONT_SIZE, color='black', ha='center', va='center')
plt.show()

kill = False
while not kill:

    particle = input("Choose a particle by index, press a non-int char to quit\n")
    try:
        particle = int(particle)
        try: 
            while particle < 0 or particle > PARTICLE_QTY - 1:
                particle = input("Invalid particle index, choose a new particle or press a non-int char to quit\n")
                particle = int(particle)

            if kill == True:
                break
            
            for i, position in enumerate(positions):
                if i == PARTICLE_QTY:
                    break
                plt.text(position[0], position[1], str(i), fontsize=FONT_SIZE, color='black', ha='center', va='center')
                if i == particle or i in neighbours[particle]:
                    if i == particle:
                        plt.plot(position[0], position[1], 'o', color='#FFFF14', markersize=position[2] * PARTICLE_SIZE)
                    else:
                        plt.plot(position[0], position[1], 'o', color='#E50000', markersize=position[2] * PARTICLE_SIZE)
                else:
                    plt.plot(position[0], position[1], 'o', color='#76FF7B', markersize=position[2] * PARTICLE_SIZE)

            chosen = mpatches.Patch(color='#FFFF14', label='Chosen')
            neighbour = mpatches.Patch(color='#E50000', label='Neighbour')
            unrelated = mpatches.Patch(color='#76FF7B', label='Unrelated')
            plt.legend(handles=[chosen, neighbour, unrelated], loc='best')        
            plt.show()
        except ValueError:
            kill = True
            break 
    except ValueError:
        kill = True
        break 