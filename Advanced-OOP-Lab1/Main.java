public class Main{
public static void main(String[] args){
Sedan s=new Sedan();
Vehicle v=s;
v.accelerate();
s.accelerate(100);
}
}