package simulation;

import java.io.File;

import configuration.builders.GradientFieldPDPBuilder;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.experiment.Experiment;
import framework.layer.physical.PhysicalConnectionStructure;
import gui.SimpleGUI;

public class GradientFieldSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		int numberOfLinesInGrid = 14;
		int numberOfAgents = 3;
		
		new SimpleGUI(numberOfLinesInGrid);
//		new MyFirstEventListener(); //creates a simple event listener
//		new PackagesCreatedEventListener();
//		new PackagesDeliveredEventListener();
		new Experiment<PhysicalConnectionStructure<Truck, Crossroads, Road>>(1, new GradientFieldPDPBuilder(numberOfLinesInGrid,numberOfAgents));
	}

}
