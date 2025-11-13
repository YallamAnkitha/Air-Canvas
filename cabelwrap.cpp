#include <iostream>
#include <vector>
#include <queue>
using namespace std;

bool isEdge(int r, int c, int N, int M) {
    return r == 0 || r == N-1 || c == 0 || c == M-1;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);

    int N, M;
    cin >> N >> M;
    vector<vector<char>> grid(N, vector<char>(M));
    cin.ignore();

    for(int i = 0; i < N; i++) {
        string line;
        getline(cin, line);
        int idx = 0;
        for(char ch : line) {
            if(ch != ' ') {
                grid[i][idx++] = ch;
            }
        }
    }

    vector<vector<bool>> visited(N, vector<bool>(M, false));
    queue<pair<int,int>> q;
    vector<pair<int,int>> cablePath;
    bool found = false;

    // Find cable start on the edge
    for(int i = 0; i < N && !found; i++) {
        for(int j = 0; j < M && !found; j++) {
            if(isEdge(i,j,N,M) && grid[i][j] == 'C') {
                q.push({i,j});
                visited[i][j] = true;
                found = true;
            }
        }
    }

    int dr[] = {-1,1,0,0};
    int dc[] = {0,0,-1,1};

    // BFS to find connected cable path
    while(!q.empty()) {
        auto u = q.front(); q.pop();
        cablePath.push_back(u);
        int r = u.first, c = u.second;

        for(int d = 0; d < 4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if(nr >= 0 && nr < N && nc >= 0 && nc < M) {
                if(!visited[nr][nc] && grid[nr][nc] == 'C') {
                    visited[nr][nc] = true;
                    q.push({nr, nc});
                }
            }
        }
    }

    // Count intersections where switches needed
    int switches = 0;
    for(auto &pos : cablePath) {
        int r = pos.first, c = pos.second;
        for(int d = 0; d < 4; d++) {
            int nr = r + dr[d], nc = c + dc[d];
            if(nr >= 0 && nr < N && nc >= 0 && nc < M && grid[nr][nc] == 'R') {
                switches++;
                break;
            }
        }
    }

    cout << switches << "\n";
    return 0;
}
