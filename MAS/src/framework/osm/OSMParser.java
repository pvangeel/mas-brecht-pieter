package framework.osm;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * A parser that parses OpenStreetMap Data from an OSM data file
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class OSMParser extends DefaultHandler {

	private final SAXParser saxParser;
	private final OSMObjectPool objectPool = new OSMObjectPool();
	private OSMObjectBuilder<?> activeBuilder;

	public OSMParser() throws Exception {
		saxParser = SAXParserFactory.newInstance().newSAXParser();
	}

	public OSMObjectPool parse(File file) throws Exception {
		if (file == null) {
			throw new IllegalArgumentException();
		}
		System.out.println("parsing " + file + " begon");
		saxParser.parse(file, this);
		objectPool.clearUnusedObjects();
		System.out.println("parsing " + file + " done");
		return objectPool;
	}

	public void handleFinishedObject(OSMObject osmObject) {
		objectPool.addObject(osmObject);
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if (hasActiveBuilder()) {
			getActiveBuilder().handle(name, getArguments(attributes));
		} else {
			OSMObjectBuilder<?> builder = getObjectBuilder(name);
			if (builder != null) {
				activeBuilder = builder;
				getActiveBuilder().initiate(getArguments(attributes));
			}
		}
	}

	private HashMap<String, String> getArguments(Attributes attributes) {
		HashMap<String, String> arguments = new HashMap<String, String>();
		for (int i = 0; i < attributes.getLength(); i++) {
			arguments.put(attributes.getQName(i), attributes.getValue(i));
		}
		return arguments;
	}

	private OSMObjectBuilder<?> getObjectBuilder(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		if (name.equals("node")) {
			return new OSMNodeBuilder(objectPool);
		} else if (name.equals("way")) {
			return new OSMWayBuilder(objectPool);
		} else {
			return null;
		}
	}

	public OSMObjectBuilder<?> getActiveBuilder() {
		if (activeBuilder == null) {
			throw new IllegalStateException();
		}
		return activeBuilder;
	}

	private boolean hasActiveBuilder() {
		return activeBuilder != null;
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		if (name.equals("node") || name.equals("way")) {
			getActiveBuilder().terminate();
			activeBuilder = null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {

	}
}