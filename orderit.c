#include<stdio.h>
#include<string.h>
#define MAX 15
char shuffled[MAX][100], original[MAX][100];
int dp[MAX][MAX];
int max(int a,int b){return a>b?a:b;}
int main(){
int n;scanf("%d",&n);
char temp[100];
scanf("%s",temp);
for(int i=0;i<n;i++)scanf(" %[^\n]",shuffled[i]);
scanf("%s",temp);
for(int i=0;i<n;i++)scanf(" %[^\n]",original[i]);
for(int i=1;i<=n;i++)
for(int j=1;j<=n;j++)
dp[i][j]=strcmp(shuffled[i-1],original[j-1])==0?dp[i-1][j-1]+1:max(dp[i-1][j],dp[i][j-1]);
printf("%d\n",n-dp[n][n]);
return 0;
}
