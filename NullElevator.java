package nullobject;

import constants.ElevatorConstants.DIRECTION;
import elevatorentities.Person;

/*this class implements Null Object design pattern*/
public class NullElevator extends AbstractElevator {

	@Override
	public Double getRequestCandidateFactor(int startFloor,int destFloor, DIRECTION destDirection) throws Exception {
		return null;
	}

	@Override
	public Double getMovingDirectionUltimateDoubleDistance(Integer ultimateStop) {
		return null;
	}

	@Override
	public Double getMovingDistance(int destFloor) {
		return null;
	}

	@Override
	public Double getStaticDistance(int destFloor) {
		return null;
	}

	@Override
	public boolean underMaintenance() {
		return false;
	}

	@Override
	public boolean isNil() {
		return false;
	}

}
