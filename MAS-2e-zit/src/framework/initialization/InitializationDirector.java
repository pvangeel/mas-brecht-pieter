package framework.initialization;

import framework.core.VirtualClock;
import framework.initialization.spatial.SpatialPattern;
import framework.instructions.InstructionManager;
import framework.layer.agent.AgentStructure;
import framework.layer.deployment.DeploymentStructure;
import framework.layer.physical.PhysicalStructure;

/**
 * 
 * A director directs the creation and deployment of entities in the simulation,
 * according to a given time pattern. Any number of directors can be active
 * simultaneously (see InitializationDispatcher)
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public abstract class InitializationDirector<P extends PhysicalStructure<?>> {

	private InitializationDispatcher<P> dispatcher;

	private final TimePattern timePattern;

	private final SpatialPattern<P, ?> spatialPattern;

	private long nextTime;

	public InitializationDirector(TimePattern timePattern) {
		if (timePattern == null) {
			throw new IllegalArgumentException();
		}
		this.timePattern = timePattern;
		this.spatialPattern = null;
		trySetNextTime();
	}

	public InitializationDirector(TimePattern timePattern, SpatialPattern<P, ?> spatialPattern) {
		if (timePattern == null) {
			throw new IllegalArgumentException();
		}
		if (spatialPattern == null) {
			throw new IllegalArgumentException();
		}
		this.timePattern = timePattern;
		this.spatialPattern = spatialPattern;
		trySetNextTime();
	}

	public void setDispatcher(InitializationDispatcher<P> dispatcher) {
		if (this.dispatcher == null && dispatcher == null) {
			throw new IllegalStateException();
		}
		if (this.dispatcher != null && dispatcher != null) {
			throw new IllegalStateException();
		}
		this.dispatcher = dispatcher;
	}

	public InitializationDispatcher<P> getDispatcher() {
		if (dispatcher == null) {
			throw new IllegalStateException();
		}
		return dispatcher;
	}

	private void trySetNextTime() {
		if (!timePattern.hasNextTime()) {
			getDispatcher().removeDirector(this);
			nextTime = Long.MAX_VALUE;
		} else {
			nextTime = timePattern.getNextTime();
		}
	}

	public void processTick() {
		while (VirtualClock.getClock().getCurrentTime() > nextTime) {
			createAndDeploy();
			trySetNextTime();
		}
	}

	protected abstract void createAndDeploy();

	public TimePattern getTimePattern() {
		return timePattern;
	}

	public SpatialPattern<P, ?> getSpatialPattern() {
		if(spatialPattern == null) {
			throw new IllegalStateException();
		}
		return spatialPattern;
	}
	
	public boolean hasSpatialPattern(){
		return spatialPattern != null;
	}

	public AgentStructure getAgentStructure() {
		return dispatcher.getAgentStructure();
	}

	public DeploymentStructure getDeploymentStructure() {
		return dispatcher.getDeploymentStructure();
	}

	public P getPhysicalStructure() {
		return dispatcher.getPhysicalStructure();
	}

	public InstructionManager<P> getInstructionManager() {
		return dispatcher.getInstructionManager();
	}

	public void updateNextValue() {
		trySetNextTime();
	}

}
