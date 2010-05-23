package pclabs.configuration.directors;

import pclabs.configuration.DelayedTimePattern;
import pclabs.configuration.intructions.CreateTruck;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
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
public class VehiclesInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	public VehiclesInitializationDirector() {
		super(new DelayedTimePattern(1000));
	}

	/**
	 * Create just one vehicle and deploy the vehicle on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		long currentTime = VirtualClock.currentTime();
		
		Crossroads cr1 = getInstructionManager().findSpecificObject(Crossroads.class, 16483780); //node from leuven 16401060, 16401062
//		Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, 19501839);
		
//		System.out.println("x=" + cr1.getPosition().getX() + "   y=" + cr1.getPosition().getY());
//		System.out.println("x=" + cr2.getPosition().getX() + "   y=" + cr2.getPosition().getY());
		
//		Crossroads cr3 = getInstructionManager().findSpecificObject(Crossroads.class, 540158417);
		
//		Crossroads cr1 = getInstructionManager().findSpecificObject(Crossroads.class, 1);
//		Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, 2);
//		Crossroads cr3 = getInstructionManager().findSpecificObject(Crossroads.class, 3);
		
		getInstructionManager().addInstruction(new CreateTruck(currentTime, 1, Utils.fromKmHToMmMicroSec(5)));
		getInstructionManager().addInstruction(new DeployConnectionEntityInstruction<Truck, Crossroads, Road>(currentTime, 1, cr1.getPosition().getX(), cr1.getPosition().getY(),  true));
		getInstructionManager().addInstruction(new CreateCommunicationCapabilityInstruction(currentTime, 1, null));
		getInstructionManager().addInstruction(new CreateStorageCapabilityInstruction(currentTime, 1, 100000));
		getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, 1, 1, 1));
		getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, 1, 1));
		
		
		
//		getInstructionManager().addInstruction(new CreateTruck(currentTime, 2, Utils.fromKmHToMmMicroSec(5)));
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
