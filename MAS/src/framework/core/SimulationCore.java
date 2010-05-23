package framework.core;

import java.util.ArrayList;
import java.util.List;

import framework.events.EventBroker;
import framework.events.TickUpdateEvent;
import framework.initialization.InitializationDirector;
import framework.initialization.InitializationDispatcher;
import framework.instructions.InstructionManager;
import framework.layer.agent.AgentLayer;
import framework.layer.deployment.DeploymentLayer;
import framework.layer.physical.PhysicalLayer;
import framework.layer.physical.PhysicalStructure;

/**
 * The simulatoin core is a singleton instance that - holds the various layer
 * instances and serves as a global access point to them - initializes the
 * various dispatchers and serves as a global access point to them - starts,
 * stops, pauses and resumes the simulation - runs the simulation loop
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class SimulationCore<P extends PhysicalStructure<?>> {

	// number of ticks, the arbitrary time step after which the simulation
	// layers may be updated
	private long ticks = 0;

	private final int microsecondsPerTick = 200;

	// A dispatcher that allows automatic initialization of entities
	private final InitializationDispatcher<P> initializationDispatcher;

	// Manager that records and executes issued instructions at the correct
	// moment
	private final InstructionManager<P> instructionManager;

	// the 3 layers for physical entities, deployment devices and agent software
	public final PhysicalLayer<P> physicalLayer;
	private final DeploymentLayer deploymentLayer;
	private final AgentLayer agentLayer;

	// indicates if the simulation is running
	private boolean running;

	// singleton instance of the simulation core
	public static SimulationCore<?> simulationCore;

	private final long tickNotifyInterval;
	private long ticksSinceLastNotify;

	private final long simulationTicks;

	private final List<SimulationStopListener> stopListeners = new ArrayList<SimulationStopListener>();

	public SimulationCore(long simulationTime, long tickNotifyInterval, PhysicalLayer<P> physicalLayer, DeploymentLayer deploymentLayer,
			AgentLayer agentLayer) {
		if (physicalLayer == null || deploymentLayer == null || agentLayer == null) {
			throw new IllegalArgumentException();
		}
		if (simulationCore != null) {
			throw new IllegalStateException();
		}
		this.physicalLayer = physicalLayer;
		this.deploymentLayer = deploymentLayer;
		this.agentLayer = agentLayer;
		this.tickNotifyInterval = timeToTicks(tickNotifyInterval);
		this.simulationTicks = timeToTicks(simulationTime);
		initializationDispatcher = new InitializationDispatcher<P>(this);
		instructionManager = new InstructionManager<P>(this);
		simulationCore = this;
	}

	private long timeToTicks(long time) {
		return (long) time / microsecondsPerTick;
	}

	/**
	 * return the singleton instance of the simulation core
	 */
	public static SimulationCore<?> getSimulationCore() {
		if (simulationCore == null) {
			throw new IllegalStateException();
		}
		return simulationCore;
	}

	public InstructionManager<P> getInstructionManager() {
		return instructionManager;
	}

	public long getTicks() {
		return ticks;
	}

	public InitializationDispatcher<P> getInitializationDispatcher() {
		return initializationDispatcher;
	}

	public PhysicalLayer<P> getPhysicalLayer() {
		return physicalLayer;
	}

	public DeploymentLayer getDeploymentLayer() {
		return deploymentLayer;
	}

	public AgentLayer getAgentLayer() {
		return agentLayer;
	}

	/*
	 * See startSimulationThread
	 */
	private void runSimulation() {
		if (running) {
			initializationDispatcher.notifyTickUpdate();

			instructionManager.executePendingInstructions();

			physicalLayer.getDispatcher().notifyTickUpdate();
			deploymentLayer.getDispatcher().notifyTickUpdate();
			agentLayer.getDispatcher().notifyTickUpdate();

			VirtualClock.getClock().increaseTime(microsecondsPerTick);
			ticks++;
			tryNotifyTickUpdate();
			if (ticks == simulationTicks) {
				stopSimulation();
			}
		} else {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void tryNotifyTickUpdate() {
		if (tickNotifyInterval <= 0) {
			return;
		}
		ticksSinceLastNotify++;
		if (tickNotifyInterval == ticksSinceLastNotify) {
			EventBroker.getEventBroker().notifyAll(new TickUpdateEvent());
			ticksSinceLastNotify = 0;
		}
	}

	/**
	 * The XML input/output streams are closed and the current simulation run is
	 * ended.
	 */
	public void stopSimulation() {
		running = false;
		EventBroker.getEventBroker().terminate();
		instructionManager.terminate();
		simulationCore = null;
		VirtualClock.getClock().reset();
		for (SimulationStopListener listener : stopListeners) {
			listener.onSimulationStop();
		}
	}

	/**
	 * Pause the simulation temporarily
	 */
	public void pauseSimulation() {
		running = false;
	}

	/**
	 * Resume the simulation after pausing it
	 */
	public void resumeSimulation() {
		running = true;
	}

	/**
	 * A new thread is created and the simulator loop is set to run in it.
	 * During each loop the initializationDispatcher, instructionManager and 3
	 * layers are notified, after which the tick count and time are updated.
	 */
	public void startSimulationThread() {
		running = true;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					runSimulation();
				}
			}
		};
		new Thread(runnable).start();
	}

	public void addSimulationStopListener(SimulationStopListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		stopListeners.add(listener);
	}

	public static interface SimulationStopListener {
		public void onSimulationStop();
	}

	public List<InitializationDirector<P>> getInitializationDirectors() {
		return getInitializationDispatcher().getDirectors();
	}
}