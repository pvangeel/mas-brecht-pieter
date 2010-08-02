package configuration.intructions;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

import org.apache.log4j.Logger;

import framework.instructions.deployment.DeployConnectorInstruction;
import framework.layer.physical.position.ContinuousPosition;

public class DeployCrossroadsAndFlagsInstruction extends DeployConnectorInstruction<Truck, Crossroads, Road> {
	private static Logger logger = Logger.getLogger(DeployCrossroadsAndFlagsInstruction.class);
	
	private int connectorId;
	private long positionX;
	private long positionY;
	private String flag;

	public DeployCrossroadsAndFlagsInstruction(long executionTime, int connectorId, long positionX, long positionY, String flag) {
			super(executionTime, connectorId, positionX, positionY);
			this.connectorId = connectorId;
			this.positionX = positionX;
			this.positionY = positionY;
			this.flag = flag;
		}	

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Crossroads c =  getInstructionManager().findSpecificObject(Crossroads.class, connectorId);
		if(flag != null){
			c.setFlag(flag);
			logger.debug("Setting flag" + c.getId() + "\t" + c.toString());
		}
		
		getInstructionManager().getPhysicalStructure().addConnector(c, new ContinuousPosition(positionX, positionY));
		
	}

}
