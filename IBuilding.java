package interfaces;

import java.util.List;
import java.util.Properties;

import elevatorentities.Elevator;


/*this interface implements Interface Segregation Principle.*/
public interface IBuilding {
	
	public List<Elevator> processNumberOfFloorsAndElevatorsToBuilding(int numFloors ,int  numElevators) throws Exception;
	public Properties toGetConstantsFromPropertiesFile();

}
