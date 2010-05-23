package pclabs.agentlayer.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pclabs.physicallayer.command.LeaveCrossroadCommand;
import pclabs.physicallayer.command.MoveForwardCommand;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import pclabs4.environment.Environment;
import pclabs4.layer.devices.AStarRouter;
import pclabs4.layer.devices.Trajectory;
import pclabs4.layer.devices.AStarRouter.DistanceBasedAStar;
import framework.core.SimulationCore;
import framework.core.VirtualClock;
import framework.events.EventBroker;
import framework.events.physical.ConnectorAddedEvent;
import framework.layer.agent.Agent;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.command.Command;
import framework.layer.physical.command.move.EnterConnectorCommand;
import framework.layer.physical.position.ContinuousPosition;

public class SimpleAgent_old extends Agent {
	private static Logger logger = Logger.getLogger(SimpleAgent_old.class);
	private int agentId;
	private AStarRouter router;
	private boolean hasRoute = false;
	private Truck myTruck;
	private Trajectory t;
	private List<Crossroads> path;
	
	public SimpleAgent_old(int objectId) {
		this.agentId = objectId;
		this.router = new AStarRouter.DistanceBasedAStar();
	}
	
	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processTick(long timePassed) {
		myTruck = (Truck) getDevice().getPhysicalEntity();

		
		logger.debug("Agent: " + agentId + "processingTick");
	
		// perceive block
		boolean perceiveOnConnector = myTruck.isOnConnector();
		boolean perceiveOnConnection = myTruck.isOnConnection();
		boolean allCommandsProcessed = myTruck.allCommandsProcessed();
		
		if(allCommandsProcessed) {
			
			if(perceiveOnConnection || perceiveOnConnector) {
				if(perceiveOnConnector) {
					
					if(!hasRoute && getDestinationFromAnyPackage() != null) {
						System.out.println(myTruck.getConnectorPosition().getX() + "---" + myTruck.getConnectorPosition().getY());
						
//						PhysicalConnectionStructure<Truck, Crossroads, Road> p = (PhysicalConnectionStructure<Truck, Crossroads, Road>) SimulationCore.getSimulationCore().getPhysicalLayer().getPhysicalStructure();
//						Crossroads c = (Crossroads) p.getConnectorAtPosition(getPositionFromFile());
						Crossroads c = getDestinationFromAnyPackage();
						t = router.calculateTrajectory(VirtualClock.currentTime(), myTruck.getConnectorPosition().getConnector(), c);
						path = t.getTrajectory();
						System.out.println("HERE" + t);
						hasRoute = true;
					}
					
					if(path != null && path.size() > 0) {
						Crossroads cr = path.remove(0);
						if(!myTruck.getConnectorPosition().getConnector().equals(cr)){
							myTruck.addCommand(new LeaveCrossroadCommand(myTruck, myTruck.getConnectorPosition().getConnector().getConnectionTo(cr)), this);
						}
					}
					else{
						hasRoute = false;
					}
						
					
	
				}
				
				if(perceiveOnConnection){
					if (myTruck.getConnectionPosition().getConnection().isAtEnd(myTruck)) {
						myTruck.addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(myTruck, true), this);
					} else {
						myTruck.addCommand(new MoveForwardCommand(myTruck), this);
					}
				}
			}
		}
	}

	/**
	 * Get a destination from a package. This is hardcoded and will always get the first package.
	 * The ideia of this method is tjust to 
	 * You should create a better design for this
	 * @return
	 */
	private Crossroads getDestinationFromAnyPackage() {
		if(Environment.getAgentEnvInstance().searchPackages().size() > 0) {
			System.out.println("There are packages");
			
			return Environment.getAgentEnvInstance().searchPackages().get(0).getDestination();
		}
		
		return null;

	}

//	private ContinuousPosition getPositionFromFile() {
//		
//		File f = new File("/tmp/p");
//		String line=null;
//		if(f.exists()) {
//			BufferedReader br;
//			try {
//				br = new BufferedReader(new FileReader(f));
//				line = br.readLine();
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return new ContinuousPosition(Long.valueOf(line.split(",")[0]), Long.valueOf(line.split(",")[1])); 
//		}
//		return null;
//	}
	
}
