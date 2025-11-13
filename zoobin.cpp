#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <queue>
using namespace std;

typedef pair<int, int> Edge;

Edge normalize(int a, int b) {
    return a < b ? make_pair(a, b) : make_pair(b, a);
}

void buildGraph(const set<Edge>& edges, map<int, vector<int>>& graph) {
    for (set<Edge>::const_iterator it = edges.begin(); it != edges.end(); ++it) {
        int u = it->first;
        int v = it->second;
        graph[u].push_back(v);
        graph[v].push_back(u);
    }
}

int countCycles(map<int, vector<int>>& graph) {
    set<int> visited;
    int cycles = 0;

    for (map<int, vector<int>>::iterator it = graph.begin(); it != graph.end(); ++it) {
        int node = it->first;
        if (visited.count(node)) continue;

        queue<int> q;
        q.push(node);
        visited.insert(node);

        set<int> component;
        int edgeCount = 0;

        while (!q.empty()) {
            int u = q.front(); q.pop();
            component.insert(u);
            for (size_t i = 0; i < graph[u].size(); ++i) {
                int v = graph[u][i];
                edgeCount++;
                if (!visited.count(v)) {
                    visited.insert(v);
                    q.push(v);
                }
            }
        }

        if (edgeCount / 2 == (int)component.size()) cycles++;
    }

    return cycles;
}

int main() {
    int E;
    cin >> E;

    set<Edge> initial, expected;
    for (int i = 0; i < E; ++i) {
        int a, b;
        cin >> a >> b;
        initial.insert(normalize(a, b));
    }
    for (int i = 0; i < E; ++i) {
        int a, b;
        cin >> a >> b;
        expected.insert(normalize(a, b));
    }

    set<Edge> diff;
    for (set<Edge>::iterator it = initial.begin(); it != initial.end(); ++it)
        if (!expected.count(*it)) diff.insert(*it);
    for (set<Edge>::iterator it = expected.begin(); it != expected.end(); ++it)
        if (!initial.count(*it)) diff.insert(*it);

    map<int, vector<int>> graph;
    buildGraph(diff, graph);

    cout << countCycles(graph) << endl;
    return 0;
}
