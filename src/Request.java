/**
 * This class stores the information in each request
 * @author abrown
 *
 */
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
	
	// Accessors
	/**
	 * Gets the value for the time the request arrives
	 * @return time
	 */
	public int getTime()
	{
		return time;
	}
	
	/**
	 * Gets the value of the person's name
	 * @return personName
	 */
	public String getPersonName()
	{
		return personName;
	}
	
	/**
	 * Gets the floor that the request starts on
	 * @return startFloor
	 */
	public int getStartFloor()
	{
		return startFloor;
	}
	
	/**
	 * Gets the floor that the request wants to go to
	 * @return destinationFloor
	 */
	public int getDestinationFloor()
	{
		return destinationFloor;
	}

}
