package layer.physical.entities;

import framework.layer.deployment.storage.Data;

public class FlagData extends Data {

	private String flagData;

	public FlagData(String flag) {
		this.flagData = flag;
	}
	
	@Override
	public long getStorageSize() {
		return 1000;
	}
	
	public String getFlagData() {
		return flagData;
	}

}
