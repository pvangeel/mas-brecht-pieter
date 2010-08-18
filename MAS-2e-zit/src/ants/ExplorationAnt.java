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

	private Trajectory route;
	private final PDPPackage pdpPackage;
	private long hopsToDo;


	public ExplorationAnt(PDPPackage pdpPackage, long hopsToDo) {
		this.pdpPackage = pdpPackage;
		this.hopsToDo = hopsToDo;
		this.route = new Trajectory(pdpPackage);
	}
	
	private ExplorationAnt(PDPPackage pdpPackage, long hopsToDo, Trajectory route) {
		this(pdpPackage, hopsToDo);
		this.route = new Trajectory(route, pdpPackage);
	}
	
	private DelegateMASDeliveryAgent extractAgent(Set<Agent> agents){
		DelegateMASDeliveryAgent vehicleAgent = (DelegateMASDeliveryAgent) agents.iterator().next();
		return vehicleAgent;
	}

	public void explore(Crossroads from) {

		LinkedList<Truck> trucksOnCrossroads = from.getOnroadEntities();
		trucksOnCrossroads.addAll(from.getOffroadEntities());

		if(trucksOnCrossroads.size() > 0) {
			for (Truck truck : trucksOnCrossroads) {
				DelegateMASDeliveryAgent vehicleAgent = extractAgent(truck.getAgentsOnAttachDevices());
				vehicleAgent.suggestRoute(route);
			}
		}

		if(hopsToDo == 0)
			return;
		try {
			route.addCrossroads(from);
		} catch (LoopException e) {
			return;
		}

		Set<Road> outgoingConnections = from.getOutgoingConnections();

		for (Road road : outgoingConnections) {
			Set<Truck> trucksOnRoad = getTrucksOnRoad(road);
			Crossroads otherConnector = road.getOtherConnector(from);

			for (Truck truck : trucksOnRoad) {
				DelegateMASDeliveryAgent vehicleAgent = extractAgent(truck.getAgentsOnAttachDevices());
				vehicleAgent.suggestRoute(route);
			}

			new ExplorationAnt(pdpPackage, --hopsToDo, route).explore(otherConnector);
		}

	}

	private Set<Truck> getTrucksOnRoad(Road road) {
		Set<Truck> trucks = road.getEntitiesTo1();
		trucks.addAll(road.getEntitiesTo2());

		return trucks;
	}

}
