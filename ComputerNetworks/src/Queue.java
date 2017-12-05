
import java.util.Scanner;

class Queue
{
    int q[],f=0,r=0,size;
    
    void insert(int n)
    {
        Scanner sc=new Scanner(System.in);
        q=new int[10];
        for(int i=0;i<n;i++)
        {
            System.out.print("\nEnter " + i + " element: ");
            int ele=sc.nextInt();
            if(r+1>10)
            {
                System.out.println("\nQueue is full \nLost Packet: "+ele);
                //break;
            }
            else
            {
                r++;
                q[i]=ele;
            }
        }
    }

    void delete()
    {
        Scanner sc=new Scanner(System.in);
        Thread t=new Thread();
        if(r==0)
                System.out.print("\nQueue empty");
        else
        {
                for(int i=f;i<r;i++)
                {
                    try{
                    t.sleep(1000);
                    }
                    catch(Exception e)
                    {}
                    System.out.print("\nLeaked Packet: "+q[i]);
                    f++;
                }
         }
         System.out.println();
    }
}