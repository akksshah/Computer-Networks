
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
class Sender{
	Socket socket = null;
	ObjectInputStream inp = null;
	ObjectOutputStream out = null;

	Sender(String address, int port){
		try{
			socket = new Socket(address, port);
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			inp = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e){}
	}

	public void run(int k, int n){
		// Size of window
                System.out.print("hello");
		int mod = (int) Math.pow(2, k-1);
		// Size of header
		int cmod = (int) Math.pow(2, k);

		int start = 0, end = mod - 1, pointer = 0;
		// Packets
		int arr [] = new int[n];
		// Keep track of acknowledged packets
		boolean done[] = new boolean[n];

		for(int i = 0 ; i < n ; i ++){
			arr[i] = i%cmod;
                        System.out.println(arr[i]);
			done[i] = false;
		}

		do{
			try{
				// Send a packet
				out.writeObject(arr[start + pointer]);
				System.out.println("Sending " + arr[start + pointer] + " : " + start + ":" + pointer);
					
				// Avoid sending packets that have had ACK
				if(done[start+pointer]){
					while(done[pointer + start]){
						pointer = (pointer + 1)%mod;
					}
				}
				else{
					pointer = (pointer + 1)%mod;
				}

				// Read ACK/NACK
				int message = (int) inp.readObject();

				if(message < 0){
					System.out.println("NACK " + Math.abs(message));
				}

				else{
					// Mark the ACK received
					int i;
					for(i = 0; i < mod ; i++){
						if(!done[start+i] && arr[start + i] == message){
							done[start + i] = true;
							System.out.println("ACK " + message);
							break;
						}
					}

					// Increment start if ACK for start of window
					// has been received
					if(i == 0){
						while(done[start]){
							start ++;
							end ++;
						}
						pointer = 0;
					}
				}
			}
			catch(Exception e){}
		} while(start < n);

		try{
			out.writeObject(-1);
			socket.close();
			out.close();
			inp.close();
		} catch(Exception e){}
	}
}
