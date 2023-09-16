import matplotlib.pyplot as plt
import matplotlib.animation as animation
from matplotlib.patches import Circle

R = 0.0015

def parse_file(filename):
    data = []

    with open(filename, 'r') as file:
        num_particles = int(file.readline().strip())
        max_value = float(file.readline().strip())
        L = float(file.readline().strip())

        while True:
            iteration_line = file.readline().strip()
            if not iteration_line:
                break

            iteration_data = []

            for _ in range(num_particles):
                particle_data = file.readline().strip().split(' ')
                x = float(particle_data[1])
                y = float(particle_data[2])

                iteration_data.append((x, y))

            data.append(iteration_data)

    return data, max_value, L

# def angle_to_color(angle):
#     color_map = mcolors.LinearSegmentedColormap.from_list('angle_cmap', ['red', 'orange', 'yellow', 'green', 'cyan', 'blue', 'purple'])
#     normalized_angle = angle / (2 * np.pi)
#     return color_map(normalized_angle)

def update_plot(frame):
    plt.clf()
    plt.gca().set_aspect('equal', adjustable='box')
    #plt.title(f"Iteration {frame+1}")

    for x1, y1, x2, y2 in line_segments:
        plt.plot([x1, x2], [y1, y2], color='black', linestyle='-', linewidth=1)

    for x, y in data[frame]:
        circle = Circle((x, y), R)
        plt.gca().add_patch(circle)

    plt.xlim(0, max_value * 2)
    plt.ylim(0, max_value)
    plt.xticks([])  # Removes tick labels on the x-axis
    plt.yticks([])

input = './output.txt'
data, max_value, L = parse_file(input)

fig = plt.figure()

topy = max_value/2 + L/2
bottomy = max_value/2 - L/2
line_segments = [
(max_value, topy, max_value, max_value),
(max_value, 0, max_value, bottomy),
(max_value, topy, 2*max_value, topy),
(max_value, bottomy, 2*max_value, bottomy)
]  # Formato: (x1, y1, x2, y2)

for x1, y1, x2, y2 in line_segments:
    plt.plot([x1, x2], [y1, y2], color='black', linestyle='-', linewidth=1)

plt.xlabel(None)
plt.ylabel(None)
plt.xticks([])
plt.yticks([])
ani = animation.FuncAnimation(fig, update_plot, frames=len(data), interval=16.67, repeat=False)
ani.save('animation.gif', writer='pillow', fps=60, dpi=300)
#plt.show()