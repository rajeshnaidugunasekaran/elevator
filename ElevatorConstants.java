package constants;

public interface ElevatorConstants {

	// Directions of the Elevator and Person Request
	public enum DIRECTION {
		UP, DOWN, NONE
	};

	// Elevator status
	public enum ELEVATOR_STATE {
		IDLE, MOVING, LOADING
	};

	// Person status
	public enum PERSON_STATUS {
		WAIT, ASSIGNED, MOVING, REACHED
	};

}
