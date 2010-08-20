package layer.physical.entities;

import ants.ExplorationAnt;

import layer.agent.entities.DelegateMASDeliveryAgent;
import layer.devices.Trajectory;
import framework.core.VirtualClock;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.Resource;
import framework.layer.physical.position.Position;
import framework.utils.Utils;

/**
 * This agent is a hack. shouldn't be like this.. it can receive direct communication from the world, but actually it 
 * should receive communication just through its sensors and devices
 * @author marioct
 *
 */
public class PDPPackage extends Resource<PDPPackage> {

	private Crossroads origin;
	private Crossroads destination;
	private double weight;
	private int id;
	
	private long packagePriority = 0;
	private int timesSentAnts = 0;
	private int tresholdToIncreasePriority = 500;
	
	private long lastAntsSent = 0; //VirtualClock.currentTime();
	private long delta = Utils.minutesToMicroSeconds(1);
	//TODO: evt aanpassen als package gereserveerd is
	private int currentHops = 20;
	private static final int maxHOPS = 20;
	private int nbAnts = 20;
	private final long timeCreated = VirtualClock.currentTime();

	public PDPPackage(int id, Crossroads origin, Crossroads destination, double weight) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.weight = weight;
	}
	
	public Crossroads getDestination() {
		return destination;
	}
	
	public Crossroads getOrigin() {
		return origin;
	}
	
	/**
	 * Creates a DTO representation of this PDPPackage
	 * @return
	 */
	public PDPPackageDTO getDTO() {
		return new PDPPackageDTO(origin, destination, weight, this);
	}

	public int getId() {
		return id;
	}

	private boolean packagePicked = false;
	
	public void packagePicked() {
		packagePicked = true;
	}
	
	@Override
	public void processTick(long timePassed) {
		if(!packagePicked){
			confirmTruck();
			sendExplorationAnts();
		}
	}
	
	
	private void confirmTruck() {
		if(currentTruck != null){
			if(!currentTruck.confirmTruck(this)){
				currentTruck = null;
			}
		}
	}
	
	private DelegateMASDeliveryAgent currentTruck;
	private Trajectory currentRouteToThis;

	
	
	public boolean reserve(DelegateMASDeliveryAgent truckAgent, Trajectory route){
		if(currentTruck == null){
			currentTruck = truckAgent;
			currentRouteToThis = route;
			return true;
		}else{
			if(better(route)){
				currentTruck = truckAgent;
				currentRouteToThis = route;
				return true;
			}else
				return false;
		}
	}
	
	
	
	private boolean better(Trajectory newRoute) {
		Trajectory routeFromCurrentTruck = currentTruck.getCurrentRoute();
		/*
		 * Als de truck die onderweg is naar dit pakje ondertussen een beter pakje heeft gevonden maar nog steeds
		 * in deze package geregistreerd staat als currentTruck (confirmTruck is nog niet gebeurd, gebeurt bij volgende tick)
		 */
		if(routeFromCurrentTruck != currentRouteToThis)
			return true;
		
		return newRoute.isBetter(currentRouteToThis);
	}

	public long getPackagePriority(){
		return packagePriority;
	}

	private void sendExplorationAnts() {
		if(lastAntsSent + delta < VirtualClock.currentTime()) {
			for (int i = 0; i < nbAnts; i++) {
				ExplorationAnt ant = new ExplorationAnt(this, Math.min(currentHops, maxHOPS));
				ant.explore(origin);
				//TODO: dubbels vermijden?
			}
			currentHops++;
			timesSentAnts++;
			if(timesSentAnts % tresholdToIncreasePriority == 0) packagePriority++;
			lastAntsSent = VirtualClock.currentTime();
		}
	}
	

	@Override
	public Position getPosition() {
		//TODO:if pickedUp: Truck location
		if(!packagePicked)
			return origin.getPosition();
		return null;
	}

	@Override
	protected Command<? extends PDPPackage> loadFailSafeCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
