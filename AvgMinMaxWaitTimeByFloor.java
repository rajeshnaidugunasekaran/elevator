package elevatorentities;

import java.util.ArrayList;
import java.util.List;

/*This class contains all the parameters to be displayed as columns into the table*/

/*this class also implements single responsibility principle*/
public class AvgMinMaxWaitTimeByFloor {

	private String start_Floor;
	private String average_wait_time;
	private String min_wait_time;
	private String max_wait_time;
	private List<Integer> waitTime = new ArrayList<Integer>();

	// Default Constructor
	public AvgMinMaxWaitTimeByFloor() {
		super();
	}

	// Parameterized Constructor
	public AvgMinMaxWaitTimeByFloor(String start_Floor, String average_wait_time, String min_wait_time,
			String max_wait_time) {
		super();
		this.start_Floor = start_Floor;
		this.average_wait_time = average_wait_time;
		this.min_wait_time = min_wait_time;
		this.max_wait_time = max_wait_time;
	}

	public String getStart_Floor() {
		return start_Floor;
	}

	public void setStart_Floor(String start_Floor) {
		this.start_Floor = start_Floor;
	}

	public String getAverage_wait_time() {
		return average_wait_time;
	}

	public void setAverage_wait_time(String average_wait_time) {
		this.average_wait_time = average_wait_time;
	}

	public String getMin_wait_time() {
		return min_wait_time;
	}

	public void setMin_wait_time(String min_wait_time) {
		this.min_wait_time = min_wait_time;
	}

	public String getMax_wait_time() {
		return max_wait_time;
	}

	public void setMax_wait_time(String max_wait_time) {
		this.max_wait_time = max_wait_time;
	}

	public List<Integer> getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(List<Integer> waitTime) {
		this.waitTime = waitTime;
	}

}
