import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

particle_quantity = 26
# generate random colors based on particle quantity
colors = []
for i in range(particle_quantity):
    colors.append(np.random.rand(3,))
print(colors)

with open('particles_4.txt') as f:
    content = f.readlines()
    content = [x.strip() for x in content]
    positions = []
    for i in range(0,len(content),particle_quantity):
        angles = []
        for j in range(1,particle_quantity):
            angle = content[i+j].split(' ')[1]
            angles.append(float(angle))
        positions.append(angles)

# Parameters
circle_radius = 21.49  # cm
particle_radius_cm = 2.25  # cm
total_time = 180  # seconds
time_step = 0.01  # Decreased time step for faster animation
n_particles = len(positions[0])  # Number of particles based on your data

# Calculate the number of time steps
num_steps = int(total_time / time_step)

# Function to update the animation
def update(frame):
    plt.clf()

    # Get particle angles for the current time step
    angles = positions[frame]

    # Calculate particle positions
    x = circle_radius * np.cos(angles)
    y = circle_radius * np.sin(angles)

    # Plot the circle
    circle = plt.Circle((0, 0), circle_radius, color='black', fill=False, linewidth=0.5)
    plt.gca().add_patch(circle)

    # Plot the particles as circles with the correct size
    for i in range(n_particles):
        particle = plt.Circle((x[i], y[i]), particle_radius_cm, color=colors[i], fill=True)
        plt.gca().add_patch(particle)

    # Set plot limits and remove axis labels and ticks
    plt.xlim(-circle_radius * 1.1, circle_radius * 1.1)
    plt.ylim(-circle_radius * 1.1, circle_radius * 1.1)
    plt.gca().set_aspect('equal', adjustable='box')
    plt.axis('off')

    return circle

fig, ax = plt.subplots()
ani = FuncAnimation(fig, update, frames=num_steps, repeat=False, interval=1)

# ani.save('circle.gif', writer='pillow', fps=30, dpi=50)

plt.show(block=False)