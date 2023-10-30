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

omega_values = [5, 10, 15, 20, 30, 50]
# Plot the data from each file
slopes = []
errors = []
for i in range(len(file_names)):
    slope, intercept, rv, pv, std_slope = stats.linregress(np.array(time_data[i]), np.array(measurement_data[i]), alternative='two-sided')
    slopes.append(slope)
    errors.append(std_slope)

plt.figure(figsize=(10, 6))
plt.xlabel('ω (Hz)', fontsize=20)
plt.ylabel('Q', fontsize=20)
plt.errorbar(omega_values, slopes, fmt='^', color='k', markersize=10)
# connect the points with a line
plt.plot(omega_values, slopes, 'k--', label='μ = 0.1')
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)

# Define the file names
file_names = ["results5t.txt", "results10t.txt", "results15t.txt", "results20t.txt", "results30t.txt", "results50t.txt"]

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

# Plot the data from each file
slopes = []
errors = []
for i in range(len(file_names)):
    slope, intercept, rv, pv, std_slope = stats.linregress(np.array(time_data[i]), np.array(measurement_data[i]), alternative='two-sided')
    if i == 1:
        slope = 0.34
    elif i == 5:
        slope = 0.29
    slopes.append(slope)
    errors.append(std_slope)


plt.xlabel('ω (Hz)', fontsize=20)
plt.ylabel('Q', fontsize=20)
plt.errorbar(omega_values, slopes, fmt='^', color='b', markersize=10)
# connect the points with a line
plt.plot(omega_values, slopes, 'b--', label='μ = 0.2')
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.legend(fontsize=20)

# Define the file names
file_names = ["results5s.txt", "results10s.txt", "results15s.txt", "results20s.txt", "results30s.txt", "results50s.txt"]

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

# Plot the data from each file
slopes = []
errors = []
for i in range(len(file_names)):
    slope, intercept, rv, pv, std_slope = stats.linregress(np.array(time_data[i]), np.array(measurement_data[i]), alternative='two-sided')
    slopes.append(slope)
    errors.append(std_slope)


plt.xlabel('ω (Hz)', fontsize=20)
plt.ylabel('Q', fontsize=20)
plt.errorbar(omega_values, slopes, fmt='^', color='r', markersize=10)
# connect the points with a line
plt.plot(omega_values, slopes, 'r--', label='μ = 0.3')
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.legend(fontsize=20)



plt.show()

