package framework.experiment;

import framework.core.SimulationCore;
import framework.core.SimulationCore.SimulationStopListener;
import framework.initialization.InitializationDirector;
import framework.layer.agent.AgentLayer;
import framework.layer.deployment.DeploymentLayer;
import framework.layer.physical.PhysicalStructure;

/**
 * 
 * An experiment is a set of runs using an identical setup. An experiment has an
 * experimentBuilder that sets up the experiment and has a number of runs, which is
 * the number of times this setup will be reinitialized and run. 
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class Experiment<S extends PhysicalStructure<?>> implements
		SimulationStopListener {

	private final ExperimentBuilder<S> experimentBuilder;
	private final int experimentRuns;
	private int runs = 0;

	public Experiment(int experimentRuns, ExperimentBuilder<S> experimentBuilder) {
		if (experimentRuns <= 0 || experimentBuilder == null) {
			throw new IllegalArgumentException();
		}
		this.experimentRuns = experimentRuns;
		this.experimentBuilder = experimentBuilder;
		runExperiment();
	}

	private void runExperiment() {
		runs++;
		if (runs > experimentRuns) {
			System.exit(0);
		}
		System.out.println("Experiment.runExperiment() " + runs);
		ProgramParameters.initialize(experimentBuilder.getParameters());
		SimulationCore<S> core = buildCore();
		experimentBuilder.createExtraListeners(runs);
		core.addSimulationStopListener(this);
		core.startSimulationThread();
	}

	private SimulationCore<S> buildCore() {
		SimulationCore<S> core = new SimulationCore<S>(experimentBuilder.getSimulationTime(), 
				experimentBuilder.getNotificationInterval(), 
				experimentBuilder.getPhysicalcalLayer(), 
				new DeploymentLayer(), 
				new AgentLayer());
		for (InitializationDirector<S> director : experimentBuilder.getInitializers()) {
			core.getInitializationDispatcher().addDirector(director);
		}
		return core;
	}

	@Override
	public void onSimulationStop() {
		SimulationCore.getSimulationCore().pauseSimulation();
		ProgramParameters.reset();
		runExperiment();
	}
}
