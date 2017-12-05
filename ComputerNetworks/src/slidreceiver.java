
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aakash
 */
public class slidreceiver {
    public static void main(String[] args)throws Exception{
        Socket s = new Socket("localhost",3335);
        
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        
        String rbuff[] = new String[8];
        
        PrintStream p = new PrintStream(s.getOutputStream());
        int rptr = 0 , nf,rws = 8,i=0;
        String ch;
        
        do{
            nf = Integer.parseInt(din.readUTF());
            System.out.println(nf);
            if(nf<=rws-1){
                System.out.println("enter message to sent");
                for(i=1;i<=nf;i++){
                    rptr = ++rptr % 8 ;
                    System.out.println(rptr);
                    rbuff[rptr] = din.readUTF();
                    System.out.println("Rec frame "+rptr+" is "+rbuff[rptr]);
                }
                rws -=nf;
                //ano=Integer.parseInt(din.readUTF());
                System.out.println("Ack sent");
                dout.writeUTF(""+(rptr));
                rws +=nf;
            }
            else
                break;
            ch = din.readUTF();
        }while(ch.equals("yes"));
        
    }
}
