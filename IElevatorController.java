package interfaces;

import constants.ElevatorConstants.DIRECTION;

/*this interface implements Interface Segregation Principle.*/
public interface IElevatorController {
	void sendRequestToElevator(String personName,int elevNum, int fromFloor, int toFloor, DIRECTION direction,
			String personCreatedtimeStamp, int personWaitDurationForElevRequestProcess);
	void populateWaitTimeByFloorMap();

}
