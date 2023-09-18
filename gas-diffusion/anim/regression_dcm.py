import matplotlib.pyplot as plt
import numpy as np

def error(D, x, y):
    error = 0
    for i in range(len(x)):
        error += (y[i] - 4*D*x[i])**2
    return error

def regression(x, y):
    D = np.arange(-5, 5, 0.1)
    errors = []
    for i in range(len(D)):
        errors.append(error(D[i], x, y))
    return D[np.argmin(errors)], errors


