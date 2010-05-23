package pclabs3.configuration.directors;

import pclabs.configuration.DelayedTimePattern;
import pclabs.configuration.intructions.CreateTruck;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import pclabs3.configuration.instructions.CreateNullConnectionEntityInstruction;
import pclabs3.configuration.instructions.CreateSiteInstruction;
import pclabs3.layer.physical.NullConnection;
import pclabs3.layer.physical.NullConnectionEntity;
import pclabs3.layer.physical.SiteLocation;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.instructions.creation.CreateCommunicationCapabilityInstruction;
import framework.instructions.creation.CreateCommunicationLinkInstruction;
import framework.instructions.creation.CreateDeviceInstruction;
import framework.instructions.creation.CreateStorageCapabilityInstruction;
import framework.instructions.deployment.DeployConnectionEntityInstruction;
import framework.instructions.deployment.DeployDeviceInstruction;
import framework.instructions.deployment.DeployStaticResourceInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.utils.Utils;

/**
 * populates the PHYSICAL layer with vehicles
 * 
 * @author marioct
 *
 */
public class ComputerInfrastructureInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> {

	public ComputerInfrastructureInitializationDirector() {
		super(new DelayedTimePattern(1000));
	}

	@Override
	protected void createAndDeploy() {
		long currentTime = VirtualClock.currentTime();

		//Creates static resource, device, etc. to run one Agent
		getInstructionManager().addInstruction(new CreateSiteInstruction(currentTime, 1));
		getInstructionManager().addInstruction(new DeployStaticResourceInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, 1,1));
		getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, 1, -1, -1));
		getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, 1, 1));
		
		getInstructionManager().addInstruction(new CreateSiteInstruction(currentTime, 2));
		getInstructionManager().addInstruction(new DeployStaticResourceInstruction<NullConnectionEntity, SiteLocation, NullConnection>(currentTime, 2,2));
		getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, 2, -1, -1));
		getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, 2, 2));
		
	}

}
