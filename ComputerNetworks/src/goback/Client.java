package goback;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client 
{ 
    public static void main(String args[]) throws Exception 
    { 
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
        System.out.print("Enter the value of m : "); 
        int m=Integer.parseInt(br.readLine()); 
        int x=(int)((Math.pow(2,m))-1); 
        System.out.print("Enter no. of frames to be sent:"); 
        int count=Integer.parseInt(br.readLine()); 
        int data[]=new int[count]; 
        int h=0; 
        
        for(int i=0;i<count;i++) 
        { 
            System.out.print("Enter data for frame no " +h+ " => "); 
            data[i]=Integer.parseInt(br.readLine()); 
            h=(h+1)%x;  
        } 
        
        Socket client=new Socket("localhost",6264); 
        ObjectInputStream ois=new ObjectInputStream(client.getInputStream()); 
        ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream()); 
        System.out.println("Connected with server."); 
        boolean flag=false; 
        GoBackNListener listener=new GoBackNListener(ois,x); 
        listener=new GoBackNListener(ois,x); 
        listener.t.start(); 
        
        int strt=0; 
        h=0; 
        oos.writeObject(x); 
        System.out.print("hello");
        do 
        { 
            int c=h; 
            for(int i=h;i<count;i++) 
            { 
                System.out.print("|"+c+"|"); 
                c=(c+1)%x; 
            } 
            System.out.println(); 
            System.out.println(); 
            h=strt; 
            for(int i=strt;i<x;i++) 
            { 
                System.out.println("Sending frame:"+h); 
                h=(h+1)%x; 
                System.out.println(); 
                oos.writeObject(i); 
                oos.writeObject(data[i]); 
                System.out.println("\t\t\t"+data[i]);
                Thread.sleep(i*100); 
            } 
            listener.t.join(3500); 
            if(listener.reply!=x-1) 
            { 
                System.out.println("No reply from server in 3.5 seconds. Resending data from frame no " + (listener.reply+1)); 
                System.out.println(); 
                strt=listener.reply+1; 
                flag=false; 
            } 
            else 
            { 
                System.out.println("All elements sent successfully. Exiting"); 
                flag=true; 
            } 
        }while(!flag); 
        oos.writeObject(-1); 
    } 
}