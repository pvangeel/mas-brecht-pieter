package layer.physical.entities;

import layer.physical.events.GradientFieldCreatedEvent;
import layer.physical.events.GradientFieldDestroyedEvent;
import framework.core.VirtualClock;
import framework.events.EventBroker;
import framework.layer.agent.Agent;
import framework.utils.Utils;

/**
 * This agent is a hack. shouldn't be like this.. it can receive direct communication from the world, but actually it 
 * should receive communication just through its sensors and devices
 * @author marioct
 *
 */
public class GradientFieldPDPPackage extends PDPPackage {

	public GradientFieldPDPPackage(int id, Crossroads origin, Crossroads destination, double weigth) {
		super(id, origin, destination, weigth);
	}

	public long getRadius() {
		return lastRadius;
	}

	private boolean hasGradientField = false;
	private boolean action1 = false;
	private boolean action0 = false;
	private long delta = Utils.secondsToMicroSeconds(900);
	private long tLastGFIncrease = 0;
	private long lastRadius = Utils.metersToMillimeter(10);
	private int INCREASE_RADIUS_BY = 50;
	
	public void setGradientField() {
		hasGradientField = true;
	}
	
	@Override
	public void processTick(long timePassed) {
		if(hasGradientField == false && isPackageAdded()) {
			EventBroker.getEventBroker().notifyAll(new GradientFieldCreatedEvent(getId(), getOrigin().getPosition(), lastRadius));
			setGradientField();
			action0 = true;
		}
		
		if(action0 && isPackagePicked() && ! action1) {
			EventBroker.getEventBroker().notifyAll(new GradientFieldDestroyedEvent(getId()));
			action1  = true;
		}
		if(action0 && !action1){ //increase size of GF
			if(tLastGFIncrease + delta <= VirtualClock.currentTime()) {
				tLastGFIncrease = VirtualClock.currentTime();
				lastRadius += Utils.metersToMillimeter(INCREASE_RADIUS_BY);
				
				EventBroker.getEventBroker().notifyAll(new GradientFieldDestroyedEvent(getId()));
				EventBroker.getEventBroker().notifyAll(new GradientFieldCreatedEvent(getId(), getOrigin().getPosition(), lastRadius ));
				
			}
		}
	}

}
