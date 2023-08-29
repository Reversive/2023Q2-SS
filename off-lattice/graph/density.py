import matplotlib.pyplot as plt
import re

file_paths = ['../va_output100.txt', '../va_output200.txt', '../va_output800.txt', '../va_output1600.txt', '../va_output2600.txt', '../va_output4000.txt']

data = []
legends = []

for file_path in file_paths:
    match = re.search(r'va_output(\d+)\.txt', file_path)
    if match:
        n = int(match.group(1))
        legend_value = n / 400
        legends.append(legend_value)
    with open(file_path, 'r') as file:
        va_values = [float(line.strip()) for line in file]
        data.append(va_values)

plt.figure(figsize=(10, 6))

for i, (va_values, legend) in enumerate(zip(data, legends)):
    plt.plot(range(1, len(va_values) + 1), va_values, label=f'ρ = {legend:.2f}')

plt.xticks(range(0, 501, 50), fontsize=15)
plt.yticks(fontsize=15)
plt.xlabel('Iteraciones', fontsize=20)
plt.ylabel('Va', fontsize=20)
plt.legend()
plt.grid(True)
plt.show()