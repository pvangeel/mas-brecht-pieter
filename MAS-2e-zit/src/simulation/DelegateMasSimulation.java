package simulation;

import java.io.File;

import configuration.builders.DelegateMASPDPBuilder;
import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.experiment.Experiment;
import framework.experiment.ExperimentBuilder;
import framework.layer.physical.PhysicalConnectionStructure;
import gui.SimpleGUI;

public class DelegateMasSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SimpleGUI();
//		new MyFirstEventListener(); //creates a simple event listener
		//new PackagesCreatedEventListener();
		//new PackagesDeliveredEventListener();
		//new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(4, new GradientFieldPDPBuilder(new File("leuven.osm.xml")));
		
//		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(1, new DelegateMASPDPBuilder(new File("leuven.osm.xml")));
		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(1, new DelegateMASPDPBuilder(5,5));
		
	}

}
