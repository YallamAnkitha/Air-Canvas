#include <iostream>
#include <map>
#include <vector>
using namespace std;

map<pair<int, int>, pair<int, int>> slideMap;

void addSlide(int x1, int y1, int x2, int y2) {
    int dx = (x2 > x1) ? 1 : -1;
    int dy = (y2 > y1) ? 1 : -1;
    int len = abs(x2 - x1);
    for (int i = 0; i <= len; ++i) {
        int x = x1 + i * dx;
        int y = y1 + i * dy;
        slideMap[{x, y}] = {x + dx, y + dy};
    }
}

pair<int, int> simulate(int x, int y, int energy) {
    while (true) {
        // Gravity pull
        while (slideMap.find({x, y}) == slideMap.end() && x > 0) {
            x--;
        }
        if (x == 0 || energy <= 0) return {x, y};

        // Slide movement
        while (slideMap.find({x, y}) != slideMap.end()) {
            pair<int, int> next = slideMap[{x, y}];
            energy--;
            if (energy < 0) return {x, y};
            x = next.first;
            y = next.second;
        }

        // Unlock if stuck
        if (slideMap.find({x, y}) == slideMap.end() && x > 0) {
            int cost = x * y;
            if (energy >= cost) {
                energy -= cost;
                continue;
            } else {
                return {x, y};
            }
        }
    }
}

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; ++i) {
        int x1, y1, x2, y2;
        cin >> x1 >> y1 >> x2 >> y2;
        addSlide(x1, y1, x2, y2);
    }

    int startX, startY, energy;
    cin >> startX >> startY >> energy;

    pair<int, int> result = simulate(startX, startY, energy);
    cout << result.first << " " << result.second << endl;
    return 0;
}
