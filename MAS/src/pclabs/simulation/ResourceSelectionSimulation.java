package pclabs.simulation;

import pclabs3.configuration.builder.ResourceSelectionBuilder;
import pclabs3.layer.physical.NullConnection;
import pclabs3.layer.physical.NullConnectionEntity;
import pclabs3.layer.physical.SiteLocation;
import framework.experiment.Experiment;
import framework.layer.physical.PhysicalConnectionStructure;

public class ResourceSelectionSimulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MyFirstEventListener(); //creates a simple event listener
		new Experiment<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>(4, new ResourceSelectionBuilder());
	}

}
