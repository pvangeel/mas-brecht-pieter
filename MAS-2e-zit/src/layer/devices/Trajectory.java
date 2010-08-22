package layer.devices;

import java.util.LinkedList;
import java.util.List;

import ants.LoopException;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;


public class Trajectory implements Comparable<Trajectory> {

	private LinkedList<Crossroads> crossroads = new LinkedList<Crossroads>();
	
	/*
	 * Deze package wordt opgehaald via deze weg
	 */
	private PDPPackage pdpPackage;
	
	public PDPPackage getPdpPackage() {
		return pdpPackage;
	}

	public Trajectory(Trajectory route, PDPPackage pdpPackage) {
		this(pdpPackage);
		crossroads = (LinkedList<Crossroads>) route.crossroads.clone();
	}
	
	public Trajectory(Trajectory route){
		this(route, route.getPdpPackage());
	}

	public Trajectory(PDPPackage pdpPackage) {
		this.pdpPackage = pdpPackage;
	}

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
		String ret = "T:";
		if(crossroads == null)
			return ret;
		
		for (Crossroads c: crossroads) {
			ret += c.toString() + " -> ";
		}
		
		return ret;
	}
	
	public boolean addCrossroads(Crossroads crossroads) {
		if(this.crossroads.contains(crossroads)) return false;
		this.crossroads.addFirst(crossroads);
		return true;
	}
	
	public Crossroads getLast() {
		return this.crossroads.getLast();
	}

	public boolean isBetter(Trajectory other) {
		int compare = compareTo(other);
		if(compare == -1) return true;
		return false;
	}

	private long getLength() {
		long distance = 0;
		for(int i = 0; i < crossroads.size() - 1; i++){
			distance += crossroads.get(i).distanceTo(crossroads.get(i + 1));
		}
		return distance;
		//TODO: testen!
	}

	@Override
	public int compareTo(Trajectory o) {
		if(getPdpPackage().getPackagePriority() > o.getPdpPackage().getPackagePriority()) return -1;
		if(getPdpPackage().getPackagePriority() < o.getPdpPackage().getPackagePriority()) return 1;
		
		if(getLength() < o.getLength()) return -1;
		if(getLength() > o.getLength()) return 1;
		return 0;
		//TODO: testen!
	}

	public int size() {
		return crossroads.size();
	}

	public Crossroads getAndRemoveFirst() {
		return crossroads.remove();
	}

	public Crossroads getSecondLast() {
		return crossroads.get(crossroads.size() - 2);
	}

	
}
