STATIC_PATH = '../src/main/resources/Static100.txt'
PARTICLE_QTY = int(open(STATIC_PATH, 'r').readline().strip())
POSITIONS_PATH = '../positions.csv'

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