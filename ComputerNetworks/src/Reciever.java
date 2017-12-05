
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class Receiver{
	Socket socket = null;
	ObjectOutputStream out = null;
	ObjectInputStream inp = null;

	Receiver(int port){
		try{
		ServerSocket serverSocket = new ServerSocket(port);
		socket = serverSocket.accept();

		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		inp = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e){}
	}

	public void run(int k, int n){
		int mod = (int) Math.pow(2, k-1);
                System.out.print("hello aakasj");
		int cmod = (int) Math.pow(2, k);
		int arr[] = new int[n];
		boolean done[] = new boolean[n];

		for(int i = 0 ; i < n ; i++) {
			arr[i] = i%cmod;
			done[i] = false;
		}

		int start = 0, end = mod - 1;
		
		do{
			boolean sent = false;
			try{
				// Read message
				int message = (int) inp.readObject();
				// Indicates end of packets
				if(message < 0) break;
				
				// Randomly drop a packet
				if((int) (Math.random()*10) > 8){
					System.out.println("Dropped " +message);
					out.writeObject(-message);
					continue;
				}

				int i;

				// Mark received packet as done
				// Send acknowledgement
				for(i = 0 ; i < mod ; i ++){
					if(!done[start + i] && message == arr[start + i]){
						System.out.println("Recieved " + message);
						out.writeObject(message);
						done[start + i] = true;
						sent = true;
						break;
					}
				}

				// Increment if received beginning of window
				while(done[start] && start < n){ start++; end++; }
				
				// Break if necessary
				if(start == n) break;

				// In case Sender sends a packet not inside the window
				if(!sent) out.writeObject(-69);
			} catch(Exception e){}


		}while(true);

		try{
			socket.close();
			inp.close();
			out.close();
		} catch(Exception e){}
	}
}
