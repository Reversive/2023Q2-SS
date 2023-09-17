import matplotlib.pyplot as plt
import numpy as np

def calculate_area(L):
    return 0.09 * 0.09 + 0.09 * L

L_values = [0.03, 0.05, 0.07, 0.09]

average_pressures = {}
std_dev_pressures = {}  # Agregado: diccionario para desvío estándar

for L in L_values:
    file_name = f"Pressure_280_L_{L}_v1.txt"
    with open(file_name, 'r') as file:
        lines = file.readlines()
        pressures = [float(line.split()[1]) for line in lines]
        average_pressure = np.mean(pressures)
        std_dev_pressure = np.std(pressures)
        average_pressures[L] = average_pressure
        std_dev_pressures[L] = std_dev_pressure

areas = [calculate_area(L) for L in L_values]
average_pressures_list = [average_pressures[L] for L in L_values]
std_dev_pressures_list = [std_dev_pressures[L] for L in L_values]

inverse_areas = [1 / A for A in areas]

plt.figure(figsize=(8, 6))
plt.plot(inverse_areas, average_pressures_list, 'x', color='green')
plt.xlabel("A⁻¹ (1/m²)", fontsize=15)
plt.ylabel("Presión (kg/s$^2$)", fontsize=15)

x_values = np.arange(inverse_areas[-1] - 1, inverse_areas[0] + 1, 0.0001)
y_values = 0.0204 * x_values
plt.plot(x_values, y_values, color='red')

plt.grid(True)

plt.show()
