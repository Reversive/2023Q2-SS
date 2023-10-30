import matplotlib.pyplot as plt
from scipy import stats
import numpy as np


# Define the file names
file_names = ["resultsD3.0.txt", "resultsD4.0.txt", "resultsD5.0.txt", "resultsD6.0.txt"]

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
omega_values = [3,4,5,6]
# Plot the data from each file
slopes = []
errors = []
for i in range(len(file_names)):
    slope, intercept, rv, pv, std_slope = stats.linregress(np.array(time_data[i]), np.array(measurement_data[i]), alternative='two-sided')
    plt.plot(time_data[i], measurement_data[i], colors[i], label='ω = ' + str(omega_values[i]) + ' Hz')
    plt.plot(time_data[i], intercept + slope * np.array(time_data[i]), colors[i] + '--')
    slopes.append(slope)
    errors.append(std_slope)
plt.show()

# make 6 colors
colors = ['b', 'g', 'r', 'c', 'm', 'y']

plt.figure(figsize=(10, 6))
plt.xlabel('D (cm)', fontsize=20)
plt.ylabel('Q', fontsize=20)
plt.errorbar(omega_values, slopes, fmt='^', color='k', markersize=10)
# connect the points with a line
plt.plot(omega_values, slopes, 'k--')
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.show()
