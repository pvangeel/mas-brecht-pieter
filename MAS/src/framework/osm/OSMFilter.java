package framework.osm;

import java.util.HashSet;

/**
 * A filter for OpenStreetMap data, to filter out unwanted data like traffic lights, bus stops, etc
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */
public class OSMFilter {

	private final OSMObjectPool objectPool;

	public OSMFilter(OSMObjectPool objectPool) {
		if (objectPool == null) {
			throw new IllegalArgumentException();
		}
		this.objectPool = objectPool;
		startFiltering();
	}

	public OSMObjectPool getFilteredObjectPool() {
		return objectPool;
	}

	private void startFiltering() {
		boolean changed = true;
		while(changed) {
			changed = false;
//			int before = objectPool.getNodes().size();
			for(OSMNode node : new HashSet<OSMNode>(objectPool.getNodes())) {
				if(objectPool.hasOSMNode(node.getId())) {
					if(node.isUseless()) {
						objectPool.removeNode(node);
						changed = true;
					}
				}
			}
//			int after = objectPool.getNodes().size();
//			System.out.println("OSMFilter.startFiltering() "+after+"/"+before);
		}
	}
	
	public static void main(String[] args) {
		OSMObjectPool objectPool = new OSMObjectPool();
		OSMNode node1 = new OSMNode(1, 50,5);
		OSMNode node2 = new OSMNode(2, 50,5);
		OSMNode node3 = new OSMNode(3, 50,5);
		OSMNode node4 = new OSMNode(4, 50,5);
		OSMNode node5 = new OSMNode(5, 50,5);
		OSMNode node6 = new OSMNode(6, 50,5);
		
		OSMWay way1 = new OSMWay(11);
		way1.addNode(node1);
		way1.addNode(node2);
		way1.addNode(node3);
		way1.addNode(node4);
		OSMWay way2 = new OSMWay(12);
		way2.addNode(node2);
		way2.addNode(node5);
		OSMWay way3 = new OSMWay(13);
		way3.addNode(node5);
		way3.addNode(node6);
		OSMWay way4 = new OSMWay(14);
		way4.addNode(node6);
		way4.addNode(node3);
//		OSMWay way5 = new OSMWay(15);
//		way5.addNode(node4);
//		way5.addNode(node5);
//
//		way1.setOneWay(true);
		way2.setOneWay(true);
		way3.setOneWay(true);
		way4.setOneWay(true);
//		way5.setOneWay(true);

		objectPool.addObject(node1);
		objectPool.addObject(node2);
		objectPool.addObject(node3);
		objectPool.addObject(node4);
		objectPool.addObject(node5);
		objectPool.addObject(node6);
		objectPool.addObject(way1);
		objectPool.addObject(way2);
		objectPool.addObject(way3);
		objectPool.addObject(way4);
//		objectPool.addObject(way5);
		
		new OSMFilter(objectPool);
	}
}
