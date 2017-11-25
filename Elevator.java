/*this class implements Single Responsibity principle, 
as it contains only floor related functionalities*/

package elevatorentities;

import java.util.HashMap;
import java.util.List;

import constants.ElevatorConstants.DIRECTION;
import constants.ElevatorConstants.ELEVATOR_STATE;
import facade.ElevatorProcessInterface;
import nullobject.AbstractElevator;

public class Elevator extends AbstractElevator {
	
	ElevatorProcessInterface elevatorProcessInterface = new ElevatorProcessInterface();

	private int elevatorNumber;
	private int currentFloor = elevatorProcessInterface.toGetInitialFloorFromPropertiesFile();
	private Building building;
	private constants.ElevatorConstants.ELEVATOR_STATE status;
	private Integer ultimateHigh = Building.getInstance().getNumFloor();
	private Integer ultimateLow = elevatorProcessInterface.toGetUltimateLowFromPropertiesFile();

	private DIRECTION movingDirection;

	private HashMap<Integer, List<Person>> stops = new HashMap<Integer, List<Person>>();
	private int totalFloorsTravelled;

	// Parameterized Elevator constructor which sets all the parameters
	public Elevator(String ElevatorName, int elevatorNumber, int initialFloor, Building building) throws Exception {
		this.setElevatorNumber(elevatorNumber);
		this.setCurrentFloor(initialFloor);
		this.setBuilding(building);
		if (initialFloor == 1) {
			this.setStatus(ELEVATOR_STATE.IDLE);
			this.setMovingDirection(DIRECTION.NONE);
		}
		if (initialFloor == 1) {
			this.setStatus(ELEVATOR_STATE.IDLE);
		}
	}

	public Double getRequestCandidateFactor(int startFloor, int destFloor,
			constants.ElevatorConstants.DIRECTION destDirection) throws Exception {
		// to simply the algorithm, return the factor base-off the distance from the
		// next hit of the target floor

		// it decides, if the elevator is moving up direction or down direction.
		movingDirection = destFloor > currentFloor ? DIRECTION.UP : DIRECTION.DOWN;

		// if the elevator is under maintenance, then return null
		if (underMaintenance())
			return null;

		// if the elevator is in idle state.
		if (getStatus() == constants.ElevatorConstants.ELEVATOR_STATE.IDLE) {
			return getStaticDistance(destFloor);
		}

		// if elevator is moving down, also the person destination direction down.
		if (movingDirection == constants.ElevatorConstants.DIRECTION.DOWN
				&& destDirection == constants.ElevatorConstants.DIRECTION.DOWN && destFloor <= currentFloor
				&& startFloor <= currentFloor) {
			return getMovingDistance(destFloor);
		}

		if (movingDirection == constants.ElevatorConstants.DIRECTION.DOWN
				&& destDirection == constants.ElevatorConstants.DIRECTION.DOWN && destFloor <= currentFloor
				&& startFloor >= currentFloor) {
			return getMovingDirectionUltimateDoubleDistance(ultimateLow) + getMovingDistance(startFloor)
					+ getMovingDistance(destFloor);
		}

		// if elevator is moving up, also the person destination direction up.
		if (movingDirection == constants.ElevatorConstants.DIRECTION.UP
				&& destDirection == constants.ElevatorConstants.DIRECTION.UP && destFloor >= currentFloor
				&& startFloor >= currentFloor) {
			return getMovingDistance(destFloor);
		}

		// if elevator is moving up, also the person destination direction up.
		if (movingDirection == constants.ElevatorConstants.DIRECTION.UP
				&& destDirection == constants.ElevatorConstants.DIRECTION.UP && destFloor >= currentFloor
				&& startFloor <= currentFloor) {
			return getMovingDirectionUltimateDoubleDistance(ultimateHigh) + getMovingDistance(destFloor)
					+ getMovingDistance(startFloor);
		}

		// if elevator is moving in opposite direction to that of the person request.
		if (movingDirection == constants.ElevatorConstants.DIRECTION.DOWN
				&& destDirection == constants.ElevatorConstants.DIRECTION.UP && destFloor > currentFloor)

		{
			return getMovingDirectionUltimateDoubleDistance(ultimateLow) + (getMovingDistance(destFloor));
		}

		if (movingDirection == constants.ElevatorConstants.DIRECTION.DOWN
				&& destDirection == constants.ElevatorConstants.DIRECTION.UP && destFloor == currentFloor)

		{
			return getMovingDirectionUltimateDoubleDistance(ultimateLow);
		}

		if (movingDirection == constants.ElevatorConstants.DIRECTION.DOWN
				&& destDirection == constants.ElevatorConstants.DIRECTION.UP && destFloor < currentFloor)

		{
			return getMovingDirectionUltimateDistance(ultimateLow)
					+ (getMovingDistanceUltimateToDest(destFloor, ultimateLow));
		}
		// if elevator is moving in opposite direction to that of the person request.
		if (movingDirection == constants.ElevatorConstants.DIRECTION.UP
				&& destDirection == constants.ElevatorConstants.DIRECTION.DOWN && destFloor < currentFloor)

		{
			return getMovingDirectionUltimateDoubleDistance(ultimateHigh) + (getMovingDistance(destFloor));
		}

		if (movingDirection == constants.ElevatorConstants.DIRECTION.UP
				&& destDirection == constants.ElevatorConstants.DIRECTION.DOWN && destFloor == currentFloor)

		{
			return getMovingDirectionUltimateDoubleDistance(ultimateHigh);
		}

		if (movingDirection == constants.ElevatorConstants.DIRECTION.UP
				&& destDirection == constants.ElevatorConstants.DIRECTION.DOWN && destFloor > currentFloor)

		{
			return getMovingDirectionUltimateDistance(ultimateHigh)
					+ (getMovingDistanceUltimateToDest(destFloor, ultimateHigh));
		}

		// running out of time to figure out what else is possible, so throw an
		// exception so we can tell in unit testing
		throw new Exception("unsupported use case");
	}

	private Double getMovingDistanceUltimateToDest(int destFloor, Integer ultimateLow2) {
		return Double.valueOf(Math.abs(destFloor - ultimateLow2));
	}

	private Double getMovingDirectionUltimateDistance(Integer ultimateLow) {
		return Double.valueOf(Math.abs(currentFloor - ultimateLow));
	}

	// Calculates the double distance of the current floor to the ultimate position.
	public Double getMovingDirectionUltimateDoubleDistance(Integer ultimateStop) {
		return 2 * Double.valueOf(Math.abs(currentFloor - ultimateStop));
	}

	// Calculates the distance between the currentfloor of the elevator to the
	// destination of the person request
	public Double getMovingDistance(int destFloor) {
		return Double.valueOf(Math.abs(currentFloor - destFloor));
	}

	// it calculates the distance between the current floor of the elevator and
	// destination floor of the person request.
	public Double getStaticDistance(int destFloor) {
		return Double.valueOf(Math.abs(currentFloor - destFloor));
	}

	// elevator is undermaintenance
	public boolean underMaintenance() {
		return totalFloorsTravelled > 100;
	}

	public HashMap<Integer, List<Person>> getStops() {
		return stops;
	}

	public void setStops(HashMap<Integer, List<Person>> stops) {
		this.stops = stops;
	}

	public DIRECTION getMovingDirection() {
		return movingDirection;
	}

	public void setMovingDirection(DIRECTION movingDirection) {
		this.movingDirection = movingDirection;
	}

	public int getElevatorNumber() {
		return elevatorNumber;
	}

	public void setElevatorNumber(int elevatorNumber) {
		this.elevatorNumber = elevatorNumber;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public constants.ElevatorConstants.ELEVATOR_STATE getStatus() {
		return status;
	}

	public void setStatus(constants.ElevatorConstants.ELEVATOR_STATE status) {
		this.status = status;
	}

	@Override
	public boolean isNil() {
		return false;
	}

}