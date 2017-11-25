/*this class implements Singleton design pattern.*/

package timemanager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import elevatorentities.AvgMinMaxWaitTimeByFloor;
import elevatorentities.Building;

public class TimeManager{
	private static TimeManager timeManagerInstance;
	private long currentTimeMillis;

	private TimeManager() {

	}

	public static TimeManager getInstance() {
		if (timeManagerInstance == null) {
			timeManagerInstance = new TimeManager();
		}
		return timeManagerInstance;
	}

	public long getCurrentTimeMillis() {
		return currentTimeMillis;

	}

	
	/*this method runs the process for specified duration.*/
	public void runtime() throws Exception {
		/*
		 * Elevator functions for 15 minutes, below is the initialization of the
		 * duration
		 */
		long elevatorProcessDuration = 900000;

		long startTimeOfProcess = 0;

		while (currentTimeMillis - startTimeOfProcess <= elevatorProcessDuration) {
			//calls method to create the person request
			ProcessElevatorRequestesAccToPersonRequest();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();

			}

			currentTimeMillis += 4000;

		}

		generateWaitTimeByFloor();

		System.exit(0);

	}

	/*this method is used to generate the Avg/Max/Min wait time by floor.*/
	private void generateWaitTimeByFloor() {
		int max = 0;
		int min = 0;
		int avg = 0;
		Map<Integer, AvgMinMaxWaitTimeByFloor> waitTimeByFloor = controller.ElevatorControllerSimulator.getInstance()
				.getWaitTimeByFloor();
		System.out.printf("%10s %30s %20s %5s", " Start Floor ", " Average Wait Time ", " Min Wait Time",
				" Max Wait Time");
		System.out.println();
		for (Integer key : waitTimeByFloor.keySet()) {
			AvgMinMaxWaitTimeByFloor avgMinMaxWaitTimeByFloor = waitTimeByFloor.get(key);
			List<Integer> waitTime = avgMinMaxWaitTimeByFloor.getWaitTime();
			if (waitTime.size() > 0) {
				max = Collections.max(waitTime);
				min = Collections.min(waitTime);
				avg = findAvg(waitTime);
			}

			System.out.format("%10s %30s %20s %5s", "Floor " + key, avg + "seconds", min + "seconds", max + "seconds");
			System.out.println();
		}
	}

	/*this method is used to find the average of integers in the arraylist.*/
	private int findAvg(List<Integer> waitTime) {
		Integer sum = 0;
		if (!waitTime.isEmpty()) {
			for (Integer wait : waitTime) {
				sum += wait;
			}
			return sum / waitTime.size();
		}
		return sum;
	}

	/*this method creates a person object, every 12 seconds.*/
	private void ProcessElevatorRequestesAccToPersonRequest() throws Exception {

		// Each minute 5 persons requests are raised
		if (currentTimeMillis % 12000 == 0) {
			Building.getInstance().createPersonRequest();
		}
	}
}
