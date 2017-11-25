package facade;

import java.util.Properties;

import elevator_gui.ElevatorDisplay;
import elevatorentities.Building;
import timemanager.TimeManager;

/*this class is the facade class, which contains implementation of all the methods*/

public class ElevatorProcessInterface {

	int numFloor = 0;
	int numElev = 0;
	int ultimateLow = 0;
	int initialFloor = 0;

	public int toGetNumberOfFloorsFromPropertiesFile() {
		// to get the values from the properties file.
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String numFloors = properties.getProperty("numOfFloors");
		return numFloor = Integer.parseInt(numFloors);
	}

	public int toGetNumberOfElevatorsFromPropertiesFile() {
		// to get the values from the properties file.
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String numElevators = properties.getProperty("numOfElevators");
		return numElev = Integer.parseInt(numElevators);
	}
	
	public int toGetUltimateLowFromPropertiesFile() {
		// to get the values from the properties file.
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String ultimateLowString = properties.getProperty("UltimateLow");
		return ultimateLow  = Integer.parseInt(ultimateLowString);
	}
	
	public int toGetInitialFloorFromPropertiesFile() {
		// to get the values from the properties file.
		Properties properties = Building.getInstance().toGetConstantsFromPropertiesFile();
		String initialFloorString = properties.getProperty("InitialFloor");
		return initialFloor   = Integer.parseInt(initialFloorString);
	}

	public void toInitializeNumFloorsAndElevators(int numberOfFloors, int numberOfElevators) {
		// Initializes the number of Elevators and sets the frame values.
		ElevatorDisplay.getInstance().initialize(numberOfFloors);
		for (int i = 1; i <= numberOfElevators; i++) {
			ElevatorDisplay.getInstance().addElevator(i, 1);
		}

	}

	public void toAddNumFloorAndNumElevToTheBuilding(int numberOfFloors, int numberOfElevators) throws Exception {
		// Adds number of floors and elevators to the building.
		Building.getInstance().processNumberOfFloorsAndElevatorsToBuilding(numberOfFloors, numberOfElevators);
	}

	public void toRunElevatorProcessTimeBasis() throws Exception {
		// Calls method, which periodically creates the person request.
		TimeManager.getInstance().runtime();
	}

}
