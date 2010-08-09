package layer.agent.entities;

import java.util.Collections;
import java.util.List;

import layer.devices.Trajectory;
import layer.physical.commands.DropPackage;
import layer.physical.commands.LeaveCrossroadCommand;
import layer.physical.commands.MoveForwardCommand;
import layer.physical.commands.PickPackage;
import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

import org.apache.log4j.Logger;

import framework.core.VirtualClock;
import framework.layer.agent.Agent;
import framework.layer.physical.command.move.EnterConnectorCommand;

/**
 * This agent is meant to show how to issue commands.
 * 
 * It doesn't have ANY good abstractions to create a good design.
 * 
 * THE DESIGN OF THIS AGENT IS VERY BAD, IT IS USED JUST TO SHOW TO TO ISSUE COMMANDS 
 * 
 * @author marioct
 *
 */
public class DelegateMASDeliveryAgent extends Agent {
	private static Logger logger = Logger.getLogger(DelegateMASDeliveryAgent.class);
//	private AStarRouter router;
	private Truck myTruck;
	private List<Crossroads> path;
	private long lastTime;
	private Crossroads lastLocation;
	
	public DelegateMASDeliveryAgent() {
//		this.router = new AStarRouter.DistanceBasedAStar();
		lastTime = VirtualClock.currentTime();
	}
	
	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}
	Crossroads destination = null;		

	@Override
	public void processTick(long timePassed) {
		if(myTruck == null)
			myTruck = (Truck) getDevice().getPhysicalEntity();
//
//
//		// perceive block
//		boolean perceiveOnConnector = myTruck.isOnConnector();
//		boolean perceiveOnConnection = myTruck.isOnConnection();
//		boolean allCommandsProcessed = myTruck.allCommandsProcessed();
//
////		int packIndex = (int) Math.floor(Math.random() * Environment.getAgentEnvInstance().searchPackages().size());
//		if(allCommandsProcessed) {
//			if(myTruck.getPDPPackageInfo() != null) { //truck is loaded with a package
//				destination = myTruck.getPDPPackageInfo().getDestination();
//			}
////			else {
////				if(destination == null && Environment.getAgentEnvInstance().searchPackages().size() > 0){
//////					destination = Environment.getAgentEnvInstance().searchPackages().get(0).getOrigin();
////					destination = Environment.getAgentEnvInstance().getStrongestFieldOrigin(myTruck.getPosition());
////				}
////			}
//				
//			if(path == null && destination != null) { // need to define a route to be followed to get the package
//				Trajectory t = router.calculateTrajectory(VirtualClock.currentTime(), myTruck.getConnectorPosition().getConnector(), destination);
//				if(t.getTrajectory() == null) {
//					System.out.println("===> " + myTruck.getConnectorPosition().getConnector().getPosition().getX()
//							+ " -" + myTruck.getConnectorPosition().getConnector().getPosition().getY());
////					throw new RuntimeException("EEOR");
//					destination = null;
//					path = null;
//				}
//				else
//					path = t.getTrajectory();
//				
//			} else {
//				if(path != null && path.size() > 0) {
//					if(perceiveOnConnector) {
//						Crossroads cr = path.remove(0);
//						if(!myTruck.getConnectorPosition().getConnector().equals(cr)){
//							myTruck.addCommand(new LeaveCrossroadCommand(myTruck, myTruck.getConnectorPosition().getConnector().getConnectionTo(cr)), this);
//							lastTime = VirtualClock.currentTime();
//						}
//						lastLocation = cr;
//					}
//					else {
//						if(perceiveOnConnection){
//							if (myTruck.getConnectionPosition().getConnection().isAtEnd(myTruck)) {
//								myTruck.addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(myTruck, true), this);
//							} else {
//								myTruck.addCommand(new MoveForwardCommand(myTruck), this);
//							}
//						}	
//					}
//				} else {
//					if(perceiveOnConnector) {
//						if(myTruck.getConnectorPosition().getConnector().equals(destination)) {
//							if(myTruck.getConnectorPosition().getConnector().hasPackage()) {
//								System.out.println("Picking");
//								myTruck.addCommand(new PickPackage(myTruck), this);
//								
//								lastLocation = myTruck.getConnectorPosition().getConnector();
//							}
//							if (myTruck.getPDPPackageInfo() != null) {
//								lastLocation = myTruck.getConnectorPosition().getConnector();
//								
//								System.out.println("Delivering");
//								myTruck.addCommand(new DropPackage(myTruck, myTruck.getPDPPackageInfo()), this);
//							}
//							path = null;
//							destination = null;
//						}
//						
//					}
//					else {
//						if(perceiveOnConnection){
//							if (myTruck.getConnectionPosition().getConnection().isAtEnd(myTruck)) {
//								myTruck.addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(myTruck, true), this);
//							} else {
//								myTruck.addCommand(new MoveForwardCommand(myTruck), this);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
		boolean perceiveOnConnector = myTruck.isOnConnector();
		boolean perceiveOnConnection = myTruck.isOnConnection();
		boolean allCommandsProcessed = myTruck.allCommandsProcessed();
		if(allCommandsProcessed) {
		if(path != null && path.size() > 0) {
			if(perceiveOnConnector) {
				Crossroads cr = path.remove(0);
				System.out.println("cr:"+cr.getId());
				if(!myTruck.getConnectorPosition().getConnector().equals(cr)){
					Road road = myTruck.getConnectorPosition().getConnector().getConnectionTo(cr);
					System.out.println("road = " + road.getConnector1().getId() + " to " + road.getConnector2().getId());
					myTruck.addCommand(new LeaveCrossroadCommand(myTruck, road), this);
					lastTime = VirtualClock.currentTime();
				}
				lastLocation = cr;
			}
			else {
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
		
	}

	public void giveRoute(Trajectory route) {
		if(this.path != null) {
			System.out.println("new path not accepted");
			return;
		}
		List<Crossroads> trajectory = route.getTrajectory();
		
		Collections.reverse(trajectory);
		
		this.path = trajectory;
		
		System.out.println("printing path");
		for (Crossroads crossroads : path) {
			System.out.println("crossrds id: " + crossroads.getId());
		}
		System.out.println("end printing path");
		
	}
}
