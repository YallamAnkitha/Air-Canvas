import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(), M = sc.nextInt();
        sc.nextLine();
        char[][] grid = new char[N][M];
        for(int i=0; i<N; i++) {
            String row = sc.nextLine().trim();
            String[] cells = row.split("\\s+");
            if(cells.length == M) {
                for(int j=0;j<M;j++) grid[i][j]=cells[j].charAt(0);
            } else {
                row = row.replace(" ", "");
                for(int j=0;j<M;j++) grid[i][j]=row.charAt(j);
            }
        }
        boolean[][] vis = new boolean[N][M];
        int[] dx = {-1,1,0,0}, dy = {0,0,-1,1};
        Queue<int[]> q = new LinkedList<>();
        ArrayList<int[]> path = new ArrayList<>();
        boolean found=false;
        for(int i=0;i<N && !found;i++) {
            for(int j=0;j<M && !found;j++) {
                if(isEdge(i,j,N,M) && grid[i][j]=='C') {
                    q.add(new int[]{i,j});
                    vis[i][j]=true;
                    found=true;
                }
            }
        }
        while(!q.isEmpty()) {
            int[] u = q.poll();
            path.add(u);
            for(int k=0;k<4;k++) {
                int ni=u[0]+dx[k], nj=u[1]+dy[k];
                if(ni>=0&&ni<N&&nj>=0&&nj<M && !vis[ni][nj] && grid[ni][nj]=='C') {
                    q.add(new int[]{ni,nj});
                    vis[ni][nj]=true;
                }
            }
        }
        int ans=0;
        for(int[] c:path)
            for(int k=0;k<4;k++) {
                int ni=c[0]+dx[k], nj=c[1]+dy[k];
                if(ni>=0&&ni<N&&nj>=0&&nj<M && grid[ni][nj]=='R') {
                    ans++;
                    break;
                }
            }
        System.out.println(ans);
    }
    static boolean isEdge(int i, int j, int N, int M) {
        return i==0||i==N-1||j==0||j==M-1;
    }
}
