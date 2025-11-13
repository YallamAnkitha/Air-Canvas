import copy

def read_cube(n):
    cube = {}
    faces = ['base', 'back', 'top', 'front', 'left', 'right']
    for face in faces:
        cube[face] = [input().split() for _ in range(n)]
    return cube

def is_solved(face):
    color = face[0][0]
    return all(cell == color for row in face for cell in row)

def is_nearly_solved(face):
    flat = [cell for row in face for cell in row]
    for color in set(flat):
        if flat.count(color) == len(flat) - 1:
            return True
    return False

def rotate_matrix(mat, direction):
    n = len(mat)
    if direction == 'right':
        return [list(reversed(col)) for col in zip(*mat)]
    elif direction == 'left':
        return [list(col) for col in zip(*mat[::-1])]
    return mat

def apply_instruction(cube, instr, n):
    parts = instr.strip().split()
    if not parts:
        return
    if parts[0] == 'turn':
        if parts[1] == 'left':
            cube['front'], cube['left'], cube['back'], cube['right'] = cube['right'], cube['front'], cube['left'], cube['back']
            cube['top'] = rotate_matrix(cube['top'], 'right')
            cube['base'] = rotate_matrix(cube['base'], 'left')
        elif parts[1] == 'right':
            cube['front'], cube['right'], cube['back'], cube['left'] = cube['left'], cube['front'], cube['right'], cube['back']
            cube['top'] = rotate_matrix(cube['top'], 'left')
            cube['base'] = rotate_matrix(cube['base'], 'right')
    elif parts[0] == 'rotate':
        if parts[1] == 'front':
            cube['front'], cube['base'], cube['back'], cube['top'] = cube['top'], cube['front'], cube['base'], cube['back']
            cube['left'] = rotate_matrix(cube['left'], 'right')
            cube['right'] = rotate_matrix(cube['right'], 'left')
        elif parts[1] == 'back':
            cube['front'], cube['top'], cube['back'], cube['base'] = cube['base'], cube['front'], cube['top'], cube['back']
            cube['left'] = rotate_matrix(cube['left'], 'left')
            cube['right'] = rotate_matrix(cube['right'], 'right')
        elif parts[1] == 'left':
            cube['top'], cube['left'], cube['base'], cube['right'] = cube['right'], cube['top'], cube['left'], cube['base']
            cube['front'] = rotate_matrix(cube['front'], 'left')
            cube['back'] = rotate_matrix(cube['back'], 'right')
        elif parts[1] == 'right':
            cube['top'], cube['right'], cube['base'], cube['left'] = cube['left'], cube['top'], cube['right'], cube['base']
            cube['front'] = rotate_matrix(cube['front'], 'right')
            cube['back'] = rotate_matrix(cube['back'], 'left')
    else:
        face, idx, direction = parts[0], int(parts[1]) - 1, parts[2]
        if face not in cube or not (0 <= idx < n):
            return
        if direction in ['left', 'right']:
            row = cube[face][idx]
            if direction == 'left':
                cube[face][idx] = row[1:] + row[:1]
            else:
                cube[face][idx] = row[-1:] + row[:-1]
        elif direction in ['up', 'down']:
            col = [cube[face][i][idx] for i in range(n)]
            if direction == 'up':
                col = col[1:] + col[:1]
            else:
                col = col[-1:] + col[:-1]
            for i in range(n):
                cube[face][i][idx] = col[i]

def solve(n, k, cube, instructions):
    for i in range(k):
        temp = copy.deepcopy(cube)
        for j in range(k):
            if i != j:
                apply_instruction(temp, instructions[j], n)
        for face in temp.values():
            if is_solved(face):
                return instructions[i]
    for i in range(k):
        temp = copy.deepcopy(cube)
        for j in range(k):
            if i != j:
                apply_instruction(temp, instructions[j], n)
        for face in temp.values():
            if is_nearly_solved(face):
                return "Faulty\n" + instructions[i]
    return "Not Possible"

# Input
n, k = map(int, input().split())
cube = read_cube(n)
instructions = [input().strip() for _ in range(k)]

# Output
print(solve(n, k, cube, instructions))
