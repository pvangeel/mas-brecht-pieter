package pclabs3.layer.physical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.layer.agent.Agent;

/**
 * Network-on-Class represents the entire network used by the agents.
 * 
 * Passive entity, that isn't modeled in the simulation framework. This class makes abstraction
 * of the network and represent a type of message service that can be used by the agents 
 * 
 * @author marioct
 *
 */
public final class NoC {
	
	private Map<Agent, ArrayList<Message>> messageBoxes; //YES, I want to constrain the type to ArrayList instead of the generic List 
	
	public NoC() {
		messageBoxes = new HashMap<Agent, ArrayList<Message>>();
	}

	public void addAgent(Agent agent) {
		messageBoxes.put(agent, new ArrayList<Message>());
	}
	
	public void sendMessage(Agent destinationAgent, Message message) {
		ArrayList<Message> inbox = null;
		
		if(messageBoxes.containsKey(destinationAgent)){
			inbox = messageBoxes.get(destinationAgent);
		}
		
		if(inbox == null) {
			inbox = new ArrayList<Message>();
			messageBoxes.put(destinationAgent, inbox);
		}
		
		inbox.add(message);
	}
	
	//FIXME stupid way to implement this method
	public void sendMessage(Message message) {
		List<Agent> agentsWithoutMessages = new ArrayList<Agent>();
		
		ArrayList<Message> inbox;
		for(Map.Entry<Agent, ArrayList<Message>> e: messageBoxes.entrySet()) {
			inbox = e.getValue();
			
			if(inbox == null) {
				agentsWithoutMessages.add(e.getKey());
			}
			else {
				inbox.add(message);
			}
		}
		
		for(Agent agent: agentsWithoutMessages) {
			inbox = new ArrayList<Message>();
			inbox.add(message);
			messageBoxes.put(agent, inbox);
		}
	}
	
	public List<Message> getMessages(Agent agent) {
		ArrayList<Message> ret = (ArrayList<Message>) messageBoxes.get(agent);
		if(ret == null){
			ret = new ArrayList<Message>();
		}
		else {
			ret = (ArrayList) ret.clone();
		}
		
		return ret;
	}
	
	public void clearMessages(Agent agent) {
		if(messageBoxes.containsKey(agent)) {
			messageBoxes.put(agent, null);
		}
	}
}
