package pclabs4.layer.devices;

import java.util.LinkedList;
import java.util.List;

import pclabs.physicallayer.entities.Crossroads;

public class Trajectory {

	private LinkedList<Crossroads> crossroads;

	public void load(List<Crossroads> result) {
		this.crossroads = new LinkedList<Crossroads>(result);
	}
	
	@SuppressWarnings("unchecked")
	public List<Crossroads> getTrajectory() {
		return (List<Crossroads>) crossroads.clone();
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
