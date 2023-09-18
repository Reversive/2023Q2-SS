import numpy as np
import matplotlib.pyplot as plt
import os
import math
import statistics

def error(D, x, y):
    error = 0
    for i in range(len(x)):
        error += (y[i] - 4*D*x[i])**2
    return error

def regression(x, y):
    D = np.arange(-0.000012, 0.00005, 0.000001)
    errors = []
    for i in range(len(D)):
        errors.append(error(D[i], x, y))
    return D[np.argmin(errors)], errors

def get_file_means(filename):
    initial_x = []
    initial_y = []
    dcms = {}
    means = []
    stds = []
    
    # get filename from arguments
    with open("data/" + filename) as file:
        lines = file.readlines()
        for i in range(280):
            curLine = lines[i].split()
            initial_x.append(float(curLine[1]))
            initial_y.append(float(curLine[2]))
        
        k = 0
        for i in range(280, len(lines)):
            curLine = lines[i].split()
            x = float(curLine[1])
            y = float(curLine[2])
            z = (x - initial_x[i % 280]) ** 2 + (y - initial_y[i % 280]) ** 2
            if(i % 280 == 0):
                k += 1
            if(k in dcms):
                dcms[k].append(z)
            else:
                dcms[k] = [z]
    
    for key in dcms:
        #print(key, len(dcms[key]))
        means.append(statistics.mean(dcms[key]))
        stds.append(statistics.stdev(dcms[key]))
   
    return means


def main():
    # get all files in data folder
    files = os.listdir("data")
    files.sort()
    #print(files)
    
    # get means for all files
    means = []
    for file in files:
        means.append(get_file_means(file))
    
    # get mean of means and std of means
    mean_of_means = []
    std_of_means = []
    for i in range(len(means[0])):
        temp = []
        for j in range(len(means)):
            temp.append(means[j][i])
        mean_of_means.append(statistics.mean(temp))
        std_of_means.append(statistics.stdev(temp))
    
    plt.errorbar(range(2, len(mean_of_means)*2 + 2, 2), mean_of_means, yerr=std_of_means, fmt='o')
    plt.xlabel("Tiempo (s)", fontsize=15)
    plt.ylabel("Desplazamiento cuadrático medio (m$^2$)", fontsize=15)
    
    # calculate the regression for the first 30 seconds
    D, errors = regression(range(2, len(mean_of_means)*2 + 2, 2), mean_of_means)
    print("D = ", D)
    print("Error = ", min(errors))

    

    plt.figure(figsize=(8, 6))
    plt.plot(range(2, len(mean_of_means)*2 + 2, 2), mean_of_means, 'o')
    plt.xlabel("Tiempo (s)", fontsize=15)
    plt.ylabel("Desplazamiento cuadrático medio (m$^2$)", fontsize=15)
    # add this label to the legend: Ajuste lineal  DCM = 0.000076 * t, align it left
    plt.legend(["Ajuste lineal  DCM = 0.000076 * t"], loc='upper left')
    x_values = range(2, len(mean_of_means)*2 + 2, 2)
    y_values = D * x_values * 4
    plt.plot(x_values, y_values, color='red')

    x_values = np.arange(-0.000012, 0.00005, 0.000001)
    y_values = errors
    # plot the results
    plt.figure(figsize=(8, 6))
    plt.plot(x_values, y_values, color='green')
    plt.xlabel("D", fontsize=15)
    plt.ylabel("E(D)", fontsize=15)
    
    
    plt.show()


if __name__ == "__main__":
    main()
            
