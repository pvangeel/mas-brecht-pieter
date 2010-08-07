package ants;

import java.util.LinkedList;
import java.util.Set;

import framework.layer.agent.Agent;

import layer.agent.entities.DelegateMASDeliveryAgent;
import layer.devices.Trajectory;
import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

public class ExplorationAnt {

	private Trajectory route = new Trajectory();
	private final PDPPackage pdpPackage;
	long hops = 0;
	private final long maxHops;
	
	
	public ExplorationAnt(PDPPackage pdpPackage, long maxHops) {
		this.pdpPackage = pdpPackage;
		this.maxHops = maxHops;
	}

	public void explore(Crossroads from) {
		
		LinkedList<Truck> trucksOnCrossroads = from.getOnroadEntities();
		trucksOnCrossroads.addAll(from.getOffroadEntities());
		
		if(trucksOnCrossroads.size() >0) {
			for (Truck truck : trucksOnCrossroads) {
				Set<Agent> agents = truck.getAgentsOnAttachDevices();
				//System.out.println("aantal agents voor truck on cross:" + agents.size() );
				DelegateMASDeliveryAgent vehicleAgent = (DelegateMASDeliveryAgent) agents.iterator().next();
				
				vehicleAgent.giveRoute(route);
				
			}
		}
		
		System.out.println("exploring shizzle");
		if(hops > maxHops)
			return;
		try {
			route.addCrossroads(from);
			hops++;
		} catch (LoopException e) {
			return;
		}
		
		Set<Road> outgoingConnections = from.getOutgoingConnections();
		
		for (Road road : outgoingConnections) {
			Set<Truck> trucksOnRoad = getTrucksOnRoad(road);
			if(trucksOnRoad.isEmpty()) {
				Crossroads otherConnector = road.getOtherConnector(from);
				explore(otherConnector);
			} else {
				for (Truck truck : trucksOnRoad) {
					//truck.announcePackage(pdpPackage, route);
					Set<Agent> agents = truck.getAgentsOnAttachDevices();
					//System.out.println("aantal agents voor truck:" + agents.size() );
					
				}
			}
			
		}
		
	}

	private Set<Truck> getTrucksOnRoad(Road road) {
		Set<Truck> trucks = road.getEntitiesTo1();
		trucks.addAll(road.getEntitiesTo2());
		
		return trucks;
	}

}
