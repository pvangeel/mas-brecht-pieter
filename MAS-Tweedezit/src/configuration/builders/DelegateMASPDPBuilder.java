package configuration.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import configuration.directors.DelegateMASAgentsInitializationDirector;
import configuration.directors.DelegateMASPDPPackagesDirector;
import configuration.directors.DelegateMASVehiclesInitializationDirector;
import configuration.directors.OSMDirector;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.experiment.ExperimentBuilder;
import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;
import framework.utils.Utils;

public class DelegateMASPDPBuilder extends ExperimentBuilder<PhysicalConnectionStructure<Truck, Crossroads, Road>>{

	private File osmMapFile;

	public DelegateMASPDPBuilder(File osmMapFile) {
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
		
		
		//TODO: Regular grid ipv map?
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> roadInfrastructure = new OSMDirector(osmMapFile);
		
//		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> vehicles = new GradientVehiclesInitializationDirector();
//		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> agents = new GradientAgentsInitializationDirector();
//		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> packages = new GradientPDPPackagesDirector();
		
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> vehicles = new DelegateMASVehiclesInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> agents = new DelegateMASAgentsInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> packages = new DelegateMASPDPPackagesDirector();
		
		
		
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
		int simulationTimeInMinutes = 60 * 24 * 50; // 5 days
		return Utils.minutesToMicroSeconds(simulationTimeInMinutes);
	}

}
