package pclabs4.environment;

import java.util.ArrayList;
import java.util.List;

import pclabs.physicallayer.entities.PDPPackageDTO;

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
	}
	
}
