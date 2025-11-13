from collections import defaultdict

def parse_slides(n, slide_data):
    slide_map = {}
    for x1, y1, x2, y2 in slide_data:
        dx = 1 if x2 > x1 else -1
        dy = 1 if y2 > y1 else -1
        length = abs(x2 - x1)
        for i in range(length + 1):
            px = x1 + i * dx
            py = y1 + i * dy
            slide_map[(px, py)] = (x2, y2)
    return slide_map

def simulate_ball(slide_map, x, y, energy):
    while True:
        # Gravity pull
        while x > 0 and (x - 1, y) not in slide_map:
            x -= 1

        # Slide if possible
        if (x, y) in slide_map:
            dest_x, dest_y = slide_map[(x, y)]
            dist = abs(dest_x - x)
            if energy >= dist:
                energy -= dist
                x, y = dest_x, dest_y
                continue
            elif energy >= x * y:
                energy -= x * y
                x, y = dest_x, dest_y
                continue
            else:
                break
        else:
            if x == 0 or (x - 1, y) not in slide_map:
                break
            else:
                x -= 1
    return x, y
n = int(input())
slide_data = [tuple(map(int, input().split())) for _ in range(n)]
start_x, start_y, energy = map(int, input().split())
slide_map = parse_slides(n, slide_data)
final_x, final_y = simulate_ball(slide_map, start_x, start_y, energy)
print(final_x, final_y)

