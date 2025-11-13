def parse_slides(n, slide_data):
    slides = {}
    for line in slide_data:
        x1, y1, x2, y2 = map(int, line.split())
        dx = 1 if x2 > x1 else -1
        dy = 1 if y2 > y1 else -1
        length = abs(x2 - x1)
        for i in range(length + 1):
            x = x1 + i * dx
            y = y1 + i * dy
            slides[(x, y)] = (x + dx, y + dy)
    return slides

def simulate_ball(slides, x, y, energy):
    while True:
        # Gravity pull
        while (x, y) not in slides and x > 0:
            x -= 1
        if x == 0 or energy <= 0:
            return x, y

        # Slide movement
        while (x, y) in slides:
            next_x, next_y = slides[(x, y)]
            energy -= 1
            if energy < 0:
                return x, y
            x, y = next_x, next_y

        # Unlock if stuck
        if (x, y) not in slides and x > 0:
            unlock_cost = x * y
            if energy >= unlock_cost:
                energy -= unlock_cost
                continue
            else:
                return x, y

# Input
n = int(input())
slide_data = [input() for _ in range(n)]
start_x, start_y, energy = map(int, input().split())

# Process
slides = parse_slides(n, slide_data)
final_x, final_y = simulate_ball(slides, start_x, start_y, energy)

# Output
print(f"{final_x} {final_y}")
