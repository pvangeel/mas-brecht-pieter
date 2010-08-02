package layer.devices;

import java.util.LinkedList;
import java.util.List;

import layer.physical.entities.Crossroads;


public class Trajectory {

	private LinkedList<Crossroads> crossroads;

	public void load(List<Crossroads> result) {
		this.crossroads = new LinkedList<Crossroads>(result);
	}
	
	@SuppressWarnings("unchecked")
	public List<Crossroads> getTrajectory() {
		if(crossroads != null)
			return (List<Crossroads>) crossroads.clone();
		
		return null;
	}

	public String toString() {
		String ret = "";
		if(crossroads == null)
			return ret;
		
		for (Crossroads c: crossroads) {
			ret += c.toString() + " -> ";
		}
		
		return ret;
	}
}
