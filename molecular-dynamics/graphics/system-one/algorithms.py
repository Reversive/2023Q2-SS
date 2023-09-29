import matplotlib.pyplot as plt
import numpy as np

labels = ["Analitic", "Verlet", "Beeman", "Gear Predictor Corrector"]
styles = ['solid', 'dashed', 'dashdot', 'dotted']
colors = ['red', 'blue', 'green', 'black']


A = 1.0
M = 70.0
K = 10000
GAMMA = 100.0

def calculateR(t):
    return A * (np.exp(-(GAMMA/(2*M)) * t)) * (np.cos(np.power((K/M) - (GAMMA*GAMMA/(4*(M*M))), 0.5) * t))

data = np.loadtxt('gpc_10-3.txt')
plt.plot(data[:,0], data[:,1], label=labels[3], linestyle=styles[3], color=colors[3])

data = np.loadtxt('beeman_10-3.txt')
plt.plot(data[:,0], data[:,1], label=labels[2], linestyle=styles[2], color=colors[2])

data = np.loadtxt('verlet_10-3.txt')
plt.plot(data[:,0], data[:,1], label=labels[1], linestyle=styles[1], color=colors[1])

t = np.arange(0.0, 5.0, 0.00001)
analytic = calculateR(t)
plt.plot(t, analytic, label=labels[0], linestyle=styles[0], color=colors[0])

plt.legend()

plt.xticks(fontsize=15)
plt.yticks(fontsize=15)
plt.xlabel('Tiempo (s)', fontsize=15)
plt.ylabel('Posición (m)', fontsize=15)

plt.show()


errors = [[], [], []]



for i in range(2, 6):
    data = np.loadtxt('beeman_10-{}.txt'.format(i))
    analytic = calculateR(data[:,0])
    error = np.mean((analytic - data[:,1]) ** 2)
    errors[0].append(error)

for i in range(2, 6):
    data = np.loadtxt('verlet_10-{}.txt'.format(i))
    analytic = calculateR(data[:,0])
    error = np.mean((analytic - data[:,1]) ** 2)
    errors[1].append(error)

for i in range(2, 6):
    data = np.loadtxt('gpc_10-{}.txt'.format(i))
    analytic = calculateR(data[:,0])
    error = np.mean((analytic - data[:,1]) ** 2)
    errors[2].append(error)


plt.loglog([10**(-i) for i in range(2, 6)], errors[0], 'o', label=labels[2], linestyle=styles[2], color=colors[2])
plt.loglog([10**(-i) for i in range(2, 6)], errors[1], 'o', label=labels[1], linestyle=styles[1], color=colors[1])
plt.loglog([10**(-i) for i in range(2, 6)], errors[2], 'o', label=labels[3], linestyle=styles[3], color=colors[3])


plt.legend()

plt.xticks(fontsize=15)
plt.yticks(fontsize=15)
plt.xlabel('dt (s)', fontsize=15)
plt.ylabel('Error cuadrático medio (m$^2$)', fontsize=15)
plt.show()