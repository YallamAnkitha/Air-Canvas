from collections import defaultdict, deque

def normalize(a, b):
    return (min(a, b), max(a, b))

def read_edges(e, lines):
    edges = set()
    for line in lines:
        a, b = map(int, line.split())
        edges.add(normalize(a, b))
    return edges

def build_graph(edge_diff):
    graph = defaultdict(list)
    for u, v in edge_diff:
        graph[u].append(v)
        graph[v].append(u)
    return graph

def count_cycles(graph):
    visited = set()
    cycles = 0

    for node in graph:
        if node in visited:
            continue
        queue = deque([node])
        visited.add(node)
        nodes = set([node])
        edge_count = 0

        while queue:
            u = queue.popleft()
            for v in graph[u]:
                edge_count += 1
                if v not in visited:
                    visited.add(v)
                    queue.append(v)
                    nodes.add(v)

        if edge_count // 2 == len(nodes):  # simple cycle
            cycles += 1
    return cycles

# Input
E = int(input())
initial_lines = [input() for _ in range(E)]
expected_lines = [input() for _ in range(E)]

initial = read_edges(E, initial_lines)
expected = read_edges(E, expected_lines)

# Symmetric difference: edges that changed
diff = initial.symmetric_difference(expected)

# Build graph from changed edges
graph = build_graph(diff)

# Output: number of rotation cycles
print(count_cycles(graph))
