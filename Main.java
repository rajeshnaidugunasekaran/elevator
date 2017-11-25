
package elevatorProcessStart;

import facade.ElevatorProcessInterface;

/*Using facade class ElevatorProcessInterface, to call all the methods of the elevator process, 
and not exposing the implementation, and just exposing the methods declaration as an interface.*/

public class Main {

	public static void main(String[] args) throws Exception {

		ElevatorProcessInterface elevatorProcessInterface = new ElevatorProcessInterface();

		// to get the values from the properties file.
		//delegates to method toGetNumberOfElevatorsFromPropertiesFile.
		int numberOfElevators = elevatorProcessInterface.toGetNumberOfElevatorsFromPropertiesFile();
		int numberOfFloors = elevatorProcessInterface.toGetNumberOfFloorsFromPropertiesFile();

		// Initializes the number of Elevators and sets the frame values.
		//delegates to method toInitializeNumFloorsAndElevators.
		elevatorProcessInterface.toInitializeNumFloorsAndElevators(numberOfFloors, numberOfElevators);

		// Adds number of floors and elevators to the building.
		//delegates to method toAddNumFloorAndNumElevToTheBuilding.
		elevatorProcessInterface.toAddNumFloorAndNumElevToTheBuilding(numberOfFloors, numberOfElevators);

		// Calls method, which periodically creates the person request.
		//delegates to method toRunElevatorProcessTimeBasis.
		elevatorProcessInterface.toRunElevatorProcessTimeBasis();

	}

}
