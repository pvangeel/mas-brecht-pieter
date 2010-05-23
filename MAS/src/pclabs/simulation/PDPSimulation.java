package pclabs.simulation;

import java.io.File;

import pclabs.configuration.builder.CatchTheFlagBuilder;
import pclabs.gui.SimpleGUI;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import framework.experiment.Experiment;
import framework.layer.physical.PhysicalConnectionStructure;

public class PDPSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SimpleGUI();
		new MyFirstEventListener(); //creates a simple event listener
//		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(4, new MyFirstExperimentBuilder());
		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(4, new CatchTheFlagBuilder(new File("leuven.osm.xml")));
	}

}
