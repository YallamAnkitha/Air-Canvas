#include<bits/stdc++.h>
using namespace std;
int main(){
int n;cin>>n;
string temp;cin>>temp;
vector<string> sh(n),or(n);
cin.ignore();
for(int i=0;i<n;i++)getline(cin,sh[i]);
cin>>temp;
cin.ignore();
map<string,int> pos;
for(int i=0;i<n;i++)getline(cin,or[i]),pos[or[i]]=i;
vector<int> seq(n);
for(int i=0;i<n;i++)seq[i]=pos[sh[i]];
vector<int> lis;
for(int x:seq){
auto it=lower_bound(lis.begin(),lis.end(),x);
if(it==lis.end())lis.push_back(x);
else *it=x;
}
cout<<n-lis.size()<<endl;
}
