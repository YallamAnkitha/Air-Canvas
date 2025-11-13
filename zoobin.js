const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

let input = [];
rl.on('line', line => input.push(line));
rl.on('close', () => {
  const E = parseInt(input[0]);
  const normalize = (a, b) => a < b ? `${a}-${b}` : `${b}-${a}`;

  const initial = new Set();
  const expected = new Set();

  for (let i = 1; i <= E; i++) {
    const [a, b] = input[i].split(' ').map(Number);
    initial.add(normalize(a, b));
  }

  for (let i = E + 1; i <= 2 * E; i++) {
    const [a, b] = input[i].split(' ').map(Number);
    expected.add(normalize(a, b));
  }

  // Find edges that differ
  const diff = new Set();
  for (let edge of initial) {
    if (!expected.has(edge)) diff.add(edge);
  }
  for (let edge of expected) {
    if (!initial.has(edge)) diff.add(edge);
  }

  // Build graph from diff edges
  const graph = {};
  for (let edge of diff) {
    const [u, v] = edge.split('-').map(Number);
    if (!graph[u]) graph[u] = [];
    if (!graph[v]) graph[v] = [];
    graph[u].push(v);
    graph[v].push(u);
  }

  // Count cycles
  const visited = new Set();
  let cycles = 0;

  for (let node in graph) {
    node = parseInt(node);
    if (visited.has(node)) continue;

    const queue = [node];
    visited.add(node);
    let nodes = new Set([node]);
    let edgeCount = 0;

    while (queue.length > 0) {
      const u = queue.pop();
      for (let v of graph[u]) {
        edgeCount++;
        if (!visited.has(v)) {
          visited.add(v);
          queue.push(v);
          nodes.add(v);
        }
      }
    }

    if (edgeCount / 2 === nodes.size) cycles++;
  }

  console.log(cycles);
});
