/*This class implements Singleton Design Pattern*/

package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import constants.ElevatorConstants.DIRECTION;
import constants.ElevatorConstants.ELEVATOR_STATE;
import constants.ElevatorConstants.PERSON_STATUS;
import elevator_gui.ElevatorDisplay;
import elevatorentities.AvgMinMaxWaitTimeByFloor;
import elevatorentities.Building;
import elevatorentities.Elevator;
import elevatorentities.Person;
import interfaces.IElevatorController;
import nullobject.NullElevator;

public class ElevatorControllerSimulator implements IElevatorController {

	int numFloors = getNumFloorsFromProperties();
	private static ElevatorControllerSimulator instance;
	private int desiredElevatorNumber;
	private List<Person> persons = new ArrayList<Person>();
	private List<Elevator> elevators;
	String currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));

	private Map<Integer, AvgMinMaxWaitTimeByFloor> waitTimeByFloor = new HashMap<>();

	public static ElevatorControllerSimulator getInstance() {
		if (instance == null) {
			instance = new ElevatorControllerSimulator();
		}
		return instance;
	}

	/*
	 * this method is used for generating waittimebyfloor table at end of the
	 * simulation
	 */
	public void populateWaitTimeByFloorMap() {
		for (int i = 1; i <= numFloors; i++) {
			waitTimeByFloor.put(i, new AvgMinMaxWaitTimeByFloor());
			setWaitTimeByFloor(waitTimeByFloor);
		}
	}

	/* this method gets the number of elevators from the building. */
	private ArrayList<Elevator> getElevators() {
		return Building.getInstance().getelevators();
	}

	public synchronized void sendRequestToElevator(String personName, int elevNum, int fromFloor, int toFloor,
			DIRECTION direction, String personCreatedtimeStamp, int personWaitDurationForElevRequestProcess) {

		currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
		int processTime;
		int timePerFloor = getTimePerFloorFromPropertiesFile();
		ElevatorDisplay.getInstance().closeDoors(elevNum);
		System.out.println(currentTimeStamp + " Elevator " + elevNum + " going to Floor " + toFloor + " for "
				+ direction + " request [Floor Requests: None][Rider Requests: None] ");

		if (waitTimeByFloor.isEmpty()) {
			populateWaitTimeByFloorMap();
		}
		if (fromFloor < toFloor) {
			for (int i = fromFloor; i <= toFloor; i++) {
				currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
				int nxtFloor = i + 1;
				ElevatorDisplay.getInstance().updateElevator(elevNum, i, toFloor, ElevatorDisplay.Direction.UP);
				if (i != toFloor) {
					System.out.println(currentTimeStamp + " Elevator " + elevNum + " moving " + direction
							+ " from Floor" + i + " to Floor" + nxtFloor + "[Floor Requests: " + toFloor
							+ "][Rider Requests: None]");
				}
				try {
					Thread.sleep(timePerFloor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
			ElevatorDisplay.getInstance().openDoors(elevNum);
			System.out.println(currentTimeStamp + " Elevator" + elevNum + " Doors Open");
			String elevatorProcessingPersonTimeString = new SimpleDateFormat("ss")
					.format(new Timestamp(System.currentTimeMillis()));
			int elevatorProcessingPersonTime = Integer.parseInt(elevatorProcessingPersonTimeString);
			int personCreatedtimeStampint = Integer.parseInt(personCreatedtimeStamp);
			if ((elevatorProcessingPersonTime - personCreatedtimeStampint) < personWaitDurationForElevRequestProcess) {
				System.out.println(
						currentTimeStamp + personName + " has left Floor " + toFloor + " [Floor People: None] ");
				System.out.println(personName + " entered Elevator " + elevNum + " [Riders: " + personName + "]");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				ElevatorDisplay.getInstance().closeDoors(elevNum);
				System.out.println("Elevator" + elevNum + " Doors Close");
				processTime = Math.abs(elevatorProcessingPersonTime - personCreatedtimeStampint);

				populateFloorWisePersonWaitTime(processTime, fromFloor);

				System.out.println("The person had to wait for " + processTime + "seconds");
			} else {
				System.out.println("Person Waited for very long, got annoyed and left the floor");
			}

		} else {
			for (int i = fromFloor; i >= toFloor; i--) {
				currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
				int nxtFloor = i - 1;
				ElevatorDisplay.getInstance().updateElevator(elevNum, i, toFloor, ElevatorDisplay.Direction.DOWN);
				if (i != toFloor) {
					System.out.println(currentTimeStamp + "Elevator " + elevNum + " moving " + direction + " from Floor"
							+ i + " to Floor" + nxtFloor + "[Floor Requests: " + toFloor + "][Rider Requests: None");
				}
				try {
					Thread.sleep(timePerFloor);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentTimeStamp = new SimpleDateFormat("hh:mm:ss").format(new Timestamp(System.currentTimeMillis()));
			ElevatorDisplay.getInstance().openDoors(elevNum);
			System.out.println("Elevator" + elevNum + " Doors Open");
			String elevatorProcessingPersonTimeString = new SimpleDateFormat("ss")
					.format(new Timestamp(System.currentTimeMillis()));
			int elevatorProcessingPersonTime = Integer.parseInt(elevatorProcessingPersonTimeString);
			int personCreatedtimeStampint = Integer.parseInt(personCreatedtimeStamp);
			if ((elevatorProcessingPersonTime - personCreatedtimeStampint) < personWaitDurationForElevRequestProcess) {
				System.out.println(currentTimeStamp + " Person P has left Floor " + toFloor + " [Floor People: None] ");
				System.out.println(
						currentTimeStamp + " Person P entered Elevator " + elevNum + " [Riders: " + personName + "]");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ElevatorDisplay.getInstance().closeDoors(elevNum);
				System.out.println(currentTimeStamp + "Elevator" + elevNum + " Doors Closed");
				processTime = Math.abs(elevatorProcessingPersonTime - personCreatedtimeStampint);

				populateFloorWisePersonWaitTime(processTime, fromFloor);

				System.out.println(currentTimeStamp + "The person had to wait for " + processTime + " seconds");

			} else {
				System.out.println(currentTimeStamp + "Person Waited for very long, got annoyed and left the floor");
			}
		}
	}

	private void populateFloorWisePersonWaitTime(int processTime, int fromFloor) {
		AvgMinMaxWaitTimeByFloor avgMinMaxWaitTimeByFloor = waitTimeByFloor.get(fromFloor);
		List<Integer> waitTime = avgMinMaxWaitTimeByFloor.getWaitTime();
		waitTime.add(processTime);
		AvgMinMaxWaitTimeByFloor avg = new AvgMinMaxWaitTimeByFloor();
		avg.setWaitTime(waitTime);
		waitTimeByFloor.replace(fromFloor, avg);
	}

	private int getTimePerFloorFromPropertiesFile() {
		int timePerFloor = 0;
		try {
			File file = new File("config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			String timePerFloorString = properties.getProperty("TimePerFloor");

			timePerFloor = Integer.parseInt(timePerFloorString);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return timePerFloor;
	}

	private int getNumFloorsFromProperties() {
		int numOfFloors = 0;
		try {
			File file = new File("config.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			String numFloorsString = properties.getProperty("numOfFloors");

			numOfFloors = Integer.parseInt(numFloorsString);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return numOfFloors;
	}

	public int getNumElevators() {
		return getElevators().size();
	}

	public synchronized void moveelevator(String personName, int elevNum, int fromFloor, int toFloor,
			DIRECTION direction, String personCreatedtimeStamp, int personWaitDurationForElevRequestProcess) {

		if (((toFloor > 0) && (toFloor <= numFloors)) && ((elevNum > 0) && (elevNum <= getNumElevators()))) {
			sendRequestToElevator(personName, elevNum, fromFloor, toFloor, direction, personCreatedtimeStamp,
					personWaitDurationForElevRequestProcess);
		} else {
			System.out.println("We're are sorry, but you have requested an invalid floor or an invalid elevator.");
		}

	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public void addPersonToTheList(Person person) throws Exception {
		this.persons.add(person);
	}

	public synchronized void processPersonRequest(DIRECTION direction, String personCreatedtimeStamp,
			int personWaitDurationForElevRequestProcess) throws Exception {

		// remove all passengers reached destination
		Iterator<Person> iter = persons.iterator();
		while (iter.hasNext()) {
			Person p = iter.next();
			if (p.getStatus() == PERSON_STATUS.REACHED) {
				iter.remove();
			}
			if (p.getStatus() == PERSON_STATUS.WAIT) {
				nullobject.AbstractElevator abstractElevator = requestElevator(p);
				Elevator elevator = (Elevator) abstractElevator;
				moveelevator(p.getPersonName(), elevator.getElevatorNumber(), p.getStartfloor(), p.getDestfloor(),
						direction, personCreatedtimeStamp, personWaitDurationForElevRequestProcess);
				
				ArrayList<Elevator> elevators1 = Building.getInstance().getelevators();
				elevators = new ArrayList<>(elevators1);
				toSetTheCurrentFloorOfTheElevatorSelected(elevators, elevator, p.getDestfloor());
			}
		}

	}

	private synchronized nullobject.AbstractElevator requestElevator(Person person) throws Exception {
		Double nearestElevator = null;
		Elevator elevatorSelected = null;
		ArrayList<Elevator> elevators1 = Building.getInstance().getelevators();
		for (Elevator elevator : elevators1) {
			// find out nearestElevator to process the person request of
			// elevators
			Double newNearestElevator = elevator.getRequestCandidateFactor(person.getStartfloor(),person.getDestfloor(),
					person.getDestfloor() > person.getStartfloor() ? constants.ElevatorConstants.DIRECTION.UP
							: constants.ElevatorConstants.DIRECTION.DOWN);
			if (nearestElevator == null) {
				nearestElevator = newNearestElevator;
				elevatorSelected = elevator;
			}
			if (nearestElevator != null && newNearestElevator < nearestElevator) {
				nearestElevator = newNearestElevator;
				elevatorSelected = elevator;
			}
		}
		if (elevatorSelected == null) {

			// Implementation of Null Object Design Pattern
			return new NullElevator();
		}
		return elevatorSelected;
	}

	private synchronized void toSetTheCurrentFloorOfTheElevatorSelected(List<Elevator> elevators,
			Elevator elevatorSelected, int currentFloor) {
		for (Elevator elevator : elevators) {
			if (elevator.getElevatorNumber() == elevatorSelected.getElevatorNumber()) {
				elevator.setCurrentFloor(currentFloor);
				elevator.setStatus(ELEVATOR_STATE.MOVING);
			}
		}
	}

	public List<Elevator> getelevators() {
		return elevators;
	}

	public void setelevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public Map<Integer, AvgMinMaxWaitTimeByFloor> getWaitTimeByFloor() {
		return waitTimeByFloor;
	}

	public void setWaitTimeByFloor(Map<Integer, AvgMinMaxWaitTimeByFloor> waitTimeByFloor) {
		this.waitTimeByFloor = waitTimeByFloor;
	}

	public int getNumRequests() {
		return desiredElevatorNumber;
	}

	public void removePersonfromTheList(Person person) {
		this.persons.remove(person);
	}

}
