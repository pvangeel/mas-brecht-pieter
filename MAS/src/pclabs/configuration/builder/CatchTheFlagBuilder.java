package pclabs.configuration.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pclabs.configuration.directors.AgentsInitializationDirector;
import pclabs.configuration.directors.OSMDirector;
import pclabs.configuration.directors.RoadInitializationDirector;
import pclabs.configuration.directors.VehiclesInitializationDirector;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import pclabs4.configuration.directors.PDPPackagesDirector;


import framework.experiment.ExperimentBuilder;
import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;
import framework.utils.Utils;

public class CatchTheFlagBuilder extends ExperimentBuilder<PhysicalConnectionStructure<Truck, Crossroads, Road>>{

	private File osmMapFile;

	public CatchTheFlagBuilder(File osmMapFile) {
		if(osmMapFile == null)
			throw new IllegalArgumentException("Should be a file.");
		this.osmMapFile = osmMapFile;
	}
	
	@Override
	public void createExtraListeners(int simulationRun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> getInitializers() {
		List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> initializers = new ArrayList<InitializationDirector<PhysicalConnectionStructure<Truck,Crossroads,Road>>>();
		
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> roadInfrastructure = new OSMDirector(osmMapFile);
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> vehicles = new VehiclesInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> agents = new AgentsInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> packages = new PDPPackagesDirector();
		
		
		initializers.add(roadInfrastructure);
		initializers.add(vehicles);
		initializers.add(agents);
		initializers.add(packages);
		
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
