import java.io.*;
import java.util.*;



class Leaky extends Thread
{
public static void main(String args[])throws Exception
{
Queue q=new Queue();
Scanner sc =new Scanner(System.in);
System.out.println("\nEnter the packets to be sent:");
int size=sc.nextInt();
q.insert(size);
q.delete();
}
}
