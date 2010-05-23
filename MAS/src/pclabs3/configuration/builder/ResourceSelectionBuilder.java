package pclabs3.configuration.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pclabs3.configuration.directors.AgentsInitializationDirector;
import pclabs3.configuration.directors.ComputerInfrastructureInitializationDirector;
import pclabs3.configuration.directors.SiteLocationInitializationDirector;
import pclabs3.layer.physical.NoC;
import pclabs3.layer.physical.NullConnection;
import pclabs3.layer.physical.NullConnectionEntity;
import pclabs3.layer.physical.SiteLocation;
import framework.experiment.ExperimentBuilder;
import framework.initialization.InitializationDirector;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;
import framework.utils.Utils;

public class ResourceSelectionBuilder extends ExperimentBuilder<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>{

	private NoC noc;

	public ResourceSelectionBuilder() {
		this.noc = new NoC();
	}
	
	@Override
	public void createExtraListeners(int simulationRun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>> getInitializers() {
		List<InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>> initializers = new ArrayList<InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>>();
		
		InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> siteInfrastructure = new SiteLocationInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> devices = new ComputerInfrastructureInitializationDirector();
		InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> agents = new AgentsInitializationDirector(noc);
		
		
		initializers.add(siteInfrastructure);
		initializers.add(devices);
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
	public PhysicalLayer<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> getPhysicalcalLayer() {
		return new PhysicalLayer<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>>(
				new PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>());
	}

	@Override
	public long getSimulationTime() {
		int simulationTimeInMinutes = 60 * 24 * 5; // 5 days
		return Utils.minutesToMicroSeconds(simulationTimeInMinutes);
	}

}
