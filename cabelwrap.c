#include <stdio.h>
#include <stdlib.h>

#define MAX 20

typedef struct {
    int r, c;
} Point;

typedef struct {
    Point points[MAX*MAX];
    int front, rear;
} Queue;

void enqueue(Queue *q, Point p) {
    q->points[q->rear++] = p;
}

Point dequeue(Queue *q) {
    return q->points[q->front++];
}

int is_empty(Queue *q) {
    return q->front == q->rear;
}

int is_edge(int r, int c, int N, int M) {
    return r == 0 || r == N-1 || c == 0 || c == M-1;
}

int main() {
    int N, M;
    scanf("%d %d", &N, &M);
    char grid[MAX][MAX];
    getchar(); // consume newline after M

    for(int i = 0; i < N; i++) {
        for(int j=0;j<M;j++) {
            char ch;
            // Read characters ignoring spaces
            do {
                ch = getchar();
            } while(ch==' ' || ch=='\n' || ch=='\r');
            grid[i][j] = ch;
        }
    }

    int visited[MAX][MAX] = {0};
    Queue q = {.front=0, .rear=0};
    Point start = {-1,-1};

    // Find start cable cell on edge
    for(int i=0;i<N && start.r == -1;i++) {
        for(int j=0;j<M && start.r == -1;j++) {
            if(is_edge(i,j,N,M) && grid[i][j] == 'C') {
                start.r = i;
                start.c = j;
            }
        }
    }
    enqueue(&q, start);
    visited[start.r][start.c] = 1;

    int dr[4] = {-1,1,0,0};
    int dc[4] = {0,0,-1,1};
    Point path[MAX*MAX];
    int path_len = 0;

    // BFS to find cable path
    while(!is_empty(&q)) {
        Point u = dequeue(&q);
        path[path_len++] = u;
        for(int d=0;d<4;d++) {
            int nr = u.r + dr[d], nc = u.c + dc[d];
            if(nr>=0 && nr<N && nc>=0 && nc<M) {
                if(!visited[nr][nc] && grid[nr][nc]=='C') {
                    visited[nr][nc] = 1;
                    enqueue(&q, (Point){nr,nc});
                }
            }
        }
    }

    // Count intersections with rods that require switching
    int switches = 0;
    for(int i=0; i<path_len; i++) {
        int r = path[i].r;
        int c = path[i].c;
        for(int d=0; d<4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if(nr>=0 && nr<N && nc>=0 && nc<M) {
                if(grid[nr][nc] == 'R') {
                    switches++;
                    break;
                }
            }
        }
    }

    printf("%d\n", switches);
    return 0;
}
