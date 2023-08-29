import matplotlib.pyplot as plt
import numpy as np
import re

file_paths = ['../va_mean_100.txt', '../va_mean_200.txt', '../va_mean_800.txt', '../va_mean_1600.txt', '../va_mean_2600.txt', '../va_mean_4000.txt']

means = []
std_devs = []
legends = []

for file_path in file_paths:
    with open(file_path, 'r') as file:
        match = re.search(r'va_mean_(\d+)\.txt', file_path)
        if match:
            n = int(match.group(1))
            legend_value = n / 400
            legends.append(legend_value)
        data = [float(line.strip()) for line in file.readlines()]
        mean = np.mean(data)
        std_dev = np.std(data)
        means.append(mean)
        std_devs.append(std_dev)

plt.figure(figsize=(10, 6))
plt.errorbar(legends, means, yerr=std_devs, fmt='o', capsize=5)

plt.xlabel('ρ', fontsize=20)
plt.xticks(range(0, 11, 1), fontsize=15)
plt.yticks(fontsize=15)
plt.ylabel('Va', fontsize=20)
plt.legend()
plt.grid(True)
plt.show()