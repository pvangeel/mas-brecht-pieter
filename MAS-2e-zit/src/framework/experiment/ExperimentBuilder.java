package framework.experiment;

import java.util.HashMap;
import java.util.List;

import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalLayer;
import framework.layer.physical.PhysicalStructure;

/**
 * 
 * An experimentBuilder sets up an experiment using a set of initializers
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public abstract class ExperimentBuilder<S extends PhysicalStructure<?>> {

	public abstract long getSimulationTime();

	public abstract long getNotificationInterval();

	public abstract PhysicalLayer<S> getPhysicalcalLayer();

	public abstract List<InitializationDirector<S>> getInitializers();

	public abstract void createExtraListeners(int simulationRun);

	public abstract HashMap<String, Object> getParameters();
	
	

}
