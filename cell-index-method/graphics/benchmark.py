import matplotlib.pyplot as plt
from parseCSV import *

m, times = parse_times()

N_values = [data[0] for data in times]
CIM_times = [data[1] for data in times]
BRUTE_times = [data[2] for data in times]

plt.figure(figsize=(9, 9), dpi=80)

plt.plot(N_values, CIM_times, color='blue', label='Cell Index Method')
plt.plot(N_values, BRUTE_times, color='orange', label='Brute Force')

plt.xticks(N_values)

plt.xlabel('N')
plt.ylabel('Time (s)')
plt.title('Cell Index Method vs Brute Force calculation time efficiency - rc = 1, r = 0.25, L = 20, M = ' + m)
plt.legend()
plt.show()