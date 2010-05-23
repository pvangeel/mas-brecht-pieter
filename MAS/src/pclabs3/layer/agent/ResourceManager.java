package pclabs3.layer.agent;

import framework.layer.agent.Agent;
import pclabs3.layer.physical.NoC;

public class ResourceManager extends Agent {
	private NoC noc;

	public ResourceManager(NoC noc) {
		this.noc = noc;
		this.noc.addAgent(this);
	}

	@Override
	public void processTick(long timePassed) {
		//Consumes messages
		if(noc.getMessages(this).size() > 0) {
			System.out.println("Nice... I have " + noc.getMessages(this).size() + " messages");
			noc.clearMessages(this);
		}
		
	}

	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}
}
