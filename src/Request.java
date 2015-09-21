
public class Request {
	
	private int time;
	private String personName;
	private int startFloor;
	private int destinationFloor;
	
	public Request(int time, String personName, int startFloor, int destinationFloor)
	{
		this.time = time;
		this.personName = personName;
		this.startFloor = startFloor;
		this.destinationFloor = destinationFloor;
	}
	
	//Getters
	public int getTime()
	{
		return time;
	}
	
	public String getPersonName()
	{
		return personName;
	}
	
	public int getStartFloor()
	{
		return startFloor;
	}
	
	public int getDestinationFloor()
	{
		return destinationFloor;
	}

}
