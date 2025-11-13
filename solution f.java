import java.util.*;
public class Main{
private static String toBinaryPattern(String[] block){StringBuilder sb=new StringBuilder();for(String row:block){for(char c:row.toCharArray())sb.append(c==' '?'0':'1');}return sb.toString();}
private static List<String> parsePatterns(String[] lines){List<String> patterns=new ArrayList<>();int width=lines[0].length();for(int i=0;i<width;i+=4){String a=lines[0].substring(i,Math.min(i+3,width));String b=lines[1].substring(i,Math.min(i+3,width));String c=lines[2].substring(i,Math.min(i+3,width));patterns.add(toBinaryPattern(new String[]{a,b,c}));}return patterns;}
private static String padLeft(String s,int n){StringBuilder sb=new StringBuilder();for(int i=0;i<n-s.length();i++)sb.append('0');sb.append(s);return sb.toString();}
private static String bitAnd(String a,String b){int n=Math.max(a.length(),b.length());a=padLeft(a,n);b=padLeft(b,n);StringBuilder sb=new StringBuilder();for(int i=0;i<n;i++)sb.append(a.charAt(i)=='1' && b.charAt(i)=='1'?'1':'0');return sb.toString();}
private static String bitOr(String a,String b){int n=Math.max(a.length(),b.length());a=padLeft(a,n);b=padLeft(b,n);StringBuilder sb=new StringBuilder();for(int i=0;i<n;i++)sb.append(a.charAt(i)=='1' || b.charAt(i)=='1'?'1':'0');return sb.toString();}
private static String bitNot(String a){StringBuilder sb=new StringBuilder();for(int i=0;i<a.length();i++)sb.append(a.charAt(i)=='1'?'0':'1');return sb.toString();}
public static void main(String[] args){
Scanner sc=new Scanner(System.in);
String[] in=new String[9];for(int i=0;i<9;i++)in[i]=sc.nextLine();
List<String> digitPatterns=parsePatterns(Arrays.copyOfRange(in,0,3));
Map<String,String> pat2sym=new HashMap<>();for(int i=0;i<digitPatterns.size();i++)pat2sym.put(digitPatterns.get(i),String.valueOf(i));
List<String> opPatterns=parsePatterns(Arrays.copyOfRange(in,3,6));String[] ops=new String[]{"||","&&","!","(",")"};for(int i=0;i<ops.length;i++)pat2sym.put(opPatterns.get(i),ops[i]);
List<String> exprBlocks=parsePatterns(Arrays.copyOfRange(in,6,9));
List<String> tokens=new ArrayList<>();List<String> values=new ArrayList<>();
StringBuilder curNum=new StringBuilder();
for(String block:exprBlocks){
String s=pat2sym.get(block);
if(s==null){System.out.println("0");return;}
if(s.matches("\\d")){curNum.append(block);}else{if(curNum.length()>0){tokens.add("NUM");values.add(curNum.toString());curNum.setLength(0);}tokens.add(s);values.add(s);}
}
if(curNum.length()>0){tokens.add("NUM");values.add(curNum.toString());}
int idx=0;
class Parser{
String parseExpression(){return parseAnd();}
String parseAnd(){String left=parseOr();while(idx<tokens.size() && tokens.get(idx).equals("&&")){idx++;String right=parseOr();left=bitAnd(left,right);}return left;}
String parseOr(){String left=parseUnary();while(idx<tokens.size() && tokens.get(idx).equals("||")){idx++;String right=parseUnary();left=bitOr(left,right);}return left;}
String parseUnary(){if(idx<tokens.size() && tokens.get(idx).equals("!")){idx++;String operand=parseUnary();return bitNot(operand);}return parsePrimary();}
String parsePrimary(){if(idx<tokens.size() && tokens.get(idx).equals("(")){idx++;String val=parseExpression();if(idx<tokens.size() && tokens.get(idx).equals(")"))idx++;return val;}if(idx<tokens.size() && tokens.get(idx).equals("NUM")){String b=values.get(idx);idx++;return b;}return "";}
}
Parser p=new Parser();
String resultBinary=p.parseExpression();
if(resultBinary==null || resultBinary.length()==0){System.out.println("0");return;}
int len=resultBinary.length();if(len%9!=0)resultBinary=padLeft(resultBinary,((len+8)/9)*9);
StringBuilder number=new StringBuilder();
for(int i=0;i<resultBinary.length();i+=9){
String chunk=resultBinary.substring(i,i+9);
String digit=null;
for(int d=0;d<digitPatterns.size();d++)if(digitPatterns.get(d).equals(chunk)){digit=String.valueOf(d);break;}
if(digit==null){System.out.println("0");return;}
number.append(digit);
}
try{System.out.println(Integer.parseInt(number.toString()));}catch(Exception e){System.out.println("0");}
}
}