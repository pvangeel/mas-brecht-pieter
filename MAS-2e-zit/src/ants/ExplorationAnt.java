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
		sendAntToNextHop(from);

	}

	private void sendAntToNextHop(Crossroads from) {
		Set<Road> outgoingConnections = new HashSet<Road>(from.getOutgoingConnections());
		
		removeRoadWhereCameFrom(outgoingConnections, from);
		
		Crossroads otherConnector = chooseNextCrossroadsRandom(from, outgoingConnections);
		
//		Set<Truck> trucksOnRoad = getTrucksOnRoad(road);

//		for (Truck truck : trucksOnRoad) {
//			DelegateMASDeliveryAgent vehicleAgent = extractAgent(truck);
//			//				System.out.println("onRoad:"+road.getId());
//			vehicleAgent.suggestRoute(route);
//		}
		
		hopsToDo = hopsToDo - 1;
		new ExplorationAnt(pdpPackage, hopsToDo, route).explore(otherConnector);
	}

	private Crossroads chooseNextCrossroadsRandom(Crossroads from,
			Set<Road> outgoingConnections) {
		int randomRoad = (int) Math.floor(outgoingConnections.size() * Math.random());
		Road road = outgoingConnections.toArray(new Road[outgoingConnections.size()])[randomRoad];
		Crossroads otherConnector = road.getOtherConnector(from);
		return otherConnector;
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
		from.suggestRoute(route);
	}

//	private Set<Truck> getTrucksOnRoad(Road road) {
//		Set<Truck> trucks = new HashSet<Truck>();
//		trucks.addAll(road.getEntitiesTo1());
//		trucks.addAll(road.getEntitiesTo2());
//
//		return trucks;
//	}

}
