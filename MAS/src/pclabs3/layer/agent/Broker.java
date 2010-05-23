package pclabs3.layer.agent;

import framework.core.VirtualClock;
import framework.layer.agent.Agent;
import pclabs3.layer.physical.Message;
import pclabs3.layer.physical.NoC;

public class Broker extends Agent {
	private NoC noc;

	public Broker(NoC noc) {
		this.noc = noc;
		this.noc.addAgent(this);
	}
	
	@Override
	public void processTick(long timePassed) {
		//produces messages
		if(Math.random() >= 0.9999999999999) {
			Message m = new CNETMsg("Test" + VirtualClock.currentTime());
			noc.sendMessage(m);
		}
	}

	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}
}
