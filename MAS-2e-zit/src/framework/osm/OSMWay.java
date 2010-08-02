package framework.osm;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class OSMWay extends OSMObject {

	private final LinkedList<OSMNode> nodes = new LinkedList<OSMNode>();
	private boolean oneWay;

	public OSMWay(int id) {
		super(id);
	}

	public OSMWay(int id, boolean oneWay) {
		super(id);
		setOneWay(oneWay);
	}

	public void addNode(OSMNode node) {
		nodes.add(node);
	}

	public List<OSMNode> getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return "OSMWay (id: " + getId() + ", oneway: " + isOneWay() + ", nodes: " + getNodes() + ")";
	}

	public boolean isOneWay() {
		return oneWay;
	}

	public boolean isTwoWay() {
		return !oneWay;
	}

	public void setOneWay(boolean oneWay) {
		this.oneWay = oneWay;
	}

	public void setTwoWay(boolean twoWay) {
		this.oneWay = !twoWay;
	}

	@Override
	public void executeSpecificAddOperations() {
		for (OSMNode node : getNodes()) {
			node.belongsTo(this);
		}
	}

	public boolean isFirstNode(OSMNode osmNode) {
		return getNodes().get(0).equals(osmNode);
	}

	public boolean isLastNode(OSMNode osmNode) {
		return getNodes().get(getNodes().size() - 1).equals(osmNode);
	}

	public void removeNode(OSMNode node) {
//		System.out.println("OSMWay.removeNode() "+this);
		if (nodes.contains(node)) {
			for (OSMNode nextNode : getNextNodes(node)) {
				nextNode.incommingWayRemoved(this);
			}
			for (OSMNode previousNode : getPreviousNodes(node)) {
				previousNode.outgoingWayRemoved(this);
			}
			nodes.remove(node);
		}
	}

	private Collection<OSMNode> getNextNodes(OSMNode node) {
		HashSet<OSMNode> nextNodes = new HashSet<OSMNode>();
		OSMNode next1 = getNode(getNodes().indexOf(node) + 1);
		if (next1 != null) {
			nextNodes.add(next1);
		}
		if (isTwoWay()) {
			OSMNode next2 = getNode(getNodes().indexOf(node) - 1);
			if (next2 != null) {
				nextNodes.add(next2);
			}
		}
		return nextNodes;
	}

	private Collection<OSMNode> getPreviousNodes(OSMNode node) {
		HashSet<OSMNode> previousNodes = new HashSet<OSMNode>();
		OSMNode previous1 = getNode(getNodes().indexOf(node) - 1);
		if (previous1 != null) {
			previousNodes.add(previous1);
		}
		if (isTwoWay()) {
			OSMNode previous2 = getNode(getNodes().indexOf(node) + 1);
			if (previous2 != null) {
				previousNodes.add(previous2);
			}
		}
		return previousNodes;
	}

	private OSMNode getNode(int index) {
		if (index >= 0 && index <= getNodes().size() - 1) {
			return getNodes().get(index);
		}
		return null;
	}

	@Override
	public void executeSpecificRemoveOperations() {
		throw new UnsupportedOperationException();
	}

	public OSMWay createNewWay() {
		OSMWay newWay = new OSMWay(getId());
		newWay.setOneWay(isOneWay());
		return newWay;
	}

	@Override
	public void onCreationDone() {
		if(isUsed()) {
			for(OSMNode node : nodes) {
				node.setUsed(true);
			}
		}
	}

}
