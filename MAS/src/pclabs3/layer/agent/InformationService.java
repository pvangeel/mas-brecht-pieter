package pclabs3.layer.agent;

import framework.layer.agent.Agent;
import pclabs3.layer.physical.NoC;

public class InformationService extends Agent {
	private NoC noc;

	public InformationService(NoC noc) {
		this.noc = noc;
		this.noc.addAgent(this);
	}
	
	@Override
	public void processTick(long timePassed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}
}
