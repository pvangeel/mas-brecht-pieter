package layer.physical.commands;


import layer.physical.entities.Truck;

import org.apache.log4j.Logger;

import framework.layer.physical.command.Command;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;

public class PickPackage extends Command<Truck>{

	private static Logger logger = Logger.getLogger("PickPackage");

	public PickPackage(Truck dv) {
		super(dv);
		
		logger.debug("SenseEnvironmentCommand created.");
	}
	
	@Override
	public void execute() throws IllegalCommandException,
			CommandUncompletedException {
		logger.debug("Executing PickPackage");
		
		if( getEntity().isOnConnector() ){
			if(getEntity().getConnectorPosition().getConnector().hasPackage())
				getEntity().load();
			
//			try {
//				StorageCapability storage = getEntity().getAttachedDevices().iterator().next().getStorageCapability();
//				if(flag != null) {
//					storage.storeData(new FlagData(flag));
//				}
//				else {
//					throw new IllegalCommandException(this);
//				}
//				
//			} catch (InactiveCapabilityException e) {
//				logger.debug("Inactive Capability", e);
//				throw new IllegalCommandException(this);
//			} catch (StorageCapacityExceededExeption e) {
//				logger.debug("Storage Capacity Exceeeded", e);
//				throw new IllegalCommandException(this);
//			}
			
		}
	}

}
