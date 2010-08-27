package configuration.directors;

import java.util.Random;

import configuration.DelayedTimePattern;
import configuration.intructions.CreateTruck;
import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.instructions.creation.CreateCommunicationCapabilityInstruction;
import framework.instructions.creation.CreateDeviceInstruction;
import framework.instructions.creation.CreateStorageCapabilityInstruction;
import framework.instructions.deployment.DeployConnectionEntityInstruction;
import framework.instructions.deployment.DeployDeviceInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.utils.Utils;

/**
 * populates the PHYSICAL layer with vehicles
 * 
 * @author marioct
 *
 */
public class DelegateMASVehiclesInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	private final int numberOfAgents;
	private final int numberOfCrossroads;
	private Random random;

	public DelegateMASVehiclesInitializationDirector(int numberOfAgents, int widthOfGrid) {
		super(new DelayedTimePattern(1000));
		this.numberOfAgents = numberOfAgents;
		this.numberOfCrossroads = widthOfGrid * widthOfGrid;
		this.random = new Random();
	}

	/**
	 * Create just one vehicle and deploy the vehicle on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		long currentTime = VirtualClock.currentTime();
		
		/*
		 * Onderstaande lijntjes halen de crossroads op waar de vehicles worden gedeployed
		 */
		
//		Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, 2);

//		Crossroads cr1 = getInstructionManager().findSpecificObject(Crossroads.class, 16483780);
//		Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, 16483813);
		
//		System.out.println("x=" + cr1.getPosition().getX() + "   y=" + cr1.getPosition().getY());
//		System.out.println("x=" + cr2.getPosition().getX() + "   y=" + cr2.getPosition().getY());
		
//		Crossroads cr3 = getInstructionManager().findSpecificObject(Crossroads.class, 540158417);
		
//		Crossroads cr1 = getInstructionManager().findSpecificObject(Crossroads.class, 1);
//		Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, 2);
//		Crossroads cr3 = getInstructionManager().findSpecificObject(Crossroads.class, 3);
		
		for (int i = 0; i < numberOfAgents; i++) {
			int delay = 0 * i;
			Crossroads cr = getInstructionManager().findSpecificObject(Crossroads.class, (int) (random.nextDouble() * numberOfCrossroads));
			getInstructionManager().addInstruction(new CreateTruck(currentTime + Utils.minutesToMicroSeconds(delay), i, Utils.fromKmHToMmMicroSec(1)));
			getInstructionManager().addInstruction(new DeployConnectionEntityInstruction<Truck, Crossroads, Road>(currentTime + Utils.minutesToMicroSeconds(delay), i, cr.getPosition().getX(), cr.getPosition().getY(),  true));
			getInstructionManager().addInstruction(new CreateCommunicationCapabilityInstruction(currentTime + Utils.minutesToMicroSeconds(delay), i, null));
			getInstructionManager().addInstruction(new CreateStorageCapabilityInstruction(currentTime + Utils.minutesToMicroSeconds(delay), i, 100000));
			getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime + Utils.minutesToMicroSeconds(delay), i, i, i));
			getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime + Utils.minutesToMicroSeconds(delay), i, i));
		}
		
//		getInstructionManager().addInstruction(new CreateTruck(currentTime, 2, Utils.fromKmHToMmMicroSec(1)));
//		getInstructionManager().addInstruction(new DeployConnectionEntityInstruction<Truck, Crossroads, Road>(currentTime, 2, cr2.getPosition().getX(),cr2.getPosition().getY(), true));
//		getInstructionManager().addInstruction(new CreateCommunicationCapabilityInstruction(currentTime, 2, null));
//		getInstructionManager().addInstruction(new CreateStorageCapabilityInstruction(currentTime, 2, 100000));
//		getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, 2, 2, 2));
//		getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, 2, 2));
//		
//		
//		getInstructionManager().addInstruction(new CreateTruck(currentTime, 3, Utils.fromKmHToMmMicroSec(5)));
//		getInstructionManager().addInstruction(new DeployConnectionEntityInstruction<Truck, Crossroads, Road>(currentTime, 3, cr1.getPosition().getX(), cr1.getPosition().getY(),  true));
//		getInstructionManager().addInstruction(new CreateCommunicationCapabilityInstruction(currentTime, 3, null));
//		getInstructionManager().addInstruction(new CreateStorageCapabilityInstruction(currentTime, 3, 100000));
//		getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, 3, 3, 3));
//		getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, 3, 3));
		
	}

}
