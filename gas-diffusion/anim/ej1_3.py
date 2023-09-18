import matplotlib.pyplot as plt
import numpy as np

def calculate_area(L):
    return 0.09 * 0.09 + 0.09 * L

L_values = [0.03, 0.05, 0.07, 0.09]

average_pressures = {}
std_dev_pressures = {}  # Agregado: diccionario para desvío estándar

for L in L_values:
    file_name = f"pressure_{L}.txt"
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
plt.plot(inverse_areas, average_pressures_list, 'o', color='green')
plt.xlabel("A⁻¹ (1/m²)", fontsize=15)
plt.xlim(0,110)
plt.ylim(0,2.2)
plt.ylabel("Presión (kg/s$^2$)", fontsize=15)

x_values = np.arange(0, inverse_areas[0] + 5, 0.0001)
y_values = 0.02075 * x_values
plt.plot(x_values, y_values, color='red')
# Agregar leyenda
plt.plot([], [], color='red', label='Ajuste lineal  P = 0.02075 * A⁻¹')
plt.legend(loc='upper left')
plt.grid(True)

plt.show()
