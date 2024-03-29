package configuration.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import configuration.directors.DelegateMASPDPPackagesDirector;
import configuration.directors.DelegateMASVehiclesInitializationDirector;
import configuration.directors.GradientAgentsInitializationDirector;
import configuration.directors.GradientFieldPDPPackagesDirector;
import configuration.directors.OSMDirector;
import configuration.directors.RegularGridDirector;
import framework.experiment.ExperimentBuilder;
import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;
import framework.utils.Utils;

public class GradientFieldPDPBuilder extends ExperimentBuilder<PhysicalConnectionStructure<Truck, Crossroads, Road>>{

	private File osmMapFile;
	private int width;
	private int numberOfAgents;
	private boolean regularGrid;
	private PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>> physicalLayer;

	public GradientFieldPDPBuilder(File osmMapFile) {
		if(osmMapFile == null)
			throw new IllegalArgumentException("Should be a file.");
		this.osmMapFile = osmMapFile;
	}
	
	public GradientFieldPDPBuilder(int width, int numberOfAgents) {
		this.width = width;
		this.numberOfAgents = numberOfAgents;
		regularGrid = true;
	}
	
	@Override
	public void createExtraListeners(int simulationRun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> getInitializers() {
		List<InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>>> initializers = new ArrayList<InitializationDirector<PhysicalConnectionStructure<Truck,Crossroads,Road>>>();
		
		
		
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> roadInfrastructure;
		if(regularGrid) {
			roadInfrastructure = new RegularGridDirector(width, physicalLayer);
		} else {
			roadInfrastructure = new OSMDirector(osmMapFile);
		}
		
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> vehicles = new DelegateMASVehiclesInitializationDirector(numberOfAgents, width);
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> agents = new GradientAgentsInitializationDirector(numberOfAgents);
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> packages = new GradientFieldPDPPackagesDirector(width);
		
		
		
		
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
		physicalLayer = new PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>>(
				new PhysicalConnectionStructure<Truck, Crossroads, Road>());
		return physicalLayer;
	}

	@Override
	public long getSimulationTime() {
		int simulationTimeInMinutes = 60 * 24 * 3; // 5 days
		return Utils.minutesToMicroSeconds(simulationTimeInMinutes);
	}

}
