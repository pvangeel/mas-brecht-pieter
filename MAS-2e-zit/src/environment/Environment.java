package environment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackageDTO;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

import framework.layer.physical.position.ConnectionElementPosition;

/**
 * This singleton is here just because the focus of the MAS-PC lab session 4 is on BDI agents, 
 * not in the simulation of the environment
 * 
 * Environment is responsible for sending the EVENTS to the EventBroker
 * @author marioct
 *
 */
public class Environment implements AgentEnvironment {
	
	private static Environment env = null;

	private Environment(){
		waitingPackages = new ArrayList<PDPPackageDTO>();
		pickedPackages = new ArrayList<PDPPackageDTO>();
		deliveredPackages = new ArrayList<PDPPackageDTO>();
	}
	
	public static Environment getInstance() {
		if(Environment.env == null) {
			Environment.env = new Environment();
		}
		
		return Environment.env;
	}
	
	public static AgentEnvironment getAgentEnvInstance() {
		return Environment.getInstance();
	}

	private ArrayList<PDPPackageDTO> deliveredPackages;
	private ArrayList<PDPPackageDTO> pickedPackages;
	private ArrayList<PDPPackageDTO> waitingPackages;

	@SuppressWarnings("unchecked")
	@Override
	synchronized public List<PDPPackageDTO> searchPackages() {
		return (List<PDPPackageDTO>) waitingPackages.clone();
	}
	
	
	synchronized public void pickPackage(PDPPackageDTO p) {
		waitingPackages.remove(p);
		pickedPackages.add(p);
	}
	
	
	synchronized public void deliverPackage(PDPPackageDTO p) {
		pickedPackages.remove(p);
		deliveredPackages.add(p);
	}
	
	synchronized public void  addPackage(PDPPackageDTO p ) {
		waitingPackages.add(p);
		
//		EventBroker.getEventBroker().notifyAll(new )
	}

	/**
	 * Utility method that implements the entire Gradient Field logic. It receives the position of the truck and
	 * returns the strongest available field for this truck.
	 * 
	 * TODO: change the POWER of the fields, currently the fields have continuous power
	 */
	@Override
	public Crossroads getStrongestFieldOrigin(ConnectionElementPosition<Truck, Crossroads, Road> position) {
		LinkedList<PDPPackageDTO> candidatePackages = new LinkedList<PDPPackageDTO>();
		TreeMap<Long, PDPPackageDTO> candidatesMap = new TreeMap<Long, PDPPackageDTO>();
		
		for(PDPPackageDTO p: waitingPackages) {
			long distance = p.getOrigin().getPosition().getDistanceTo(position);
			//long radius = p.getRadius();
//			if(distance < radius) {
//				candidatePackages.add(p);
//				candidatesMap.put(radius-distance, p);
//			}
		}
		
//		if(candidatePackages.size() > 0)
//			return candidatePackages.getFirst().getOrigin();
		if(candidatesMap.size() > 0)
			return candidatesMap.firstEntry().getValue().getOrigin();
		else
			return null;
	}
	
}
