import matplotlib.pyplot as plt

# Define the file names
file_names = ["resultsD3.0.txt", "resultsD4.0.txt", "resultsD5.0.txt", "resultsD6.0.txt"]

# Initialize lists to store time and measurement data
time_data = []
measurement_data = []

# Read data from each file
for file_name in file_names:
    with open(file_name, 'r') as file:
        data = file.readlines()
        time_aux = []
        measurement_aux = []
        for line in data:
            time, measurement = line.strip().split(' ')
            time_aux.append(float(time))
            measurement_aux.append(float(measurement))
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
for i in range(len(file_names)):
    plt.plot(time_data[i], measurement_data[i], color=colors[i],
             label=f'D = {omega_values[i]} cm', linewidth=1.5)

plt.xlim(0, 1000)  # Set the x-axis limit to 1000
plt.legend()
plt.show()