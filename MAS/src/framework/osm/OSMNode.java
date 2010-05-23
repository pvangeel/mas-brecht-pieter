package framework.osm;

import java.util.ArrayList;

import framework.layer.physical.position.LLPosition;

public class OSMNode extends OSMObject {

	private final LLPosition position;
	private final ArrayList<OSMWay> incomming = new ArrayList<OSMWay>();
	private final ArrayList<OSMWay> outgoing = new ArrayList<OSMWay>();

	public OSMNode(int id, double latitude, double longitude) {
		super(id);
		position = new LLPosition(latitude, longitude);
	}

	public LLPosition getPosition() {
		return position;
	}

	@Override
	public String toString() {
		String i = "[";
		for (OSMWay osmWay : incomming) {
			i += osmWay.getId() + ",";
		}
		i += "]";
		String o = "[";
		for (OSMWay osmWay : outgoing) {
			o += osmWay.getId() + ",";
		}
		o += "]";
		return "OSMNode (id: " + getId() + " i: " + i + " o: " + o + ")";
		// + ", lat: "
		// + getPosition().getLatitude() + ", long: " +
		// getPosition().getLongitude() + ")";
	}

	@Override
	public void executeSpecificAddOperations() {

	}

	public void belongsTo(OSMWay osmWay) {
//		System.out.println("OSMNode.belongsTo() " + getId() + " belongs to " + osmWay.getId());
		if (osmWay.isTwoWay()) {
			if (osmWay.isFirstNode(this) || osmWay.isLastNode(this)) {
				incomming.add(osmWay);
				outgoing.add(osmWay);
			} else {
				incomming.add(osmWay);
				incomming.add(osmWay);
				outgoing.add(osmWay);
				outgoing.add(osmWay);
			}
		} else {
			if (osmWay.isFirstNode(this)) {
				outgoing.add(osmWay);
			} else if (osmWay.isLastNode(this)) {
				incomming.add(osmWay);
			} else {
				incomming.add(osmWay);
				outgoing.add(osmWay);
			}
		}
//		System.out.println("OSMNode.belongsTo() " + this);
	}

	public ArrayList<OSMWay> getIncomming() {
		return incomming;
	}

	public ArrayList<OSMWay> getOutgoing() {
		return outgoing;
	}

	@Override
	public void executeSpecificRemoveOperations() {
//		System.out.println("OSMNode.executeSpecificRemoveOperations() " + this);
		for (OSMWay way : incomming) {
			way.removeNode(this);
		}
		for (OSMWay way : outgoing) {
			way.removeNode(this);
		}
		incomming.clear();
		outgoing.clear();
	}

	public void incommingWayRemoved(OSMWay osmWay) {
//		System.out.println("OSMNode.incommingWayRemoved() " + this);
		incomming.remove(osmWay);
	}

	public void outgoingWayRemoved(OSMWay osmWay) {
//		System.out.println("OSMNode.outgoingWayRemoved() " + this);
		outgoing.remove(osmWay);
	}

	// public boolean hasNoUsefulWays() {
	// Collection<OSMWay> in = new HashSet<OSMWay>(incomming);
	// Collection<OSMWay> out = new HashSet<OSMWay>(outgoing);
	// in.removeAll(out);
	// out.removeAll(in);
	// boolean notUseful = in.isEmpty() || out.isEmpty();
	// return notUseful;
	// }

	@Override
	public void onCreationDone() {
	}

	public boolean isUseless() {
//		System.out.println("OSMNode.isUseless() " + this);
		if (getIncomming().size() == 1 && getOutgoing().size() == 1) {
			OSMWay in = (OSMWay) getIncomming().toArray()[0];
			OSMWay out = (OSMWay) getOutgoing().toArray()[0];
			if (in.equals(out)) {
//				System.out.println("OSMNode.isUseless() " + in.isFirstNode(this) + " " + in.isLastNode(this));
				return in.isFirstNode(this) || in.isLastNode(this);
			}
		}
		return getIncomming().isEmpty() || getOutgoing().isEmpty();
	}
}
