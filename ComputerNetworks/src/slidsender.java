
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
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
public class slidsender {
    public static void main(String[] args)throws Exception{
        ServerSocket ser = new ServerSocket(3335);
        Socket s = ser.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        
        String sbuff[] = new String[8];
        PrintStream p;
        int sptr = 0 ,sws = 8, nf,ano = -999 ,i;
        String ch;
        do{
            p = new PrintStream(s.getOutputStream());
            System.out.println("Enter no. of frames");
            nf = Integer.parseInt(br.readLine());
            dout.writeUTF(""+nf);
            dout.flush();
            if(nf<=sws-1){
                System.out.println("enter message to sent");
                for(i=1;i<=nf;i++){
                    sbuff[sptr] = br.readLine();
                    dout.writeUTF(sbuff[sptr]);
                    sptr = ++sptr % 8 ;
                }
                sws -=nf;
                ano=Integer.parseInt(din.readUTF());
                System.out.println("Ack rec for "+ano+" frames");
                sws +=nf;
            }
            else{
                System.out.println("No of frames exceed sliding win");
                break;
            }
            System.out.println("more frames?");
            ch = br.readLine();
            dout.writeUTF(ch);
        }while(ch.equals("yes"));
        s.close();
        ser.close();
    }
}
