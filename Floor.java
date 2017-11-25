/*this class implements Single Responsibity principle, 
as it contains only floor related functionalities*/

package elevatorentities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import controller.ElevatorControllerSimulator;
import factory.ElevatorSimulatorFactory;

public class Floor {
	private ArrayList<Person> waiting = new ArrayList<Person>();
	private String floorName;
	private int floorId;
	private Building building;
	private int personNumber = 0;

	static int instanceCounter = 0;
	String personCreatedtimeStamp;
	private int personWaitDurationForElevRequestProcess;

	constants.ElevatorConstants.DIRECTION direction = null;
	ElevatorSimulatorFactory elevatorSimulatorFactory = new ElevatorSimulatorFactory();

	public static enum Direction {
		UP, DOWN, IDLE
	}

	public Floor(String floorName, int floorId, Building building) {
		this.floorName = floorName;
		this.floorId = floorId;
		this.setBuilding(building);
	}

	public Floor() {
		
	}

	public void addWaiting(Person p) {
		waiting.add(p);
		// p.setWaittime(System.currentTimeMillis());

	}

	public void createNewPersonRequest() throws Exception {

		String timeStamp = new SimpleDateFormat("hh.mm.ss").format(new Timestamp(System.currentTimeMillis()));

		Random rand = new Random();
		// Generate random integers in range 0 to 20

		int fromFloor = 1 + rand.nextInt(20);
		int toFloor = 1 + rand.nextInt(20);

		while (fromFloor == toFloor) {
			toFloor = 1 + rand.nextInt(19);
		}

		direction = calculateDirectionOfPersonRequest(fromFloor, toFloor);

		int numberOfFloors = getNumberFloors();
		int timePerFloor = getTimePerFloor();
		int doorTime = getdoorTime();

		personWaitDurationForElevRequestProcess = calculatepersonWaitDurationForElevRequestProcess(numberOfFloors,
				timePerFloor, doorTime);

		instanceCounter++;
		personNumber = instanceCounter;

		//calling person object using factoryclass.
		Person person = elevatorSimulatorFactory.getPersonParameterized("Person " + personNumber,fromFloor,toFloor,direction);

		personCreatedtimeStamp = new SimpleDateFormat("ss").format(new Timestamp(System.currentTimeMillis()));
		/*System.out.println(
				"Total Seconds Person can wait on floor for its request to get processed " + personCreatedtimeStamp);*/
		System.out.println(timeStamp + " Person P" + personNumber + " created on Floor " + fromFloor + " ,wants to go "
				+ direction + " to Floor " + toFloor);
		System.out.println("Person P" + personNumber + " presses " + direction + " button on Floor " + fromFloor);
		/*System.out.println("Elevator P" + personNumber + " presses " + direction + " button on Floor " + fromFloor);*/
		// add person

		ElevatorControllerSimulator.getInstance().addPersonToTheList(person);
		Building.getInstance().addWaitersToFloor(person, toFloor);

		ElevatorControllerSimulator.getInstance().processPersonRequest(direction, personCreatedtimeStamp,
				personWaitDurationForElevRequestProcess);
		
		ElevatorControllerSimulator.getInstance().removePersonfromTheList(person);

	}

	private int calculatepersonWaitDurationForElevRequestProcess(int numberOfFloors, int timePerFloor, int doorTime) {
		int personWaitDurationForElevRequestProcess = (numberOfFloors * (timePerFloor + doorTime) * 2) / 1000;
		return personWaitDurationForElevRequestProcess;
	}

	private int getdoorTime() {
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String doorTimeString = properties.getProperty("ElevatorTimeAtDestination");
		int doorTime = Integer.parseInt(doorTimeString);
		return doorTime;

	}

	private int getTimePerFloor() {
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String timePerFloorString = properties.getProperty("TimePerFloor");
		int timePerFloor = Integer.parseInt(timePerFloorString);
		return timePerFloor;

	}

	private int getNumberFloors() {
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String numFloors = properties.getProperty("numOfFloors");
		int numFloor = Integer.parseInt(numFloors);
		return numFloor;

	}

	private constants.ElevatorConstants.DIRECTION calculateDirectionOfPersonRequest(int fromFloor, int toFloor) {

		if (fromFloor > toFloor) {
			direction = constants.ElevatorConstants.DIRECTION.DOWN;
		} else if (fromFloor < toFloor) {
			direction = constants.ElevatorConstants.DIRECTION.UP;
		}
		return direction;

	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	public int getPersonWaitDurationForElevRequestProcess() {
		return personWaitDurationForElevRequestProcess;
	}

	public void setPersonWaitDurationForElevRequestProcess(int personWaitDurationForElevRequestProcess) {
		this.personWaitDurationForElevRequestProcess = personWaitDurationForElevRequestProcess;
	}

	public String getPersonCreatedtimeStamp() {
		return personCreatedtimeStamp;
	}

	public void setPersonCreatedtimeStamp(String personCreatedtimeStamp) {
		this.personCreatedtimeStamp = personCreatedtimeStamp;
	}

}
