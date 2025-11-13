using System;
class OrderIt {
static void Main() {
int n = int.Parse(Console.ReadLine());
Console.ReadLine();
string[] shuffled = new string[n], original = new string[n];
for (int i = 0; i < n; i++) shuffled[i] = Console.ReadLine();
Console.ReadLine();
for (int i = 0; i < n; i++) original[i] = Console.ReadLine();
int[,] dp = new int[n + 1, n + 1];
for (int i = 1; i <= n; i++)
for (int j = 1; j <= n; j++)
dp[i,j] = shuffled[i-1]==original[j-1] ? dp[i-1,j-1]+1 : Math.Max(dp[i-1,j],dp[i,j-1]);
Console.WriteLine(n - dp[n,n]);
}
}
