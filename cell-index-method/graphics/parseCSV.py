STATIC_PATH = '../src/main/resources/Static100.txt'
PARTICLE_QTY = int(open(STATIC_PATH, 'r').readline().strip())
POSITIONS_PATH = '../positions.csv'
NEIGHBOURS_PATH = '../neighbours.csv'

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
        matrix_size = int(second_line)
        return matrix_size
    else:
        return 0