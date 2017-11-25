/*This class implements Data transfer Object Design Pattern,
 *  as this class contains a POJO class, and its attributes, 
 *  which can be used and transferred as object.
 *  It does not have any behavior.
 *  */


/*this class also implements single responsibility principle*/
package elevatorentities;

import constants.ElevatorConstants.DIRECTION;
import constants.ElevatorConstants.PERSON_STATUS;

public class Person {
	private String personName;
	private int startfloor;
	private int destfloor;
	private PERSON_STATUS status;
	private DIRECTION personMoveDirection;

	// Person constructor, it initializes all the parameters of person
	public Person(String personName, int startfloor, int destfloor, DIRECTION direction) {
		this.personName = personName;
		this.startfloor = startfloor;
		this.destfloor = destfloor;
		setStatus(PERSON_STATUS.WAIT);
		setPersonMoveDirection(direction);
	}

	public int getStartfloor() {
		return startfloor;
	}

	public void setStartfloor(int startfloor) {
		this.startfloor = startfloor;
	}

	public int getDestfloor() {
		return destfloor;
	}

	public void setDestfloor(int destfloor) {
		this.destfloor = destfloor;
	}

	public PERSON_STATUS getStatus() {
		return status;
	}

	public void setStatus(PERSON_STATUS status) {
		this.status = status;
	}

	public DIRECTION getPersonMoveDirection() {
		return personMoveDirection;
	}

	public void setPersonMoveDirection(DIRECTION personMoveDirection) {
		this.personMoveDirection = personMoveDirection;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
