#include <stdio.h>
#include <string.h>
#define MAX 15
#define MAXK 101
char cube[6][MAX][MAX],temp[6][MAX][MAX],instructions[MAXK][50];
int N,K;
void copyCube(){
for(int f=0;f<6;f++)
for(int i=0;i<N;i++)
for(int j=0;j<N;j++)
temp[f][i][j]=cube[f][i][j];
}
int isSolved(){
for(int f=0;f<6;f++){
char c=temp[f][0][0],same=1;
for(int i=0;i<N&&same;i++)
for(int j=0;j<N&&same;j++)
if(temp[f][i][j]!=c)same=0;
if(same)return 1;
}
return 0;
}
int main(){
scanf("%d%d",&N,&K);
for(int f=0;f<6;f++)
for(int i=0;i<N;i++)
for(int j=0;j<N;j++)
scanf(" %c",&cube[f][i][j]);
getchar();
for(int i=0;i<K;i++)
fgets(instructions[i],sizeof(instructions[i]),stdin);
for(int skip=0;skip<K;skip++){
copyCube();
for(int i=0;i<K;i++){
if(i==skip)continue;
}
if(isSolved()){
printf("%s",instructions[skip]);
return 0;
}
}
printf("Not Possible\n");
return 0;
}
