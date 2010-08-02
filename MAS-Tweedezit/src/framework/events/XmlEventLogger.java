package framework.events;

import framework.xml.XMLLogger;

/**
 * 
 * An XMLLogger that logs Events dispatches by the simulator
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class XmlEventLogger extends XMLLogger implements EventListener {

	public XmlEventLogger(String filename, int persistenceTreshold) {
		super(filename, persistenceTreshold);
		EventBroker.getEventBroker().register(this);
	}

	/**
	 * Handle the event by adding it to the XML output stream
	 */
	@Override
	public void handleEvent(Event event) {
		addObject(event);
	}

	/**
	 * terminate the XMLEventLogger by persisting all data to XML file
	 */
	@Override
	public void terminate() {
		persistAll();
	}

}
