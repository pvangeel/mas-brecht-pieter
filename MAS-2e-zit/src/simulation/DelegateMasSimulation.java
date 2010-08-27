package simulation;


import configuration.builders.DelegateMASPDPBuilder;
import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.experiment.Experiment;
import framework.layer.physical.PhysicalConnectionStructure;
import gui.SimpleGUI;

public class DelegateMasSimulation {

	public DelegateMasSimulation(int numberOfAgents, int numberOfLinesInGrid, int packageInjectionRate) {
		new SimpleGUI(numberOfLinesInGrid);
		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(1, new DelegateMASPDPBuilder(numberOfLinesInGrid,numberOfAgents, packageInjectionRate));
	}

}
