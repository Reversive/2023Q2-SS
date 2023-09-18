import matplotlib.pyplot as plt
import numpy as np

def calculate_area(L):
    return 0.09 * 0.09 + 0.09 * L

L_values = [0.03, 0.05, 0.07, 0.09]

average_pressures = {}
std_dev_pressures = {}  # Agregado: diccionario para desvío estándar

for filename in ["pressure1.txt", "pressure2.txt", "pressure3.txt", "pressure4.txt"]:
    data = np.loadtxt(filename)
    mean_values = data[:, 0] 
    std_values = data[:, 1]
    means.append(mean_values)
    stds.append(std_values)

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
plt.errorbar(inverse_areas, average_pressures_list, yerr=std_dev_pressures_list, fmt='o', color='green', capsize=5)  # Agregado: barras de error
plt.xlabel("A⁻¹ (1/m²)", fontsize=15)
plt.ylabel("Presión (kg/s$^2$)", fontsize=15)

plt.grid(True)

plt.show()
