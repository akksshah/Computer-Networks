
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
public class CharCount{
    
        String frame;
    
        public CharCount(){
                frame = "";
        }
        
        public CharCount(String frame){
                this.frame=frame;
        }
        
        public static String senderCharCount(List<CharCount> frameList){
		String sendString = "";
		for(CharCount frameObj: frameList){
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
	public static String receiverCharCount(String sentString){
		List<CharCount> frameList = new ArrayList<CharCount>();
		while(sentString.length()!=0){
			boolean isANumber = true;
			int number = 0;
			int addToFrame = 0;
                        int count = 0;
			int i = 0;
			while(isANumber == true){
				String firstLetter = sentString.substring(i, i+1);
				try{
					addToFrame = Integer.parseInt(firstLetter);
					number = number*10 + addToFrame;
                                        count++;
					i++;
				}catch(NumberFormatException e){
					isANumber = false;
				}
			}
			//11 takes one extra character than 9 would, thats why number+i-1. 
			//(i is the number of digits of the string)
			String partFrame = sentString.substring(count, number+i-1);
			
			CharCount frame  = new CharCount(partFrame);
			frameList.add(frame);	
			sentString = sentString.substring(number+i-1, sentString.length());
		}
		String receivedString = "";
		for(CharCount frameObj: frameList){
			String frame = frameObj.frame;
			receivedString = receivedString + frame + "\n";
		}
		return receivedString;
	}
        
        public static void main(String args[])throws Exception{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the number of frames.");
		int numberOfFrames = Integer.parseInt(br.readLine());
		List<CharCount> frameList = new ArrayList<CharCount>();
		System.out.println("Enter the frames one by one.");
		for(int i = 0; i<numberOfFrames; i++){
			CharCount frame  = new CharCount(br.readLine());	
			frameList.add(frame);		
		}

		System.out.println("\nCHARACTER COUNT");
		String sentString = CharCount.senderCharCount(frameList);
		System.out.println("Sent String : "+sentString);
		String receivedString = CharCount.receiverCharCount(sentString);
		System.out.println("Received String Frame1wsie : \n"+receivedString);
	}
}

/*
Output:
Enter the number of frames.
3
Enter the frames one by one.
abc
ab2123456
asdfghjkl;kjghfdfsfghj

CHARACTER COUNT
Sent String : 4abc10ab212345623asdfghjkl;kjghfdfsfghj
Received String Frame1wsie : 
abc
ab2123456
asdfghjkl;kjghfdfsfghj
*/