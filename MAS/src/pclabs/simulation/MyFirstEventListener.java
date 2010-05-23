package pclabs.simulation;

import pclabs.agentlayer.entities.CautchFlagEvent;
import dashboard.graph.AvgPickupGraph;
import framework.core.VirtualClock;
import framework.events.Event;
import framework.events.EventBroker;
import framework.events.EventListener;
import framework.events.TickUpdateEvent;
import framework.events.physical.CommandIssuedEvent;

/**
 * This is a simple example of how to create a simple EventListener that 
 * prints messages of all types of events.
 * 
 * @author marioct
 *
 */
public class MyFirstEventListener implements EventListener {

	private AvgPickupGraph graph;
	private double commands = 0;
	
	public MyFirstEventListener() {
		EventBroker.getEventBroker().register(this);
		graph = new AvgPickupGraph("My first listener and graph", "x", "Accumulated number of commands", "Just for testing...");
	}
	
	@Override
	public void handleEvent(Event event) {
//		if( event instanceof TickUpdateEvent  == true){
//		graph.addValue(((TickUpdateEvent)event).getTime(), Math.random() * 20);	
////			System.out.println(event.toString());
//		}
		
		if(event instanceof CautchFlagEvent == true){
			graph.addValue(VirtualClock.currentTime(), commands);
			commands++;
		}
	}

	@Override
	public void terminate() {
	}

}
