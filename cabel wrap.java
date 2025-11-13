import java.util.*;

public class CableWrap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        sc.nextLine();
        char[][] grid = new char[N][M];
        boolean[][] visited = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            String[] row = sc.nextLine().split(" ");
            for (int j = 0; j < M; j++) {
                grid[i][j] = row[j].charAt(0);
            }
        }
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        int switches = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (!visited[i][j] && (grid[i][j] == 'C' || grid[i][j] == 'R')) {
                    Stack<int[]> stack = new Stack<>();
                    stack.push(new int[]{i, j});
                    while (!stack.isEmpty()) {
                        int[] pos = stack.pop();
                        int x = pos[0], y = pos[1];
                        if (x < 0 || y < 0 || x >= N || y >= M) continue;
                        if (visited[x][y]) continue;
                        if (grid[x][y] != 'C' && grid[x][y] != 'R') continue;
                        visited[x][y] = true;
                        if (grid[x][y] == 'R') switches++;
                        for (int d = 0; d < 4; d++) {
                            int nx = x + dx[d], ny = y + dy[d];
                            stack.push(new int[]{nx, ny});
                        }
                    }
                }
            }
        }
        System.out.println(switches);
    }
}
