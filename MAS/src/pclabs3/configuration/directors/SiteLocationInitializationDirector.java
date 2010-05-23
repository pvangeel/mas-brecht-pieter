package pclabs3.configuration.directors;


import pclabs3.configuration.instructions.CreateSiteLocationInstruction;
import pclabs3.layer.physical.NullConnection;
import pclabs3.layer.physical.NullConnectionEntity;
import pclabs3.layer.physical.SiteLocation;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.initialization.startup.StartupTimePattern;
import framework.instructions.creation.CreateInstruction;
import framework.instructions.deployment.DeployConnectorInstruction;
import framework.layer.physical.PhysicalConnectionStructure;

public class SiteLocationInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> {

	public SiteLocationInitializationDirector() {
		super(new StartupTimePattern());
	}

	/**
	 * Creates a simple graph, with 5 cross nodes, and 8 roads
	 * O -- O
	 * |    |
	 * |    | 
	 * |    |
	 * O -- O
	 * 
	 * O - Crossroad
	 * -- - Roads
	 */
	@Override
	protected void createAndDeploy() {
		long currentTime = VirtualClock.currentTime();
		
		int n1=1, n2=2, n3=3, n4=4, n5=5;
		
		/**
		 * Creates the Sites
		 */
		CreateInstruction c1 = new CreateSiteLocationInstruction(currentTime, n1);
		CreateInstruction c2 = new CreateSiteLocationInstruction(currentTime, n2);
		CreateInstruction c3 = new CreateSiteLocationInstruction(currentTime, n3);
		CreateInstruction c4 = new CreateSiteLocationInstruction(currentTime, n4);
		
		long x = 6696460879l;
		long y = 4003935499l;
		long delta =  1000000l;
		
		DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection> nn1 = new DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, n1, x, y);
		DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection> nn2 = new DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, n2, x+delta, y);
		DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection> nn3 = new DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, n3, x, y+delta);
		DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection> nn4 = new DeployConnectorInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, n4, x+delta, y+delta);

		getInstructionManager().addInstruction(c1);
		getInstructionManager().addInstruction(c2);
		getInstructionManager().addInstruction(c3);
		getInstructionManager().addInstruction(c4);
		
		getInstructionManager().addInstruction(nn1);
		getInstructionManager().addInstruction(nn2);
		getInstructionManager().addInstruction(nn3);
		getInstructionManager().addInstruction(nn4);
		
	}

}
