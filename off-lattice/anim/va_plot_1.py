import matplotlib.pyplot as plt

etas = [0.1, 0.5, 1, 2, 3.5, 5]
iterations = 1000
# Listas para almacenar los valores de parámetro de orden
va_values = []

# Ejecutar el programa Java con diferentes etas
for eta in etas:
    # Leer el archivo de salida y almacenar los valores de va en la lista
    va_file = f"../va_output_{eta}.txt"
    with open(va_file, "r") as f:
        va_lines = f.read().splitlines()
        va_values.append(list(map(float, va_lines)))

# Crear el gráfico
plt.figure(figsize=(10, 6))
for i, eta in enumerate(etas):
    plt.plot(range(iterations), va_values[i], label=f"$\\eta = {eta}$")

plt.xlabel("Iteraciones", fontsize=20)
plt.ylabel("Va", fontsize=20)
plt.legend()
plt.grid(True)
plt.xticks(fontsize=15)
plt.yticks(fontsize=15)

# Guardar el gráfico en un archivo PNG
plt.savefig("grafico_va.png")

# Mostrar el gráfico
plt.show()
