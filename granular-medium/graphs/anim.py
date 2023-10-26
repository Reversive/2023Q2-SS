import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# Read the data from the text file
data_file = 'results.txt'  # Replace with your file name
particles = []
with open(data_file, 'r') as f:
    for line in f:
        info = line.split()
        particle_id = int(info[0])
        position_x, position_y = float(info[1]), float(info[2])
        radius = float(info[6])
        particles.append((particle_id, position_x, position_y, radius))

# Create a figure and axis
fig, ax = plt.subplots()

# Set axis limits
ax.set_xlim(0, 1)  # Adjust the limits according to your data
ax.set_ylim(0, 1)  # Adjust the limits according to your data

# Create a scatter plot
sc = ax.scatter([p[1] for p in particles], [p[2] for p in particles], s=[p[3] for p in particles])

# Update function for animation
def update(frame):
    sc.set_offsets([[p[1] + frame * 0.01, p[2] + frame * 0.01] for p in particles])
    return sc,

# Animate the plot
ani = FuncAnimation(fig, update, frames=100, interval=50, blit=True)

# Show the animation
plt.show()