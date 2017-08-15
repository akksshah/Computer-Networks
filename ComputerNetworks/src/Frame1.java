/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aakash
 */
package framingmethods;

import java.util.*;

public class Frame1{
	
	String frame;

	public Frame1(String frame)
	{
		this.frame = frame;
	}

	public static String senderCharCount(List<Frame1> frameList)
	{
		String sendString = "";
		for(Frame1 frameObj: frameList)
		{
			String frame = frameObj.frame;
			int length = frame.length();
			length = length +1;
			String appendBefore = Integer.toString(length);
			appendBefore = appendBefore + frame;
			sendString = sendString + appendBefore;
		}
		return sendString;
	}

	//Wasn't working for 9+ number of characters plus frame so made a few changes
	public static String receiverCharCount(String sentString)
	{
		List<Frame1> frameList = new ArrayList<Frame1>();
		while(sentString.length()!=0)
		{
			boolean isANumber = true;
			int number = 0;
			int addToFrame = 0;
			int i = 0;
			while(isANumber == true)
			{
				String firstLetter = sentString.substring(i, i+1);
				try {
					addToFrame = Integer.parseInt(firstLetter);
					number = number*10 + addToFrame;
					i++;
				}catch(NumberFormatException e)
				{
					isANumber = false;
				}
			}
			//11 takes one extra character than 9 would, thats why number+i-1. 
			//(i is the number of digits of the string)
			String partFrame = sentString.substring(0, number+i-1);
			
			Frame1 frame  = new Frame1(partFrame);
			frameList.add(frame);	
			sentString = sentString.substring(number+i-1, sentString.length());
		}
		String receivedString = "";
		for(Frame1 frameObj: frameList)
		{
			String frame = frameObj.frame;
			receivedString = receivedString + frame + "\n";
		}
		return receivedString;
	}

	public static String senderByteStuff(List<Frame1> frameList)
	{
		String sendString = "";
		for(Frame1 frameObj: frameList)
		{
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
		sentString = sentString.replaceAll("\\$\\$", "\\$");
		List<Frame1> frameList = new ArrayList<Frame1>();
		while(sentString.contains("##"))
		{
			String frameString = sentString.substring(0, sentString.indexOf("##"));
			frameString = frameString.replaceAll("\\$#", "#");
			Frame1 frame = new Frame1(frameString);
			frameList.add(frame);
			sentString = sentString.substring(sentString.indexOf("##")+2, sentString.length());
		}
		sentString = sentString.replaceAll("\\$#", "#");
		Frame1 frame = new Frame1(sentString);
		frameList.add(frame);
		String receivedString = "";
		for(Frame1 frameObj: frameList)
		{
			String frameString = frameObj.frame;
			receivedString = receivedString + frameString + "\n";
		}
		return receivedString;
	}
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of frames.");
		int numberOfFrames = sc.nextInt();
		List<Frame1> frameList = new ArrayList<Frame1>();
		System.out.println("Enter the frames one by one.");
		for(int i = 0; i<numberOfFrames; i++)
		{
			Frame1 frame  = new Frame1(sc.next());	
			frameList.add(frame);		
		}
		sc.close();

		System.out.println("\nCHARACTER COUNT");
		String sentString = Frame1.senderCharCount(frameList);
		System.out.println("Sent String : "+sentString);
		String receivedString = Frame1.receiverCharCount(sentString);
		System.out.println("Received String Frame1wsie : \n"+receivedString);
		System.out.println("BYTE STUFFING");
		sentString = Frame1.senderByteStuff(frameList);
		System.out.println("Sent String : "+sentString);
		receivedString = Frame1.receiverByteStuff(sentString);
		System.out.println("Received String Framewsie : \n"+receivedString);
	}
}