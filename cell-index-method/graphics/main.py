import matplotlib.pyplot as plt
from parseCSV import *

positions = parse_positions()

plt.figure(figsize=(9, 8), dpi=80)


for i, position in enumerate(positions):
    if i == PARTICLE_QTY:
        break
    plt.plot(position[0], position[1], 'o', color='#FFAAAF', markersize=position[2] * 25)
plt.show()
