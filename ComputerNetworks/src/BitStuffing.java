
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aakash
 */

public class BitStuffing{
	
	String frame;

	public BitStuffing(String frame)
	{
		this.frame = frame;
	}

	public static String senderBitStuff(List<BitStuffing> frameList)
	{
		String sendString = "";
		for(BitStuffing frameObj: frameList)
		{
			String frame = frameObj.frame;
			String append = "01111110";
			frame = frame.replaceAll("11111", "111110");
			frame = append + frame + append;
			sendString = sendString + frame;
		}
		return sendString;
	}

	public static String receiverBitStuff(String sentString)
	{
		sentString = sentString.substring(8, sentString.length());
		List<BitStuffing> frameList = new ArrayList<BitStuffing>();
		while(sentString.contains("01111110"))
		{
			String frameString = sentString.substring(0, sentString.indexOf("01111110"));
			frameString = frameString.replaceAll("111110", "11111");
			BitStuffing frame = new BitStuffing(frameString);
			frameList.add(frame);
			sentString = sentString.substring(sentString.indexOf("01111110")+8, sentString.length());
		}
		String receivedString = "";
		for(BitStuffing frameObj: frameList)
		{
			String frameString = frameObj.frame;
			receivedString = receivedString + frameString + "\n";
		}
		return receivedString;
	}
	
	public static void main(String args[])throws Exception
	{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of frames.");
		int numberOfFrames = Integer.parseInt(br.readLine());
		List<BitStuffing> frameList = new ArrayList<BitStuffing>();
		System.out.println("Enter the frames one by one.");
		for(int i = 0; i<numberOfFrames; i++)
		{
			BitStuffing frame  = new BitStuffing(br.readLine());	
			frameList.add(frame);		
		}

		System.out.println("\nBIT STUFFING");
		String sentString = BitStuffing.senderBitStuff(frameList);
		System.out.println("Sent String : "+sentString);
		String receivedString = BitStuffing.receiverBitStuff(sentString);
		System.out.println("Received String Framewsie : \n"+receivedString);
	}
}

/*
Output:
Enter the number of frames.
3
Enter the frames one by one.
01111110
01111111
101101010111111011

BIT STUFFING
Sent String : 0111111001111101001111110011111100111110110111111001111110101101010111110101101111110
Received String Framewsie : 
01111110

01111111

101101010111111011
*/