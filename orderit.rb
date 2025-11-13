n = gets.to_i
gets
shuffled = Array.new(n) { gets.chomp }
gets
original = Array.new(n) { gets.chomp }
dp = Array.new(n + 1) { Array.new(n + 1, 0) }
(1..n).each do |i|
  (1..n).each do |j|
    if shuffled[i - 1] == original[j - 1]
      dp[i][j] = dp[i - 1][j - 1] + 1
    else
      dp[i][j] = [dp[i - 1][j], dp[i][j - 1]].max
    end
  end
end
puts n - dp[n][n]
