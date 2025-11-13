from collections import deque
n,m=map(int,input().split())
g=[list(input().replace(' ','').strip())for _ in range(n)]
v=[[0]*m for _ in range(n)]
d=[(0,1),(0,-1),(1,0),(-1,0)]
def bfs(x,y):
 q=deque([(x,y)]);v[x][y]=1;c=0
 while q:
  i,j=q.popleft()
  if g[i][j]=='R':c+=1
  for dx,dy in d:
   ni,nj=i+dx,j+dy
   if 0<=ni<n and 0<=nj<m and not v[ni][nj] and g[ni][nj]in'CR':
    v[ni][nj]=1;q.append((ni,nj))
 return c
s=0
for i in range(n):
 for j in range(m):
  if g[i][j]=='C'and not v[i][j]:s+=bfs(i,j)
print(s)
