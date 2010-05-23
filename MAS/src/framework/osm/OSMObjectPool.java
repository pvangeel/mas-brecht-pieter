package framework.osm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OSMObjectPool {

	private final Map<Integer, OSMNode> nodes = new HashMap<Integer, OSMNode>();
	private final Map<Integer, OSMWay> ways = new HashMap<Integer, OSMWay>();

	public void addObject(OSMObject osmObject) {
		if (osmObject == null) {
			throw new IllegalArgumentException();
		}
		if (osmObject instanceof OSMNode) {
			if(nodes.containsKey(osmObject.getId())) {
				throw new IllegalArgumentException();
			}
			nodes.put(osmObject.getId(), (OSMNode) osmObject);
		} else if (osmObject instanceof OSMWay) {
			if(ways.containsKey(osmObject.getId())) {
				throw new IllegalArgumentException();
			}
			ways.put(osmObject.getId(), (OSMWay) osmObject);
		} else {
			throw new IllegalArgumentException();
		}
		osmObject.executeSpecificAddOperations();
	}

	public Collection<OSMNode> getNodes() {
		return nodes.values();
	}

	public Collection<OSMWay> getWays() {
		return ways.values();
	}

	public OSMNode getOSMNode(int id) {
		if (!hasOSMNode(id)) {
			throw new IllegalArgumentException();
		}
		return nodes.get(id);
	}

	public boolean hasOSMNode(int id) {
		return nodes.containsKey(id);
	}

	public void print() {
		System.out.println("--Nodes--");
		for (OSMNode node : getNodes()) {
			System.out.println(node);
		}
		System.out.println("--Ways--");
		for (OSMObject way : getWays()) {
			System.out.println(way);
		}
	}

	public void removeNode(OSMNode node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		if(!hasNode(node)) {
			throw new IllegalArgumentException();
		}
		nodes.remove(node.getId());
		node.executeSpecificRemoveOperations();
	}

	private boolean hasNode(OSMNode node) {
		return nodes.containsKey(node.getId());
	}

	public void clearUnusedObjects() {
		for(OSMNode node : new HashSet<OSMNode>(nodes.values())) {
			if(hasNode(node) && !node.isUsed()) {
				removeNode(node);
			}
		}
	}
	
	
}
