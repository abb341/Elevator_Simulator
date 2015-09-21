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
	
	//Accessors
	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	public ArrayList<Request> getPeopleOnElevatorList()
	{
		return peopleOnElevatorList;
	}
	
	//Mutators
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
		//Load the Passengers
		peopleOnElevatorList.addAll(peopleOnCurrentFloorList);
		numPassengers = peopleOnElevatorList.size();
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
