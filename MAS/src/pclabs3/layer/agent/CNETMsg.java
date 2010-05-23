package pclabs3.layer.agent;

import pclabs3.layer.physical.Message;

public class CNETMsg implements Message {

	private String data;
	
	public CNETMsg(String data) {
		this.data = data;
	}
	
	@Override
	public String getData() {
		return data;
	}

}
