import matplotlib.pyplot as plt

# Define the file names
file_names = ["results5.txt", "results10.txt", "results15.txt", "results20.txt", "results30.txt", "results50.txt"]

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
omega_values = [5, 10, 15, 20, 30, 50]

# Plot the data from each file
for i in range(len(file_names)):
    plt.plot(time_data[i], measurement_data[i], color=colors[i],
             label=f'ω = {omega_values[i]} Hz', linewidth=1.5)

plt.xlim(0, 1000)  # Set the x-axis limit to 1000
plt.legend(fontsize=20)
plt.xticks(fontsize=20)
plt.yticks(fontsize=20)
plt.show()