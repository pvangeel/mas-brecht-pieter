package configuration.directors;

import java.util.Random;

import configuration.intructions.CreateGradientFieldPDPPackage;
import configuration.intructions.CreatePDPPackage;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.initialization.IntervalTimePattern;
import framework.instructions.creation.CreateCommunicationCapabilityInstruction;
import framework.instructions.creation.CreateDeviceInstruction;
import framework.instructions.creation.CreateStorageCapabilityInstruction;
import framework.instructions.deployment.DeployAgentInstruction;
import framework.instructions.deployment.DeployConnectionEntityInstruction;
import framework.instructions.deployment.DeployDeviceInstruction;
import framework.instructions.deployment.DeployPDPPackage;
import framework.layer.agent.Agent;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.utils.IdGenerator;
import framework.utils.Utils;

/**
 * populates the PHYSICAL layer with packages
 * 
 * @author marioct
 *
 */
public class GradientFieldPDPPackagesDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	private static final int PACKAGES_PER_HOUR = 1;
	private static final long PACKAGE_GENERATOR_SEED = 1290299;
	private static final int TOTAL_NUMBER_OF_PACKAGES = 2000;
	private Random random;
	private int numberOfPackages = 0;
	private long lastTime = 0;
	private final int maxIdOfNode;
	
	public GradientFieldPDPPackagesDirector(int width) {
		super(new IntervalTimePattern(Utils.minutesToMicroSeconds(10)));
		this.maxIdOfNode = width * width;
		random = new Random(PACKAGE_GENERATOR_SEED);
	}

	/**
	 * Create just one package and deploy the package on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		long currentTime = VirtualClock.currentTime();
		if(lastTime != 0 && currentTime - lastTime < Utils.minutesToMicroSeconds(60))
			return;
		lastTime = currentTime;
		if(numberOfPackages >= TOTAL_NUMBER_OF_PACKAGES) {
			return;
		}
		
		int i=0;
		for(; i<PACKAGES_PER_HOUR; i++) {
//			int id1 = nodesExternalIds[(int)Math.ceil(random.nextDouble() * (nodesExternalIds.length - 1))];
//			int id2 = nodesExternalIds[(int)Math.ceil(random.nextDouble() * (nodesExternalIds.length - 1))];
			
			int id1 = (int) (random.nextDouble() * maxIdOfNode);
			Crossroads cr1 = getInstructionManager().findSpecificObject(Crossroads.class, id1);
			while(cr1.hasPackage()){
				id1 = (int) (random.nextDouble() * maxIdOfNode);
				cr1 = getInstructionManager().findSpecificObject(Crossroads.class, id1);
			}
			int id2 = (int) (random.nextDouble() * maxIdOfNode);
			while(id1 == id2){
				id2 = (int) (random.nextDouble() * maxIdOfNode);
			}
			
			
			Crossroads cr2 = getInstructionManager().findSpecificObject(Crossroads.class, id2);
			
			
			//avoid deploying packages in Crossroads without exit
			if(cr2.getOutgoingConnections().size() > 0 || cr1.getOutgoingConnections().size() > 0) {
				int packageId = IdGenerator.getIdGenerator().getNextId(PDPPackage.class);
				
//				int deviceId = IdGenerator.getIdGenerator().getNextId(Device.class);
				getInstructionManager().addInstruction(new CreateGradientFieldPDPPackage(currentTime, packageId, cr1, cr2, 10.0));
				getInstructionManager().addInstruction(new DeployPDPPackage(currentTime, packageId));
//				System.out.println("Package deployed:" + packageId);
				//getInstructionManager().addInstruction(new DeployAgentInstruction(currentTime, lastId + i, 1));
				
				//getInstructionManager().addInstruction(new DeployConnectionEntityInstruction<Truck, Crossroads, Road>(currentTime, 1, cr1.getPosition().getX(), cr1.getPosition().getY(),  true));
//				getInstructionManager().addInstruction(new CreateCommunicationCapabilityInstruction(currentTime, 1, null));
//				getInstructionManager().addInstruction(new CreateStorageCapabilityInstruction(currentTime, 1, 100000));
//				getInstructionManager().addInstruction(new CreatePDPPackage(currentTime, packageId, cr1, cr2, 10.0));
//				getInstructionManager().addInstruction(new CreateDeviceInstruction(currentTime, deviceId, -1, -1));
//				getInstructionManager().addInstruction(new DeployDeviceInstruction(currentTime, deviceId, packageId));
				
				
			}
			
		}
		
		numberOfPackages += i; 
	}

}
