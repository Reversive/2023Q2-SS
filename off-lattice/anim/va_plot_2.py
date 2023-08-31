import numpy as np
import matplotlib.pyplot as plt

L_values = [7]
N_values = [300]
etas = [0.1, 0.5, 1, 2, 3.5, 5]

num_executions = 15
num_results_per_execution = 200

va_values = {L: {} for L in L_values}

for L in L_values:
    for eta in etas:
        last_va_values = []

        va_file = f"../{L}_mult_va_output{eta}.txt"
        with open(va_file, "r") as f:
            va_lines = f.read().splitlines()

            va_execution = []
            execution_count = 0

            for line in va_lines:
                if execution_count < num_executions:
                    if len(va_execution) < num_results_per_execution:
                        va_value = float(line)
                        va_execution.append(va_value)
                    else:
                        last_va_values.append(va_execution[-1])
                        va_execution = []
                        execution_count += 1

        va_values[L][eta] = last_va_values

va_averages = {L: [np.mean(va_values[L][eta]) for eta in etas] for L in L_values}
va_stddevs = {L: [np.std(va_values[L][eta]) for eta in etas] for L in L_values}

# Crear el gráfico
plt.figure(figsize=(10, 6))
colors = ['blue', 'green', 'red']

for i, L in enumerate(L_values):
    plt.errorbar(etas, va_averages[L], yerr=va_stddevs[L], fmt='o', capsize=5, label=f"L = {L}, N = {N_values[i]}", color=colors[i])

plt.xlabel(f"$\\eta$", fontsize=20)
plt.ylabel("[Va]")
plt.grid(True)
plt.xticks(fontsize=15)
plt.yticks(fontsize=15)
plt.ylabel('Va', fontsize=20)
plt.legend()
plt.show()





# import numpy as np
# import matplotlib.pyplot as plt
#
# etas = [0.1, 0.5, 1, 2, 2.75, 3.5, 4, 4.5, 5]
# num_executions = 15
# num_results_per_execution = 200
#
# va_values = {}  # Un diccionario para almacenar los valores de va por eta
#
# for eta in etas:
#     all_va_values = []  # Lista para almacenar todos los valores de va en todas las ejecuciones
#
#     va_file = f"../mult_va_output{eta}.txt"  # Ajusta la ruta según tus archivos
#     with open(va_file, "r") as f:
#         va_lines = f.read().splitlines()
#
#         va_execution = []  # Lista para almacenar los valores de va en una ejecución
#
#         for line in va_lines:
#             if len(va_execution) < num_results_per_execution:
#                 va_value = float(line)
#                 va_execution.append(va_value)
#             else:
#                 all_va_values.append(va_execution)
#                 va_execution = []
#
#         va_values[eta] = all_va_values
#
# # Calcular promedios y desvíos estándar por eta
# va_averages = [np.mean([va_values[eta][exec_num][-1] for exec_num in range(num_executions)]) for eta in etas]
# va_stddevs = [np.std([va_values[eta][exec_num][-1] for exec_num in range(num_executions)]) for eta in etas]
#
# # Crear el gráfico
# plt.figure(figsize=(10, 6))
# plt.errorbar(etas, va_averages, yerr=va_stddevs, fmt='o', capsize=5)
# plt.xlabel(f"$\\eta$", fontsize=20)
# plt.ylabel("[Va]")
# plt.grid(True)
# plt.xticks(fontsize=15)
# plt.yticks(fontsize=15)
# plt.ylabel('Va', fontsize=20)
# plt.show()
