import java.util.*;

public class GravityGlide {
    static class Point {
        int x, y;
        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    static Map<String, Point> slideMap = new HashMap<>();

    static void addSlide(int x1, int y1, int x2, int y2) {
        int dx = (x2 > x1) ? 1 : -1;
        int dy = (y2 > y1) ? 1 : -1;
        int len = Math.abs(x2 - x1);
        for (int i = 0; i <= len; i++) {
            int x = x1 + i * dx;
            int y = y1 + i * dy;
            slideMap.put(x + "," + y, new Point(x + dx, y + dy));
        }
    }

    static Point simulate(int x, int y, int energy) {
        while (true) {
            // Gravity pull
            while (!slideMap.containsKey(x + "," + y) && x > 0) {
                x--;
            }
            if (x == 0 || energy <= 0) return new Point(x, y);

            // Slide movement
            while (slideMap.containsKey(x + "," + y)) {
                Point next = slideMap.get(x + "," + y);
                energy--;
                if (energy < 0) return new Point(x, y);
                x = next.x;
                y = next.y;
            }

            // Unlock if stuck
            if (!slideMap.containsKey(x + "," + y) && x > 0) {
                int cost = x * y;
                if (energy >= cost) {
                    energy -= cost;
                    continue;
                } else {
                    return new Point(x, y);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int x1 = sc.nextInt(), y1 = sc.nextInt(), x2 = sc.nextInt(), y2 = sc.nextInt();
            addSlide(x1, y1, x2, y2);
        }
        int startX = sc.nextInt(), startY = sc.nextInt(), energy = sc.nextInt();
        Point result = simulate(startX, startY, energy);
        System.out.println(result.x + " " + result.y);
        sc.close();
    }
}
