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
inverse_areas = [1 / A for A in areas]
print(inverse_areas)
print(average_pressures_list)

def error(c, x, y):
    error = 0
    for i in range(len(x)):
        error += (y[i] - c/x[i])**2
    return error

def regression(x, y):
    c = np.arange(0.01465, 0.0269, 0.0001)
    errors = []
    for i in range(len(c)):
        errors.append(error(c[i], x, y))
    return c[np.argmin(errors)], errors


min_val, errors = regression(areas, average_pressures_list)
print(min_val)
# find min val in errors and print it
print(min(errors))
x_values = np.arange(0.01465, 0.0269, 0.0001)
y_values = errors
# plot the results
plt.figure(figsize=(8, 6))
plt.plot(x_values, y_values, color='green')
plt.xlabel("c", fontsize=15)
plt.ylabel("E(c)", fontsize=15)
plt.grid(True)
plt.show()

# DCM = 4D*t
# Y = 4D*X