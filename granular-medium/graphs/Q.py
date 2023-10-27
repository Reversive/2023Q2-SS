import matplotlib.pyplot as plt
from scipy import stats
import numpy as np


# Define the file names
file_names = ["results5.txt", "results10.txt", "results15.txt", "results20.txt", "results30.txt", "results50.txt"]

# Initialize lists to store time and measurement data
time_data = []
measurement_data = []

# Read data from each file
for file_name in file_names:
    with open(file_name, 'r') as file:
        data = file.readlines()[99:]
        time_aux = []
        measurement_aux = []
        i = 0
        for line in data:
            if(i == 0):
                timeLast, measurementLast = line.strip().split(' ')
                measurementLast = float(measurementLast)
                i = 1
            else:
                time, measurement = line.strip().split(' ')
                time_aux.append(float(time) - 10)
                measurement_aux.append(float(measurement) - measurementLast)
        time_data.append(time_aux)
        measurement_data.append(measurement_aux)

# Create the plot
plt.figure(figsize=(10, 6))
plt.xlabel('Tiempo (s)', fontsize=20)
plt.ylabel('Número de partículas que salieron', fontsize=20)

# Define colors for plots
colors = ['b', 'g', 'r', 'c', 'm', 'y']
omega_values = [5, 10, 15, 20, 30, 50]
errors = []
slopes = []
# Plot the data from each file
for i in range(len(file_names)):
    slope, _ = np.polyfit(time_data[i], measurement_data[i], 1)
    slopes.append(slope)
    line = slope * np.array(time_data[i])
    error = np.mean(np.abs(np.array(measurement_data[i]) - line))
    errors.append(error)
    plt.plot(time_data[i], line, color=colors[i], linewidth=1, label=f'ω = {omega_values[i]} rad/s',)

plt.figure(figsize=(10, 6))
plt.errorbar(omega_values, slopes, yerr=errors, fmt='o', color='b', ecolor='r', capsize=5, capthick=2)
plt.xlabel('ω (rad/s)', fontsize=20)
plt.ylabel('Slope', fontsize=20)
plt.title('Slope with Error Bars vs. ω', fontsize=20)
plt.grid()
plt.show()