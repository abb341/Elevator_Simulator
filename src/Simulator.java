import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Simulator class handles the logic of the simulation. It calls the different methods
 * in Elevator and Request and ElevatorRequestReader.
 */
public class Simulator {

	private static final int NUM_ELEVATORS = 2;
	private static final int NUM_FLOORS = 5;

	/****************************************************************************** *
	* Name: Aaron Brown
	* Block: G
	* Date: September 28, 2015
	*
	* Program #4B: Elevator Simulator
	* Description:
	* 	This program simulates the behavior of a number of elevators as they respond
	* to different requests. At each time interval, people arrive at different
	* floors, elevators move up or down by one floor, and loads or unloads.
	* ******************************************************************************/
	public static void main(String[] args) 
	{
		int maxTime = getMaxTimeFromUser();
		ArrayList<Elevator> elevatorList = new ArrayList<Elevator>(NUM_ELEVATORS);
		ArrayList<Request> requestList = new ArrayList<Request>();

		boolean didOpenRequestFile = ElevatorRequestReader.openRequestFile();

		if (didOpenRequestFile)
		{
			//Create New Elevator Objects
			elevatorList = createNewElevators(elevatorList);
			
			//Simulate the Elevators
			for (int i = 0; i < maxTime; i++)
			{
				System.out.println("Time: " + i);
				performAlgorithm(requestList, elevatorList);
			}
			
			// End of simulation - display the statistics
			displayUsageStatistics(elevatorList);
		}

	}
	
	/**
	 * Gets the amount of time the simulation will run for
	 * @return maxTime - specified by the user
	 */
	private static int getMaxTimeFromUser()
	{
		Scanner user = new Scanner(System.in);
		int maxTime;
		
		System.out.println("How many time ticks would you like "
				+ "this simulation to run?");
		maxTime = user.nextInt();
		
		return maxTime;
	}
	
	/**
	 * Create new elevator objects
	 * @param elevatorList - array list of Elevator objects
	 * @return elevatorList - array list of Elevator objects
	 */
	private static ArrayList<Elevator> 
		createNewElevators(ArrayList<Elevator> elevatorList)
	{
		for (int i = 0; i < NUM_ELEVATORS; i++)
		{
			// Choose a random floor to start on
			//Note: I am adding 1 so that elevators can start on floor 1
			int startFloor = (int) (Math.random() * NUM_FLOORS) + 1;
			System.out.println("Elevator " + i + " start floor: " + startFloor);
			elevatorList.add(new Elevator(startFloor, NUM_FLOORS));
		}
		return elevatorList;
	}

	/**
	 * Handles the logic for the simulator and displays request information
	 * @param requestList - Array List of Requests
	 * @param elevatorList - Array List of Elevators
	 */
	private static void performAlgorithm(ArrayList<Request> requestList,
			ArrayList<Elevator> elevatorList)
	{	
		requestList = newRequestList(requestList);
		
		if (requestList.size() > 0)
		{
			
			// Display Information by FLOOR
			displayInfoByFloor(requestList);
		}
		
		// Perform Elevator Logic and Update The Request List
		requestList = performElevatorLogic(elevatorList, requestList);
	}
	
	/**
	 * Display the requests on each floor
	 * @param requestList - array list of requests
	 */
	private static void displayInfoByFloor(ArrayList<Request> requestList)
	{
		// this will help make the display less cluttered
		boolean didPrintFloor = false;
		
		for (int i = 1; i <= NUM_FLOORS; i++)
		{
			for (int j = 0; j < requestList.size(); j++)
			{
				if (requestList.get(j).getStartFloor() == i)
				{
					if (!didPrintFloor)
					{
						System.out.println("Floor " + i);
						didPrintFloor = true;
					}
					
					System.out.println(requestList.get(j).getPersonName() +
							" wants to go to floor " +
							requestList.get(j).getDestinationFloor());
				}
			}
			// reset didPrintFloor
			didPrintFloor = false;
		}
	}

	/**
	 * Gets array of Requests and puts it into and ArrayList
	 * @param requestList - array list of Request objects
	 * @return requestList - array list of Request objects
	 */
	private static ArrayList<Request> newRequestList(ArrayList<Request> requestList)
	{
		Request[] requestArray = ElevatorRequestReader.getRequests();

		for (int i = 0; i < requestArray.length; i++)
		{
			//Add each individual request from the array to the arraylist
			requestList.add(requestArray[i]);
		}

		return requestList;
	}

	/**
	 * Performs a majority of the logic required to simulate the elevators
	 * @param elevatorList - array list of Elevator objects
	 * @param requestList - array list of Request objects
	 * @return requestList - array list of Request objects
	 */
	private static ArrayList<Request> performElevatorLogic(ArrayList<Elevator> elevatorList,
			ArrayList<Request> requestList)
	{
		ArrayList<Request> loadThesePeople = new ArrayList<Request>();
		ArrayList<Request> unloadThesePeople = new ArrayList<Request>();
		ArrayList<Request> peopleOnElevatorList = new ArrayList<Request>();

		for (int i = 0; i < NUM_ELEVATORS; i++)
		{
			// Determine who to load and unload
			peopleOnElevatorList = elevatorList.get(i).getPeopleOnElevatorList();
			unloadThesePeople = 
					findWhoToUnload(elevatorList.get(i), peopleOnElevatorList);
			
			loadThesePeople = findWhoToLoad(elevatorList.get(i), requestList);


			//Elevator Logic
			System.out.println("Elevator " + i + ":");
			elevatorList.get(i).unloadPassengers(unloadThesePeople);
			elevatorList.get(i).loadPassengers(loadThesePeople);
			elevatorList.get(i).move();
			elevatorList.get(i).display();
			
			// Update the request list so that it contains only unanswered requests
			requestList = updateRequests(requestList, elevatorList);
		}
		
		return requestList;
	}

	/**
	 * Finds the passengers on the elevator that want to get off on the current floor
	 * @param elevator - Elevator object
	 * @param peopleOnElevatorList - array list of people currently on the elevator
	 * @return unloadThesePeople - array list of people to unload
	 */
	private static ArrayList<Request> findWhoToUnload(Elevator elevator,
			ArrayList<Request> peopleOnElevatorList)
	{
		ArrayList<Request> unloadThesePeople = new ArrayList<Request>();
		int elevatorFloor = elevator.getCurrentFloor();
		int requestDestinationFloor;

		// Unload requests that want to get off on the current floor
		for (int i = 0; i < peopleOnElevatorList.size(); i++)
		{
			requestDestinationFloor = peopleOnElevatorList.get(i).getDestinationFloor();
			if (elevatorFloor == requestDestinationFloor)
			{
				unloadThesePeople.add(peopleOnElevatorList.get(i));
			}
		}

		return unloadThesePeople;
	}

	/**
	 * Finds who is on the same floor as the elevator and loads them onto the elevator
	 * @param elevator - Elevator object
	 * @param requestList - array list of requests
	 * @return loadThesePeople - array list of people who will be put in the elevator
	 */
	private static ArrayList<Request> findWhoToLoad(Elevator elevator,
			ArrayList<Request> requestList)
	{
		ArrayList<Request> loadThesePeople = new ArrayList<Request>();
		int elevatorFloor = elevator.getCurrentFloor();
		int requestStartFloor;
		
		// Load all the requests that are on the same floor as the elevator
		for (int i = 0; i < requestList.size(); i++)
		{
			requestStartFloor = requestList.get(i).getStartFloor();
			if (elevatorFloor == requestStartFloor)
			{
				loadThesePeople.add(requestList.get(i));
			}
		}

		return loadThesePeople;
	}

	/**
	 * Updates the requestList with new requests
	 * @param requestList - array list of Requests
	 * @param elevatorList - array list of Elevators
	 * @return requestList - array list of Requests
	 */
	private static ArrayList<Request> updateRequests(ArrayList<Request> requestList,
			ArrayList<Elevator> elevatorList)
	{
		/* Remove all the people who got on the elevator
		 * 
		 * Note: the requestList is the list of requests that have
		 * not yet been addressed by the elevator
		 */
		for (int i = 0; i < elevatorList.size(); i++)
		{
			requestList.removeAll(elevatorList.get(i).getPeopleOnElevatorList());
		}
		
		return requestList;
	}

	/**
	 * Displays the statistics about each elevator at the end of the program
	 * @param elevatorList - the array list that contains the elevators
	 */
	private static void displayUsageStatistics(ArrayList<Elevator> elevatorList)
	{
		// Display the statistics for each elevator
		for (int i = 0; i < elevatorList.size(); i++)
		{
			elevatorList.get(i).displayUsageStatistics(i);
		}
	}

}
