import java.util.*;
public class OrderIt {
public static void main(String[]a){
Scanner s=new Scanner(System.in);
int n=Integer.parseInt(s.nextLine());
s.nextLine();
String[] sh=new String[n],or=new String[n];
for(int i=0;i<n;i++)sh[i]=s.nextLine();
s.nextLine();
Map<String,Integer> map=new HashMap<>();
for(int i=0;i<n;i++)map.put(s.nextLine(),i);
int[] pos=new int[n];
for(int i=0;i<n;i++)pos[i]=map.get(sh[i]);
List<Integer> lis=new ArrayList<>();
for(int x:pos){
int idx=Collections.binarySearch(lis,x);
if(idx<0)idx=-idx-1;
if(idx==lis.size())lis.add(x);
else lis.set(idx,x);
}
System.out.println(n-lis.size());
}
}
