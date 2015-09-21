import java.util.ArrayList;


public class Elevator {
	
	private int currentFloor;
	private static int maxFloor;
	private int numPassengers = 0;
	private boolean goingUp;
	private ArrayList<Request> peopleOnElevatorList = new ArrayList<Request>();
	
	public Elevator(int startFloor, int numFloors)
	{
		currentFloor = startFloor;
		maxFloor = numFloors;
	}
	
	// Accessors
	/**
	 * 
	 * @return currentFloor - the floor the elevator is on
	 */
	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	/**
	 * returns an array list of the people who are on the elevator
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
		}
		else
		{
			moveDown();
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
		System.out.println("Unloaded on Floor " + currentFloor + ": " + unloadedPassengers);
	}
	
	public void loadPassengers(ArrayList<Request> peopleOnCurrentFloorList)
	{
		String loadedPassengers = "";
		
		//Load the Passengers
		peopleOnElevatorList.addAll(peopleOnCurrentFloorList);
		numPassengers = peopleOnElevatorList.size();
		
		//Display Information
		for (int i = 0; i < peopleOnElevatorList.size(); i++)
		{
			loadedPassengers += peopleOnElevatorList.get(i).getPersonName() + " ";
		}
		System.out.println("Loaded on Floor " + currentFloor + ": " + loadedPassengers);
	}
	
	public void display(int elevatorNumber)
	{
		System.out.println("Elevator " + elevatorNumber + ":");
		if (goingUp)
		{
			System.out.println("UP");
		}
		else
		{
			System.out.println("DOWN");
		}
		System.out.println("Moved To Floor " + currentFloor);
		System.out.println(numPassengers + " passenger(s)");
	}
	
	//Other Private Methods
	private void moveUp()
	{
		//Move Up
		currentFloor++;
	}
	
	private void moveDown()
	{
		//Move Down
		currentFloor--;
	}
	
	private void updateDirection()
	{
		if (goingUp)
		{
			if (currentFloor < maxFloor)
			{
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
