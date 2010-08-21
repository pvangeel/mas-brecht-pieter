package ants;

import java.util.HashSet;
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
	private int hopsToDo;


	public ExplorationAnt(PDPPackage pdpPackage, int hopsToDo) {
		this.pdpPackage = pdpPackage;
		this.hopsToDo = hopsToDo;
		this.route = new Trajectory(pdpPackage);
	}

	private ExplorationAnt(PDPPackage pdpPackage, int hopsToDo, Trajectory route) {
		this.pdpPackage = pdpPackage;
		this.hopsToDo = hopsToDo;
		this.route = new Trajectory(route, pdpPackage);
	}

	private DelegateMASDeliveryAgent extractAgent(Truck truck){
		Set<Agent> agents = truck.getAgentsOnAttachDevices();
		DelegateMASDeliveryAgent vehicleAgent = (DelegateMASDeliveryAgent) agents.iterator().next();
		return vehicleAgent;
	}

	public void explore(Crossroads from) {
		if(hopsToDo == 0)
			return;
		if(!route.addCrossroads(from)) return;

		exploreCrossRoads(from);
		exploreOutgoingRoads(from);

	}

	private void exploreOutgoingRoads(Crossroads from) {
		Set<Road> outgoingConnections = new HashSet<Road>(from.getOutgoingConnections());
		
		removeRoadWhereCameFrom(outgoingConnections, from);
		
		int randomRoad = (int) Math.floor(outgoingConnections.size() * Math.random());
		Road road = outgoingConnections.toArray(new Road[outgoingConnections.size()])[randomRoad];
		
		Set<Truck> trucksOnRoad = getTrucksOnRoad(road);
		Crossroads otherConnector = road.getOtherConnector(from);

		for (Truck truck : trucksOnRoad) {
			DelegateMASDeliveryAgent vehicleAgent = extractAgent(truck);
			//				System.out.println("onRoad:"+road.getId());
			vehicleAgent.suggestRoute(route);
		}
		
		hopsToDo = hopsToDo - 1;
		new ExplorationAnt(pdpPackage, hopsToDo, route).explore(otherConnector);
	}

	private void removeRoadWhereCameFrom(Set<Road> outgoingConnections, Crossroads from) {
		if(route.size() > 1){
			Crossroads cr = route.getSecondLast();
			for (Road road : outgoingConnections) {
				if(road.getOtherConnector(from) == cr){
					outgoingConnections.remove(road);
					return;
				}
			}
		}
	}

	private void exploreCrossRoads(Crossroads from) {
		LinkedList<Truck> trucksOnCrossroads = from.getOnroadEntities();
		trucksOnCrossroads.addAll(from.getOffroadEntities());

		if(trucksOnCrossroads.size() > 0) {
			for (Truck truck : trucksOnCrossroads) {
				DelegateMASDeliveryAgent vehicleAgent = extractAgent(truck);
				//				System.out.println("onCrossroad:"+from.getId());
				vehicleAgent.suggestRoute(route);
			}
		}
	}

	private Set<Truck> getTrucksOnRoad(Road road) {
		Set<Truck> trucks = new HashSet<Truck>();
		trucks.addAll(road.getEntitiesTo1());
		trucks.addAll(road.getEntitiesTo2());

		return trucks;
	}

}
