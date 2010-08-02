package configuration.directors;

import java.io.File;
import java.util.Random;
import java.util.UUID;

import configuration.intructions.CreateCrossroadsInstruction;
import configuration.intructions.CreateOneWayRoadInstruction;
import configuration.intructions.CreateTwoWayRoadInstruction;
import configuration.intructions.DeployCrossroadsAndFlagsInstruction;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.initialization.startup.StartupTimePattern;
import framework.instructions.creation.CreateInstruction;
import framework.instructions.deployment.DeployConnectionInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.osm.OSMFilter;
import framework.osm.OSMNode;
import framework.osm.OSMObjectPool;
import framework.osm.OSMParser;
import framework.osm.OSMWay;

public class OSMDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	private final OSMObjectPool objectPool;
	private int numberOfFlags = 0;
	private Random randomGen;
	
	public OSMDirector(File osmFile) {
		super(new StartupTimePattern());

		randomGen = new Random(1000);
		
		try {
			OSMParser osmParser = new OSMParser();
			OSMObjectPool rawObjectPool = osmParser.parse(osmFile);
			OSMFilter filter = new OSMFilter(rawObjectPool);
			objectPool = filter.getFilteredObjectPool();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

	@Override
	protected void createAndDeploy() {
		deployNodes();
		deployWays();
	}

	private void deployVehicles() {
//		ArrayList<OSMNode> nodes = new ArrayList<OSMNode>(objectPool.getNodes());
//		Collections.shuffle(nodes);
//		for (int i = 0; i < nbTrucks; i++) {
//			OSMNode node = nodes.get(i);
//			if (application == Application.GRADIENT) {
//				EntityCreator.getEntityCreator().createGradientDeliveryVehicle(getInstructionManager(), node.getPosition().getX(),
//						node.getPosition().getY());
//			} else if (application == Application.DMAS) {
//				EntityCreator.getEntityCreator().createDmasDeliveryVehicle(getInstructionManager(), node.getPosition().getX(),
//						node.getPosition().getY());
//			}
//		}
	}

	private void deployNodes() {
		for (OSMNode osmNode : objectPool.getNodes()) {
			deployNode(osmNode);
		}
	}

	private void deployWays() {
		for (OSMWay osmWay : objectPool.getWays()) {
			deployWay(osmWay);
		}
	}

	private int roadId = 1;

	private void deployWay(OSMWay osmWay) {
		long currentTime = VirtualClock.currentTime();
		
		for (int i = 0; i < osmWay.getNodes().size() - 1; i++) {
			
			OSMNode node1 = osmWay.getNodes().get(i);
			OSMNode node2 = osmWay.getNodes().get(i + 1);
			
			
			CreateInstruction instruction = null;
			if(osmWay.isOneWay()) {
				instruction = new CreateOneWayRoadInstruction(currentTime, roadId);
			}
			else {
				instruction = new CreateTwoWayRoadInstruction(currentTime, roadId); 
			}

			getInstructionManager().addInstruction(instruction);
			getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, roadId, node1.getId(), node2.getId()));

			roadId++;
		}
	}

	private void deployNode(OSMNode osmNode) {
		long currentTime = VirtualClock.currentTime();
		getInstructionManager().addInstruction(new CreateCrossroadsInstruction(currentTime, osmNode.getId()));
		
			if(osmNode.getPosition() == null)
				throw new RuntimeException("Error deploying osmNode");
			
		getInstructionManager().addInstruction(
//				new DeployConnectorInstruction<Truck, Crossroads, Road>(
//						currentTime, 
//						osmNode.getId(), 
//						osmNode.getPosition().getX(), 
//						osmNode.getPosition().getY()
//				)
				new DeployCrossroadsAndFlagsInstruction(
						currentTime, 
						osmNode.getId(), 
						osmNode.getPosition().getX(), 
						osmNode.getPosition().getY(),
						createFlag()
				)
		);
		
		if(osmNode.getPosition().getX() == 6694731329l && osmNode.getPosition().getY() == 4003729508l) {
			System.out.println("----->>>" + osmNode.getId());
		}
//		System.out.println(osmNode.getPosition().getX() + "   " + osmNode.getPosition().getY());
		
	}
	
	private String createFlag() {
		
		
		String flag = null;
		
		if(numberOfFlags <= 10000) {
//			if(randomGen.nextDouble() <= 0.03){
				flag = "#" + UUID.randomUUID().toString();
				numberOfFlags++;
//			}
		}
		
		return flag;
	}
}
