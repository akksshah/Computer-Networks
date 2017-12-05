
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
class selective{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter sequence number size (bits)");
		int k = sc.nextInt();
		System.out.println("Enter number of packets");
		int n = sc.nextInt();

		System.out.println("1. Client\n2.Server");
		int choice = sc.nextInt();

		int port = 5000;
		String address = "localhost";

		if(choice == 2){
			Sender sender = new Sender(address, port);
			sender.run(k, n);
		}
		else{
			Receiver receiver = new Receiver(port);
			receiver.run(k, n);
		}
	}
}