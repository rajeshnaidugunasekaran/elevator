
/*Building class implements Singleton design pattern, only one instance is 
created and private constructor is created*/


/*this class also implements single responsibility principle*/

package elevatorentities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import controller.ElevatorControllerSimulator;
import factory.ElevatorSimulatorFactory;
import interfaces.IBuilding;

public class Building implements IBuilding {

	private ArrayList<Floor> floors = new ArrayList<Floor>();
	private ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	private ElevatorControllerSimulator elevatorController;
	private static Building instance;
	private int numFloor = 1;
	private int initialFloor = 1;

	// Factory Design Pattern is implemented
	ElevatorSimulatorFactory elevatorSimulatorFactory = new ElevatorSimulatorFactory();

	// creates instance for building class
	public static Building getInstance() {
		if (instance == null) {
			instance = new Building();
		}
		return instance;
	}

	// adds people waiting to floor
	public void addWaitersToFloor(Person p, int floorNum) {
		floors.get(floorNum - 1).addWaiting(p);
	}

	// This method creates the person object and processes its request
	public void createPersonRequest() throws Exception {
		//calling floor object using factoryclass.
		Floor floor = elevatorSimulatorFactory.getFloor();
		floor.createNewPersonRequest();
	}

	// This method adds number of floors and number of elevators to the building.
	public List<Elevator> processNumberOfFloorsAndElevatorsToBuilding(int numFloor, int numElevators) throws Exception {
		this.setNumFloor(numFloor);
		addFloorsToTheBuilding(numFloor);
		ArrayList<Elevator> elevators = addElevatorsToTheBuilding(numElevators);
		return elevators;

	}

	
	//this method adds Elevators to the building.
	private ArrayList<Elevator> addElevatorsToTheBuilding(int numElevators) throws Exception {
		for (int i = 1; i <= numElevators; i++) {
			//calling elevator object using factoryclass.
			//Elevator elevator = elevatorSimulatorFactory.getElevatorParameterized("Elevator"+i, i, initialFloor, this);
			Elevator elevator = elevatorSimulatorFactory.getElevatorParameterized("Elevator"+i, i, initialFloor, this);
			elevators.add(elevator);
		}
		return elevators;
	}

	//this method adds Floors to the building.
	private void addFloorsToTheBuilding(int numFloor) {
		for (int i = 1; i <= numFloor; i++) {
			Floor floor = elevatorSimulatorFactory.getFloorParameterized("Floor "+i, i, this);
			floors.add(floor);
		}
	}

	// Below method loads the config file, so that we can read properties from
	// those.
	public Properties toGetConstantsFromPropertiesFile() {
		Properties properties = new Properties();
		try {
			File file = new File("config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			properties.load(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	public int getNumFloor() {
		return numFloor;
	}

	public void setNumFloor(int numFloor) {
		this.numFloor = numFloor;
	}

	public ArrayList<Elevator> getelevators() {
		return elevators;
	}

	public void setelevators(ArrayList<Elevator> elevators) {
		this.elevators = elevators;
	}

	public int getInitialFloor() {
		return initialFloor;
	}

	public void setInitialFloor(int initialFloor) {
		this.initialFloor = initialFloor;
	}

	public ElevatorControllerSimulator getElevatorController() {
		return elevatorController;
	}

	public void setElevatorController(ElevatorControllerSimulator elevatorController) {
		this.elevatorController = elevatorController;
	}

	public ArrayList<Floor> getFloors() {
		return floors;
	}

	public void setFloors(ArrayList<Floor> floors) {
		this.floors = floors;
	}

	public static void setInstance(Building instance) {
		Building.instance = instance;
	}
}
