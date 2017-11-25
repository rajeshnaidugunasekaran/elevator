package factory;

import constants.ElevatorConstants.DIRECTION;
import elevatorentities.Building;
import elevatorentities.Elevator;
import elevatorentities.Floor;
import elevatorentities.Person;

/*this class implements factory design pattern, and creates all the classes objects here, which can be accessed anywhere else*/
public class ElevatorSimulatorFactory {

	public Floor getFloor() {
		return new Floor();
	}

	public Floor getFloorParameterized(String floorName, int floorId, Building building) {
		return new Floor(floorName, floorId, building);
	}

	public Person getPersonParameterized(String elevatorName, int elevatorNumber, int elevatorNumberr,
			DIRECTION direction) {
		return new Person(elevatorName, elevatorNumber, elevatorNumberr, direction);
	}

	public Elevator getElevatorParameterized(String elevatorName, int elevatorNumber, int initialFloor,
			Building building) throws Exception {
		return new Elevator(elevatorName, elevatorNumber, initialFloor, building);
	}
}
