package nullobject;


/*this class implements Null Object design pattern*/

public abstract class AbstractElevator {
	public abstract Double getRequestCandidateFactor(int startFloor,int destFloor, constants.ElevatorConstants.DIRECTION destDirection)
			throws Exception ;
	public abstract Double getMovingDirectionUltimateDoubleDistance(Integer ultimateStop);
	public abstract Double getMovingDistance(int destFloor);
	public abstract Double getStaticDistance(int destFloor);
	public abstract boolean underMaintenance();
	public abstract boolean isNil();

}
