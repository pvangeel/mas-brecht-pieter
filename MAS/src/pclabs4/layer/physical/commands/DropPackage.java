package pclabs4.layer.physical.commands;


import org.apache.log4j.Logger;

import pclabs.physicallayer.entities.PDPPackageDTO;
import pclabs.physicallayer.entities.Truck;
import framework.layer.physical.command.Command;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;

public class DropPackage extends Command<Truck>{

	private static Logger logger = Logger.getLogger("DropPackage");
	private PDPPackageDTO pdpPackage;

	public DropPackage(Truck dv, PDPPackageDTO pdpPackage) {
		super(dv);
		this.pdpPackage = pdpPackage;
		logger.debug("DropPackage Command created.");
	}
	
	@Override
	public void execute() throws IllegalCommandException,
			CommandUncompletedException {
		logger.debug("Executing PickPackage");
		
		if( getEntity().isOnConnector() ){
			
			if(getEntity().getConnectorPosition().getConnector().equals(pdpPackage.getDestination())) {
				getEntity().unload();
			}
			else {
				throw new IllegalCommandException(this);
			}
			
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
