import java.util.ArrayList;
import java.util.Scanner;


public class Simulator {

	private static final int NUM_ELEVATORS = 1;
	private static final int NUM_FLOORS = 5;

	public static void main(String[] args) {
		Scanner user = new Scanner(System.in);
		int maxTime;
		ArrayList<Elevator> elevatorList = new ArrayList<Elevator>(NUM_ELEVATORS);
		ArrayList<Request> requestList = new ArrayList<Request>();

		boolean didOpenRequestFile = ElevatorRequestReader.openRequestFile();

		if (didOpenRequestFile)
		{
			//Create New Elevator Objects
			for (int i = 0; i < NUM_ELEVATORS; i++)
			{
				//Note: I am subtracting 1 so that elevators can start on floor 0
				int startFloor = (int) (Math.random() * NUM_FLOORS) + 1;
				System.out.println("Elevator " + i + " start floor: " + startFloor);
				elevatorList.add(new Elevator(startFloor, NUM_FLOORS));
			}

			System.out.println("How many time ticks would you like this simulation to run?");
			maxTime = user.nextInt();

			for (int i = 0; i < maxTime; i++)
			{
				System.out.println("Time Elapsed: " + i);
				performAlgorithm(requestList, elevatorList);
				//performElevatorAlgorithm(elevatorList);
			}
		}

	}

	private static void performAlgorithm(ArrayList<Request> requestList,
			ArrayList<Elevator> elevatorList)
	{	
		requestList = newRequestList(requestList);
		
		String personName;
		int startFloor;
		int destinationFloor;
		
		if (requestList.size() > 0)
		{
			// Do Stuff With Request List
			for (int j = 0; j < requestList.size(); j++)
			{
				personName = requestList.get(j).getPersonName();
				startFloor = requestList.get(j).getStartFloor();
				destinationFloor = requestList.get(j).getDestinationFloor();
				System.out.println("Waiting: " + personName + "; Start Floor: " +
						startFloor + "; Destination: " + destinationFloor);
			}
		}
		
		// Perform Elevator Logic and Update The Request List
		requestList = performElevatorLogic(elevatorList, requestList);
	}

	/**
	 * Gets array of Requests and puts it into and ArrayList
	 * @param requestList
	 * @return
	 */
	private static ArrayList<Request> newRequestList(ArrayList<Request> requestList)
	{
		//requestList.removeAll(requestList);
		Request[] requestArray = ElevatorRequestReader.getRequests();

		for (int i = 0; i < requestArray.length; i++)
		{
			//Add each individual request from the array to the arraylist
			requestList.add(requestArray[i]);
		}

		return requestList;
	}

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
			unloadThesePeople = findWhoToUnload(elevatorList.get(i), peopleOnElevatorList);
			loadThesePeople = findWhoToLoad(elevatorList.get(i), requestList);


			//Elevator Logic
			elevatorList.get(i).unloadPassengers(unloadThesePeople);
			elevatorList.get(i).loadPassengers(loadThesePeople);
			elevatorList.get(i).move();
			elevatorList.get(i).display(i);
			
			//UPDATE REQUEST LIST HERE
			requestList = updateRequests(requestList, elevatorList);
		}
		return requestList;
	}

	private static ArrayList<Request> findWhoToUnload(Elevator elevator,
			ArrayList<Request> peopleOnElevatorList)
	{
		ArrayList<Request> unloadThesePeople = new ArrayList<Request>();
		int elevatorFloor = elevator.getCurrentFloor();
		int requestDestinationFloor;

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

	private static ArrayList<Request> findWhoToLoad(Elevator elevator,
			ArrayList<Request> requestList)
	{
		ArrayList<Request> loadThesePeople = new ArrayList<Request>();
		int elevatorFloor = elevator.getCurrentFloor();
		int requestStartFloor;

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

	private static ArrayList<Request> updateRequests(ArrayList<Request> requestList,
			ArrayList<Elevator> elevatorList)
	{
		for (int i = 0; i < elevatorList.size(); i++)
		{
			requestList.removeAll(elevatorList.get(i).getPeopleOnElevatorList());
		}
		return requestList;
	}



}
