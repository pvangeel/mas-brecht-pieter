package pclabs3.configuration.instructions;

import java.lang.reflect.InvocationTargetException;

import pclabs3.layer.physical.NoC;

import framework.instructions.creation.CreateInstruction;
import framework.layer.agent.Agent;

public class CreateAgentInstruction extends CreateInstruction {

	private Class<? extends Agent> agentType;
	private NoC noc;

	public CreateAgentInstruction(long currentTime, Class<? extends Agent> agentType, int agentId, NoC noc) {
		super(currentTime, agentId);
		this.agentType = agentType;
		this.noc = noc;
	}
	
	@Override
	protected Object createObject() {
		Agent newAgent = null;
		try {
			
			newAgent = agentType.getConstructor(NoC.class).newInstance(noc);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		return newAgent;
	}

}
