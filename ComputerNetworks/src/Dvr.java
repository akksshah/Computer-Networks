import java.util.*;


public class Dvr
{
	public static int returnRandom()
	{
		Random randomNumber = new Random();
		//0 inclusive, 10 exclusive
		return (randomNumber.nextInt(10)+1);
	}

	public static void printRoutingTable(int[] array, int router, int numberOfRouters)
	{
		System.out.println("Table for Router " + (char)	(router+65));
		for(int i=0; i<numberOfRouters; i++)
			System.out.print(array[i] + "\t");
		System.out.println();
	}

	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of routers in the network.");
		int numberOfRouters = sc.nextInt();
		System.out.println("Enter the network topology.");
		int[][] networkTopology = new int[numberOfRouters][numberOfRouters];
		for(int i =0; i<numberOfRouters; i++)
			for(int j=0; j<numberOfRouters; j++)
				networkTopology[i][j] = sc.nextInt();

		System.out.println("Enter the number of the router whose table you would like to know. \n(in ones and zeroes where 1 corresponds to a connection and zero corresponds to no connection)");
		int router = sc.nextInt();

		int[] neighbours = new int[numberOfRouters];
		int totalNeighbours = 0;
		for(int i=0; i<numberOfRouters; i++)
		{
			if(networkTopology[router-1][i]==1)
			{
				neighbours[totalNeighbours] = i;
				totalNeighbours++;
			}
		}

		List<int[]> listOfArrays = new ArrayList<int[]>();
		for(int i=0; i<totalNeighbours; i++)
		{
			int[] array = new int[numberOfRouters];
			int currentNeighbour = neighbours[i];
			for(int j=0; j<numberOfRouters; j++)
			{
				if(j==currentNeighbour)
					array[j] = 0;
				else
					array[j] = returnRandom();
			}
			listOfArrays.add(array);
			printRoutingTable(array, currentNeighbour, numberOfRouters);
		}

		System.out.println("There are a total of " + totalNeighbours + " neighbours.");
		int[] delay = new int[totalNeighbours];
		for(int i=0; i<totalNeighbours; i++)
		{
			int currentNeighbour = neighbours[i];
			System.out.println("Delay to neighbour " + (char)(currentNeighbour+65) + " is:");
			delay[i] = sc.nextInt();
		}

		int[] finalTable = new int[numberOfRouters];
		int[] finalDelay = new int[numberOfRouters];
		int viaRouter = 100;
		for(int i=0; i<numberOfRouters; i++)
		{
			int min = 1000;
			int neighbourNumber = -1;
			if(i==(router-1))
			{
				viaRouter = -20;
				min = 0;
			}
			else
			{
				for(int[] table: listOfArrays)
				{
					neighbourNumber++;
					int time = delay[neighbourNumber] + table[i];
					if(time<min)
					{
						min = time;
						viaRouter = neighbours[neighbourNumber];
					}
				}
			}
			finalTable[i] = viaRouter;
			finalDelay[i] = min;
		}

		System.out.println("Table for Router " + (char)	(router+65));
		for(int i=0; i<numberOfRouters; i++)
			System.out.print((char)(finalTable[i]+65) + "\t");
		System.out.println();
		System.out.println("Final Delay");
		for(int i=0; i<numberOfRouters; i++)
			System.out.print(finalDelay[i] + "\t");
		System.out.println();
	}
}

/*student@student-Inspiron-5548:~/Desktop/Pracs$ javac Dvr.java
student@student-Inspiron-5548:~/Desktop/Pracs$ java Dvr
Enter the number of routers in the network.
4
Enter the network topology.
0                                
1
1
0
1
0
0
1
1
0
0
1
0
1
1
0
Enter the number of the router whose table you would like to know. 
(in ones and zeroes where 1 corresponds to a connection and zero corresponds to no connection)
2
Table for Router A
0	9	6	6	
Table for Router D
8	5	5	0	
There are a total of 2 neighbours.
Delay to neighbour A is:
1
Delay to neighbour D is:
3
Table for Router B
A	-	A	D	
Final Delay
1	0	7	3	*/