const readline = require('readline');
const rl = readline.createInterface({ input: process.stdin, output: process.stdout });
let input = [];
rl.on('line', line => input.push(line));
rl.on('close', () => {
  let n = parseInt(input[0]);
  let shuffled = input.slice(2, 2 + n);
  let original = input.slice(3 + n, 3 + 2 * n);
  let dp = Array.from({ length: n + 1 }, () => Array(n + 1).fill(0));
  for (let i = 1; i <= n; i++) {
    for (let j = 1; j <= n; j++) {
      if (shuffled[i - 1] === original[j - 1]) dp[i][j] = dp[i - 1][j - 1] + 1;
      else dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
    }
  }
  console.log(n - dp[n][n]);
});
