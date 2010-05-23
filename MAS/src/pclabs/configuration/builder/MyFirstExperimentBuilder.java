package pclabs.configuration.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pclabs.configuration.directors.AgentsInitializationDirector;
import pclabs.configuration.directors.RoadInitializationDirector;
import pclabs.configuration.directors.VehiclesInitializationDirector;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;


import framework.experiment.ExperimentBuilder;
import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;
import framework.utils.Utils;

public class MyFirstExperimentBuilder extends ExperimentBuilder<PhysicalConnectionStructure<Truck, Crossroads, Road>>{

	@Override
	public void createExtraListeners(int simulationRun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> getInitializers() {
		List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> initializers = new ArrayList<InitializationDirector<PhysicalConnectionStructure<Truck,Crossroads,Road>>>();
		
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> roadInfrastructure = new RoadInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> vehicles = new VehiclesInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> agents = new AgentsInitializationDirector();
		
		
		initializers.add(roadInfrastructure);
		initializers.add(vehicles);
		initializers.add(agents);
		
		return initializers;
	}

	@Override
	public long getNotificationInterval() {
		return Utils.secondsToMicroSeconds(1);
	}

	@Override
	public HashMap<String, Object> getParameters() {
		return new HashMap<String, Object>();
	}

	@Override
	public PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>> getPhysicalcalLayer() {
		return new PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>>(
				new PhysicalConnectionStructure<Truck, Crossroads, Road>());
	}

	@Override
	public long getSimulationTime() {
		int simulationTimeInMinutes = 60 * 24 * 5; // 5 days
		return Utils.minutesToMicroSeconds(simulationTimeInMinutes);
	}

}
