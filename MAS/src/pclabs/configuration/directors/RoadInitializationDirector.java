package pclabs.configuration.directors;


import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import pclabs.configuration.intructions.CreateCrossroadsInstruction;
import pclabs.configuration.intructions.CreateTwoWayRoadInstruction;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.initialization.startup.StartupTimePattern;
import framework.instructions.creation.CreateInstruction;
import framework.instructions.deployment.DeployConnectionInstruction;
import framework.instructions.deployment.DeployConnectorInstruction;
import framework.layer.physical.PhysicalConnectionStructure;

public class RoadInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	public RoadInitializationDirector() {
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
		
		CreateInstruction c1 = new CreateCrossroadsInstruction(currentTime, n1);
		CreateInstruction c2 = new CreateCrossroadsInstruction(currentTime, n2);
		CreateInstruction c3 = new CreateCrossroadsInstruction(currentTime, n3);
		CreateInstruction c4 = new CreateCrossroadsInstruction(currentTime, n4);
		
		long x = 6696460879l;
		long y = 4003935499l;
		long delta =  1000000l;
		
		DeployConnectorInstruction<Truck,Crossroads,Road> nn1 = new DeployConnectorInstruction<Truck, Crossroads, Road>(currentTime, n1, x, y);
		DeployConnectorInstruction<Truck,Crossroads,Road> nn2 = new DeployConnectorInstruction<Truck, Crossroads, Road>(currentTime, n2, x+delta, y);
		DeployConnectorInstruction<Truck,Crossroads,Road> nn3 = new DeployConnectorInstruction<Truck, Crossroads, Road>(currentTime, n3, x, y+delta);
		DeployConnectorInstruction<Truck,Crossroads,Road> nn4 = new DeployConnectorInstruction<Truck, Crossroads, Road>(currentTime, n4, x+delta, y+delta);
		
		getInstructionManager().addInstruction(c1);
		getInstructionManager().addInstruction(c2);
		getInstructionManager().addInstruction(c3);
		getInstructionManager().addInstruction(c4);
		
		getInstructionManager().addInstruction(nn1);
		getInstructionManager().addInstruction(nn2);
		getInstructionManager().addInstruction(nn3);
		getInstructionManager().addInstruction(nn4);
		
		CreateInstruction r1 = new CreateTwoWayRoadInstruction(currentTime, 6);
		CreateInstruction r2 = new CreateTwoWayRoadInstruction(currentTime, 7);
		CreateInstruction r3 = new CreateTwoWayRoadInstruction(currentTime, 8);
		CreateInstruction r4 = new CreateTwoWayRoadInstruction(currentTime, 9);
		
		getInstructionManager().addInstruction(r1);
		getInstructionManager().addInstruction(r2);
		getInstructionManager().addInstruction(r3);
		getInstructionManager().addInstruction(r4);
//		getInstructionManager().addInstruction(r5);
		
		
		getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, 6, n1, n2));
		getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, 7, n2, n4));
		getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, 8, n4, n3));
		getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, 9, n3, n1));
		
		
	}

}
