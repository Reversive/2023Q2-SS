STATIC_PATH = '../src/main/resources/Static300.txt' #TODO CAMBIAR ESTO CUANDO SE CORRE
PARTICLE_QTY = int(open(STATIC_PATH, 'r').readline().strip())
POSITIONS_PATH = '../positions.csv'
NEIGHBOURS_PATH = '../neighbours.csv'
BENCHMARK_PATH = '../benchmark3.txt' #TODO CAMBIAR ESTO CUANDO SE CORRE BENCHMARK

def parse_positions():
    i = 0
    particles = [[]] * PARTICLE_QTY
    with open(POSITIONS_PATH, 'r') as f:
        while i < PARTICLE_QTY:
            line = f.readline()
            if not line:
                break
            positions = line.rsplit()[0].split(',')
            particles.insert(i, [eval(i) for i in positions])
            i += 1
    return particles

def parse_neighbours():
    neighbours = [[]] * PARTICLE_QTY
    with open(NEIGHBOURS_PATH, 'r') as f:
        while True:
            line = f.readline()
            if not line:
                break
            particles = line.rsplit()[0].split(',')
            neighbours.insert(int(particles[0]), [eval(i) for i in particles[1:]])
    return neighbours

def parse_matrix_size():
    with open(STATIC_PATH, 'r') as file:
        lines = file.readlines()
    if len(lines) >= 2:
        second_line = lines[1].strip()
        matrix_size = float(second_line)
        return matrix_size
    else:
        return 0
    
def parse_times():
    times = []
    first_line = None
    with open(BENCHMARK_PATH, 'r') as f:
        first_line = f.readline()
        while True:
            line = f.readline()
            if not line:
                break
            time_values = line.strip().split(',')
            times.append([int(time_values[0]), float(time_values[1]), float(time_values[2])])
    return first_line, times