package framework.initialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import framework.core.SimulationCore;
import framework.instructions.InstructionManager;
import framework.layer.agent.AgentStructure;
import framework.layer.deployment.DeploymentStructure;
import framework.layer.physical.PhysicalStructure;
import framework.utils.Dispatcher;

/**
 * 
 * Receives ticks from the simulation core and dispatches these to all the active directors
 * 
 * @author Bart Tuts and Jelle Van Gompel 
 */

public class InitializationDispatcher<P extends PhysicalStructure<?>> extends Dispatcher {

	private final List<InitializationDirector<P>> directors = new ArrayList<InitializationDirector<P>>();

	private final SimulationCore<P> simulationCore;

	public InitializationDispatcher(SimulationCore<P> simulationCore) {
		super(1);
		if (simulationCore == null) {
			throw new IllegalArgumentException();
		}
		this.simulationCore = simulationCore;
	}

	public void addDirector(InitializationDirector<P> director) {
		if (director == null) {
			throw new IllegalArgumentException();
		}
		directors.add(director);
		director.setDispatcher(this);
	}

	public void removeDirector(InitializationDirector<P> director) {
		if (director == null) {
			throw new IllegalArgumentException();
		}
		if (!director.getDispatcher().equals(this)) {
			throw new IllegalStateException();
		}
		directors.remove(director);
		director.setDispatcher(null);
	}

	@Override
	protected void processTick() {
		for (InitializationDirector<P> director : new ArrayList<InitializationDirector<P>>(directors)) {
			director.processTick();
		}
	}
	
	public List<InitializationDirector<P>> getDirectors() {
		return Collections.unmodifiableList(directors);
	}

	public AgentStructure getAgentStructure() {
		return simulationCore.getAgentLayer().getAgentStructure();
	}

	public DeploymentStructure getDeploymentStructure() {
		return simulationCore.getDeploymentLayer().getDeploymentStructure();
	}

	public P getPhysicalStructure() {
		return simulationCore.getPhysicalLayer().getPhysicalStructure();
	}

	public InstructionManager<P> getInstructionManager() {
		return simulationCore.getInstructionManager();
	}
}
