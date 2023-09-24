# read a file with format <time> <position_x> <position_y>
# ignore <position_y> and plot <position_x> vs <time>

import matplotlib.pyplot as plt
import numpy as np

# read file
data = np.loadtxt('worm.txt')

# plot
plt.plot(data[:,0], data[:,1])
plt.xlabel('time')
plt.ylabel('position')
plt.show()
