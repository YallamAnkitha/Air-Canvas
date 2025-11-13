#include <stdio.h>
#include <stdlib.h>

#define MAX 51

typedef struct {
    int dx, dy;
    int exists;
} Slide;

Slide grid[MAX][MAX];

void add_slide(int x1, int y1, int x2, int y2) {
    int dx = (x2 > x1) ? 1 : -1;
    int dy = (y2 > y1) ? 1 : -1;
    int len = abs(x2 - x1);
    for (int i = 0; i <= len; i++) {
        int x = x1 + i * dx;
        int y = y1 + i * dy;
        grid[x][y].dx = dx;
        grid[x][y].dy = dy;
        grid[x][y].exists = 1;
    }
}

int main() {
    int n;
    scanf("%d", &n);

    // Read slides
    for (int i = 0; i < n; i++) {
        int x1, y1, x2, y2;
        scanf("%d %d %d %d", &x1, &y1, &x2, &y2);
        add_slide(x1, y1, x2, y2);
    }

    int x, y, energy;
    scanf("%d %d %d", &x, &y, &energy);

    while (1) {
        // Gravity pull
        while (!grid[x][y].exists && x > 0) {
            x--;
        }

        if (x == 0 || energy <= 0) {
            printf("%d %d\n", x, y);
            return 0;
        }

        // Slide movement
        while (grid[x][y].exists) {
            int dx = grid[x][y].dx;
            int dy = grid[x][y].dy;
            x += dx;
            y += dy;
            energy--;
            if (energy < 0) {
                printf("%d %d\n", x - dx, y - dy);
                return 0;
            }
        }

        // Unlock if stuck
        if (!grid[x][y].exists && x > 0) {
            int cost = x * y;
            if (energy >= cost) {
                energy -= cost;
                continue;
            } else {
                printf("%d %d\n", x, y);
                return 0;
            }
        }
    }

    return 0;
}
