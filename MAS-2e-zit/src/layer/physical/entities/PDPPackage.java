package layer.physical.entities;

import java.util.Set;

import ants.ExplorationAnt;

import layer.physical.events.GradientFieldCreatedEvent;
import layer.physical.events.GradientFieldDestroyedEvent;
import framework.core.SimulationCore;
import framework.core.VirtualClock;
import framework.events.EventBroker;
import framework.layer.agent.Agent;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.PhysicalEntity;
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

	public PDPPackage(int id, Crossroads origin, Crossroads destination, double weigth) {
		this.id = id;
		this.origin = origin;
		this.destination = destination;
		this.weight = weigth;
	}

	public Crossroads getDestination() {
		return destination;
	}
	
	public Crossroads getOrigin() {
		return origin;
	}
	
//	public long getRadius() {
//		return lastRadius;
//	}

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

//	private boolean hasGradientField = false;
	private boolean packageAdded = false;
	private boolean packagedPicked = false;
	private boolean packageDelivered = false;
	private boolean action1 = false;
	private boolean action0 = false;
	private long delta = Utils.secondsToMicroSeconds(900);
//	private long tLastGFIncrease = 0;
//	private long lastRadius = Utils.metersToMillimeter(10);
//	private int INCREASE_RADIUS_BY = 50;
	
	public void packageAdded() {
		packageAdded = true;
	}
	
	public void packagePicked() {
		packagedPicked = true;
	}
	
	public void packageDelivered() {
		packageDelivered = true;
	}
	
//	public void setGradientField() {
//		hasGradientField = true;
//	}
	
	@Override
	public void processTick(long timePassed) {
//		if(hasGradientField == false && packageAdded) {
//			EventBroker.getEventBroker().notifyAll(new GradientFieldCreatedEvent(id, origin.getPosition(), lastRadius));
//			setGradientField();
//			action0 = true;
//		}
//		
//		if(action0 && packagedPicked && ! action1) {
//			EventBroker.getEventBroker().notifyAll(new GradientFieldDestroyedEvent(id));
//			action1  = true;
//		}
//		if(action0 && !action1){ //increase size of GF
//			if(tLastGFIncrease + delta <= VirtualClock.currentTime()) {
//				tLastGFIncrease = VirtualClock.currentTime();
//				lastRadius += Utils.metersToMillimeter(INCREASE_RADIUS_BY);
//				
//				EventBroker.getEventBroker().notifyAll(new GradientFieldDestroyedEvent(id));
//				EventBroker.getEventBroker().notifyAll(new GradientFieldCreatedEvent(id, origin.getPosition(), lastRadius ));
//				
//			}
//		}
		
		if(lastAntsSent + delta < VirtualClock.currentTime()) {   
			sendExplorationAnts();
			lastAntsSent = VirtualClock.currentTime();
		}
		
	}
	
	private void sendExplorationAnts() {
		
			ExplorationAnt ant = new ExplorationAnt(this, 20);
			ant.explore(origin);
		
		
		
		// TODO Auto-generated method stub
		
	}

	private long packagePriority=1;
	private long lastAntsSent=VirtualClock.currentTime();
	

	@Override
	public Position getPosition() {
		// TODO Auto-generated method stub
		//if pickedUp: Truck location, else origin.
		
		return null;
	}

	@Override
	protected Command<? extends PDPPackage> loadFailSafeCommand() {
		// TODO Auto-generated method stub
		return null;
	}

}
