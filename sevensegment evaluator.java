import java.util.*;
public class SegmentScript{
static Map<String,String> segToSym=new HashMap<>();
static Map<String,String> digitToBin=new HashMap<>();
public static void main(String[]a){
Scanner s=new Scanner(System.in);
String[] D=new String[3],O=new String[3],E=new String[3];
for(int i=0;i<3;i++)D[i]=s.nextLine();
for(int i=0;i<3;i++)O[i]=s.nextLine();
for(int i=0;i<3;i++)E[i]=s.nextLine();
for(int i=0;i<10;i++){
String k=key(D,i),v=""+i;
segToSym.put(k,v);
digitToBin.put(v,k);
}
String[] ops={"||","&&","!","(",")"};
for(int i=0;i<5;i++)segToSym.put(key(O,i),ops[i]);
List<String> expr=new ArrayList<>();
int N=E[0].length()/3;
for(int i=0;i<N;i++)expr.add(segToSym.get(key(E,i)));
List<String> tokens=new ArrayList<>();
for(int i=0;i<expr.size();){
if(Character.isDigit(expr.get(i).charAt(0))){
String x="";
while(i<expr.size()&&Character.isDigit(expr.get(i).charAt(0)))x+=expr.get(i++);
tokens.add(toBinary(x));
}else tokens.add(expr.get(i++));
}
System.out.println(Integer.parseInt(eval(tokens),2));
}
static String key(String[] L,int i){
String r="";
for(int j=0;j<3;j++){
String z=L[j].substring(i*3,i*3+3);
for(char c:z.toCharArray())r+=(c==' '?"0":"1");
}
return r;
}
static String toBinary(String x){
String r="";
for(char c:x.toCharArray())r+=digitToBin.get(""+c);
return r;
}
static String eval(List<String> T){
Stack<String> val=new Stack<>(),op=new Stack<>();
for(String t:T){
if(t.equals("("))op.push(t);
else if(t.equals(")")){
while(!op.peek().equals("("))val.push(apply(op.pop(),val));
op.pop();
}else if(t.equals("!")||t.equals("||")||t.equals("&&")){
while(!op.isEmpty()&&prec(op.peek())>=prec(t))val.push(apply(op.pop(),val));
op.push(t);
}else val.push(t);
}
while(!op.isEmpty())val.push(apply(op.pop(),val));
return val.pop();
}
static String apply(String o,Stack<String> v){
if(o.equals("!"))return flip(v.pop());
String b=v.pop(),a=v.pop();
return o.equals("&&")?and(a,b):or(a,b);
}
static int prec(String o){
return o.equals("!")?3:o.equals("||")?2:o.equals("&&")?1:0;
}
static String and(String a,String b){
StringBuilder r=new StringBuilder();
for(int i=0;i<Math.min(a.length(),b.length());i++)
r.append(a.charAt(i)=='1'&&b.charAt(i)=='1'?'1':'0');
return r.toString();
}
static String or(String a,String b){
StringBuilder r=new StringBuilder();
for(int i=0;i<Math.min(a.length(),b.length());i++)
r.append(a.charAt(i)=='1'||b.charAt(i)=='1'?'1':'0');
return r.toString();
}
static String flip(String a){
StringBuilder r=new StringBuilder();
for(char c:a.toCharArray())r.append(c=='1'?'0':'1');
return r.toString();
}
}
