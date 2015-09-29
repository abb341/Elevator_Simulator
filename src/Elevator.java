import java.util.ArrayList;

/**
 * This class handles the logic for the elevators. It controls the direction the elevator
 * travels, the loading and unloading of passengers, and the usage statistics.
 */
public class Elevator {
	
	private int currentFloor;
	private static int maxFloor;
	private int numPassengers = 0;
	private boolean goingUp;
	private ArrayList<Request> peopleOnElevatorList = new ArrayList<Request>();
	
	// Usage Statistics
	private int numPeopleServed = 0;
	private int numTimesGoneUp = 0;
	private int numTimesGoneDown = 0;
	
	public Elevator(int startFloor, int numFloors)
	{
		currentFloor = startFloor;
		maxFloor = numFloors;
	}
	
	// Accessors
	/**
	 * Gives the currentFloor
	 * @return currentFloor - the floor the elevator is on
	 */
	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	/**
	 * Returns an array list of the people who are on the elevator
	 * @return peopleOnElevatorList - array list of the people on the elevator
	 */
	public ArrayList<Request> getPeopleOnElevatorList()
	{
		return peopleOnElevatorList;
	}
	
	//Mutators
	/**
	 * Determines whether the elevator should move up or down
	 */
	public void move()
	{
		updateDirection();
		if (goingUp)
		{
			moveUp();
			numTimesGoneUp++;
		}
		else
		{
			moveDown();
			numTimesGoneDown++;
		}
	}
	
	//Other Public Methods
	/**
	 * Unloads passengers from the elevator by removing them from the peopleOnElevatorList
	 * @param peopleLeavingElevatorList - list of requests that are removed
	 */
	public void unloadPassengers(ArrayList<Request> peopleLeavingElevatorList)
	{
		String unloadedPassengers = "";
		
		//Unload the Passengers
		peopleOnElevatorList.removeAll(peopleLeavingElevatorList);
		numPassengers = peopleOnElevatorList.size();
		
		//Display Information
		for (int i = 0; i < peopleLeavingElevatorList.size(); i++)
		{
			unloadedPassengers += peopleLeavingElevatorList.get(i).getPersonName() + " ";
		}
		
		if (unloadedPassengers.length() > 0)
		{
			System.out.println("Unloaded on Floor " + currentFloor + ": " + unloadedPassengers);
		}
	}
	
	/**
	 * Loads passengers onto the elevator by adding peopleOnCurrentFloorList to the
	 * array list - peopleOnElevatorList
	 * @param peopleOnCurrentFloorList - an array list of people on the same floor as the elevator
	 */
	public void loadPassengers(ArrayList<Request> peopleOnCurrentFloorList)
	{
		String loadedPassengers = "";
		
		//Load the Passengers
		peopleOnElevatorList.addAll(peopleOnCurrentFloorList);
		
		//Update Information
		numPassengers = peopleOnElevatorList.size();
		numPeopleServed += peopleOnCurrentFloorList.size();
		
		//Display Information
		for (int i = 0; i < peopleOnCurrentFloorList.size(); i++)
		{
			loadedPassengers += peopleOnElevatorList.get(i).getPersonName() + " ";
		}
		
		if (loadedPassengers.length() > 0)
		{
			System.out.println("Loaded on Floor " + currentFloor + ": " + loadedPassengers);
		}
	}
	
	/**
	 * Display the information about the elevator
	 * @param elevatorNumber - allows user to distinguish which elevator is which
	 */
	public void display()
	{
		// Display Direction
		if (goingUp)
		{
			System.out.println("Moved up to floor " + currentFloor);
		}
		else
		{
			System.out.println("Moved down to floor " + currentFloor);
		}
		
		System.out.println(numPassengers + " passenger(s)");
	}
	
	/**
	 * Displays statistics at the end of the program
	 * @param elevatorNumber - allows user to distinguish which elevator is which
	 */
	public void displayUsageStatistics(int elevatorNumber)
	{
		System.out.println();
		System.out.println("*****Elevator " + elevatorNumber + " Usage Statistics*****");
		System.out.println("Passengers Served - " + numPeopleServed);
		System.out.println("Has traveled upwards " + numTimesGoneUp + " floors");
		System.out.println("Has traveled downwards " + numTimesGoneDown + " floors");
		System.out.println("*************************************");
	}
	
	
	//Other Private Methods
	/**
	 * Increase the value of currentFloor to move up
	 */
	private void moveUp()
	{
		//Move Up
		currentFloor++;
	}
	
	/**
	 * Decrease the value of currentFloor to move down
	 */
	private void moveDown()
	{
		//Move Down
		currentFloor--;
	}
	
	/**
	 * Update the direction of the elevator
	 */
	private void updateDirection()
	{
		if (goingUp)
		{
			if (currentFloor < maxFloor)
			{
				// There is room to keep going up
				goingUp = true;
			}
			else
			{
				goingUp = false;
			}
		}
		else
		{
			if (currentFloor > 1)
			{
				// There is room to keep going down
				goingUp = false;
			}
			else
			{
				goingUp = true;
			}
		}
	}
	
}
