import numpy as np
import matplotlib.pyplot as plt

data = np.loadtxt("pressure_0.05.txt", skiprows=1)
elapsed_time = data[:, 0]
left_pressure = data[:, 1]
right_pressure = data[:, 2]

plt.figure(figsize=(10, 6))

plt.scatter(elapsed_time, left_pressure, label="Contenedor izquierdo", marker='x', s=10)
plt.scatter(elapsed_time, right_pressure, label="Contenedor derecho", marker='x', s=10, color='g')

plt.plot(elapsed_time, left_pressure, linestyle='-', marker='', color='b')
plt.plot(elapsed_time, right_pressure, linestyle='-', marker='', color='g')

plt.xlabel("Tiempo (s)", fontsize=15)
plt.ylabel("Presión (kg/s$^2$)", fontsize=15)
plt.legend()


plt.show()