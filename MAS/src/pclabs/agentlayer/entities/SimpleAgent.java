package pclabs.agentlayer.entities;

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
import pclabs4.layer.physical.commands.DropPackage;
import pclabs4.layer.physical.commands.PickPackage;
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
public class SimpleAgent extends Agent {
	private static Logger logger = Logger.getLogger(SimpleAgent_old.class);
	private AStarRouter router;
	private Truck myTruck;
	private List<Crossroads> path;
	
	public SimpleAgent() {
		this.router = new AStarRouter.DistanceBasedAStar();
	}
	
	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processTick(long timePassed) {
		if(myTruck == null)
			myTruck = (Truck) getDevice().getPhysicalEntity();

		// perceive block
		boolean perceiveOnConnector = myTruck.isOnConnector();
		boolean perceiveOnConnection = myTruck.isOnConnection();
		boolean allCommandsProcessed = myTruck.allCommandsProcessed();
		Crossroads destination = null;
		
		if(allCommandsProcessed) {
			if(myTruck.getPDPPackageInfo() != null) { //truck is loaded with a package
				destination = myTruck.getPDPPackageInfo().getDestination();
			}
			else {
				if(destination == null && Environment.getAgentEnvInstance().searchPackages().size() > 0)
					destination = Environment.getAgentEnvInstance().searchPackages().get(0).getOrigin();
			}
				
			if(path == null && destination != null) { // need to define a route to be followed to get the package
				Trajectory t = router.calculateTrajectory(VirtualClock.currentTime(), myTruck.getConnectorPosition().getConnector(), destination);
				path = t.getTrajectory();
				
			} else {
				if(path != null && path.size() > 0) {
					if(perceiveOnConnector) {
						Crossroads cr = path.remove(0);
						if(!myTruck.getConnectorPosition().getConnector().equals(cr)){
							myTruck.addCommand(new LeaveCrossroadCommand(myTruck, myTruck.getConnectorPosition().getConnector().getConnectionTo(cr)), this);
						}
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
				} else {
					if(perceiveOnConnector) {
						if(myTruck.getConnectorPosition().getConnector().hasPackage() && myTruck.getConnectorPosition().getConnector().equals(destination)) {
							System.out.println("Picking");
							myTruck.addCommand(new PickPackage(myTruck), this);
							destination = null;
							path = null;
						} 
						if (myTruck.getPDPPackageInfo() != null && myTruck.getConnectorPosition().getConnector().equals(destination)) {
							System.out.println("Delivering");
							myTruck.addCommand(new DropPackage(myTruck, myTruck.getPDPPackageInfo()), this);
							destination = null;
							path = null;
						}
						
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
	}
}
