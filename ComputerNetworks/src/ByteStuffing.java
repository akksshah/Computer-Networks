
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aakash
 */

public class ByteStuffing {
        
        String frame;
        
        public ByteStuffing(){
                frame = "";
        }
        
        public ByteStuffing(String frame){
                this.frame = frame;
        }
        
        public static String senderByteStuff(List<ByteStuffing> frameList){
		String sendString = "";
		for(ByteStuffing frameObj: frameList){
			String frame = frameObj.frame;
			String append = "#";
			frame = frame.replaceAll("\\$", "\\$\\$");
			frame = frame.replaceAll("#", "\\$#");
			frame = append + frame + append;
			sendString = sendString + frame;
		}
		return sendString;
	}
        
        public static String receiverByteStuff(String sentString)
	{
		sentString = sentString.substring(1, sentString.length()-1);
                //System.out.print(sentString);
		sentString = sentString.replaceAll("\\$\\$", "\\$");
		List<ByteStuffing> frameList = new ArrayList<ByteStuffing>();
		while(sentString.contains("##"))
		{
			String frameString = sentString.substring(0, sentString.indexOf("##"));
                        System.out.println(frameString);
			frameString = frameString.replaceAll("\\$#", "#");
			ByteStuffing frame = new ByteStuffing(frameString);
			frameList.add(frame);
			sentString = sentString.substring(sentString.indexOf("##")+2, sentString.length());
		}
		sentString = sentString.replaceAll("\\$#", "#");
		ByteStuffing frame = new ByteStuffing(sentString);
		frameList.add(frame);
		String receivedString = "";
		for(ByteStuffing frameObj: frameList)
		{
			String frameString = frameObj.frame;
			receivedString = receivedString + frameString + "\n";
		}
		return receivedString;
	}
        
        public static void main(String args[])throws Exception{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of frames.");
		int numberOfFrames = Integer.parseInt(br.readLine());
		List<ByteStuffing> frameList = new ArrayList<ByteStuffing>();
		System.out.println("Enter the frames one by one.");
		for(int i = 0; i<numberOfFrames; i++){
			ByteStuffing frame  = new ByteStuffing(br.readLine());	
			frameList.add(frame);		
		}

		System.out.println("BYTE STUFFING");
		String sentString = ByteStuffing.senderByteStuff(frameList);
		System.out.println("Sent String : "+sentString);
		String receivedString = ByteStuffing.receiverByteStuff(sentString);
		System.out.println("Received String Framewsie : \n"+receivedString);
	}
}

/*
Output:
Enter the number of frames.
3
Enter the frames one by one.
asdfghjk
###$$$$
1234
BYTE STUFFING
Sent String : #asdfghjk##$#$#$#$$$$$$$$##1234#
Received String Framewsie : 
asdfghjk
###$$$$
1234
*/