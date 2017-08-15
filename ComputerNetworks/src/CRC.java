
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aakash
 */
public class CRC {
        
        String frame;
        static CRC obj;
        
        public CRC(){
                frame = "";
        }
        
        public CRC(String frame){
                this.frame = frame;
        }
        
        public static void main(String[] args)throws Exception{
                System.out.println("Computing cyclic redundancy");
                obj = new CRC();
                obj.sender();
                obj.reciever();
        }
        
        public void sender()throws IOException{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter data of the Frame : ");
                String frameData = br.readLine();
                System.out.print("Enter the generating function : ");
                String generatingFunction = br.readLine();
                String frameCopy = frameData;
                frameData += "000";
                String data;
                String rem = "";
                //to calculate remainder using X-OR
                for(; frameData.length()>3 ;){
                        data = frameData.substring(0,4);
                        rem = xor(data , generatingFunction);
                        frameData = rem + frameData.substring(4);       
                }
                System.out.println("Sender frame data = " + frameCopy + "" + rem);
        }
        public String xor(String data , String gx){
                char c[] = data.toCharArray();
                char g[] = gx.toCharArray();
                String rem = "";
                Character r[] = {'0','0','0','0'};
                for(int i = 0 ; i < 4 ; i++){
                    if(c[i]==g[i])
                        r[i]='0';
                    else
                        r[i]='1';
                    rem += r[i].toString();
                }
                if(rem.indexOf("1")==-1)
                    rem = "";
                else
                    rem = rem.substring(rem.indexOf("1"));
                if(rem.length()!=3){
                    int i = rem.length();
                    if(i == -1)
                        i++;
                    for( ; i < 3 ; i++)
                        rem = "0" + rem;
                }
                return rem;
        }
        
        public void reciever()throws IOException{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter recieved string is : ");
                String recievedData = br.readLine();
                System.out.print("Enter generating function : ");
                String generatingFunction = br.readLine();
                String data = "";
                String rem = "";
                for(; recievedData.length()>3 ;){
                    data = recievedData.substring(0,4);
                        rem = xor(data , generatingFunction);
                        recievedData = rem + recievedData.substring(4);       
                }
                if(rem.equals("000"))
                        System.out.println("Data with no error");
                else
                    System.out.println("Data with error");
        }
}
