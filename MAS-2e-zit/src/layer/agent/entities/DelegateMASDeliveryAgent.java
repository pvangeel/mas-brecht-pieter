package layer.agent.entities;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import layer.devices.AStarRouter;
import layer.devices.Trajectory;
import layer.physical.commands.DropPackage;
import layer.physical.commands.LeaveCrossroadCommand;
import layer.physical.commands.MoveForwardCommand;
import layer.physical.commands.PickPackage;
import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
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
	private Truck myTruck;
	

	public DelegateMASDeliveryAgent() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(400, 500);
		jFrame.setLocation(800, 400);
		jFrame.setLayout(new GridLayout(2, 1));
		bovensteText = new JTextArea();
		ondersteText = new JTextArea();
		jFrame.add(bovensteText);
		jFrame.add(ondersteText);
		jFrame.setVisible(true);
	}

	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub

	}

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
		
		
		checkForBetterRoute();
		drive();
//		updateVenster();
	}

	private void checkForBetterRoute() {
		if(onRouteToDestination){
			suggestedRoutes.clear();
			return;
		}
		boolean hasBetter = hasBetterRoute();
		if(currentTrajectory == null || hasBetter){
			boolean reserved = tryToReserveBest();
			if(reserved){
				restoreEvaporation();
			}
		}
		
		decreaseEvaporation();
		suggestedRoutes.clear();
	}

	private boolean tryToReserveBest() {
		if(currentTrajectory != null){
			//remove trajectories that are worse
			while(currentTrajectory.isBetter(suggestedRoutes.last())){
				suggestedRoutes.pollLast();
			}
		}
		while(!suggestedRoutes.isEmpty()){
			if(suggestedRoutes.first().getPdpPackage().reserve(this, suggestedRoutes.first())){
				currentTrajectory = suggestedRoutes.first();
				System.out.println("Better route reserved");
				return true;
			}
			suggestedRoutes.pollFirst();
		}
		return false;
	}

	private void decreaseEvaporation() {
		evaporation--;
		if(evaporation == 0){
			currentTrajectory = null;
		}
	}
	
	private void restoreEvaporation() {
		evaporation = evaporationMAX;
	}

	private boolean hasBetterRoute() {
		if(suggestedRoutes.size() != 0){
			if(currentTrajectory == null) return true;
			if(suggestedRoutes.first().isBetter(currentTrajectory))
				return true;
			else
				return false;
		}
		return false;
	}

	private void drive() {
		boolean perceiveOnConnector = myTruck.isOnConnector();
		boolean perceiveOnConnection = myTruck.isOnConnection();
		boolean allCommandsProcessed = myTruck.allCommandsProcessed();

		if(!allCommandsProcessed)
			return;
		//TODO: als trucks elkaar niet kunnen inhalen, moeten trucks random blijven rijden
		if(currentTrajectory == null)
			return;
			
		if(perceiveOnConnector) {
			if(currentTrajectory.size() == 0){
				//TODO pick up/drop package
				
				if(onRouteToDestination){
					myTruck.addCommand(new DropPackage(myTruck, myTruck.getPDPPackageInfo()), this);
					onRouteToDestination = false;
					currentTrajectory = null;
					return;
				}else{
					myTruck.addCommand(new PickPackage(myTruck), this);
					restoreEvaporation();
					onRouteToDestination = true;
					currentTrajectory = router.calculateTrajectory(currentTrajectory.getPdpPackage(), VirtualClock.currentTime(), currentTrajectory.getPdpPackage().getOrigin(), currentTrajectory.getPdpPackage().getDestination());
					return;
				}
			}
			Crossroads cr = currentTrajectory.getAndRemoveFirst();
			if(myTruck.getConnectorPosition().getConnector().equals(cr)) return;
			
			Road road = myTruck.getConnectorPosition().getConnector().getConnectionTo(cr);
			myTruck.addCommand(new LeaveCrossroadCommand(myTruck, road), this);
			
			return;
		}

		if(perceiveOnConnection){
			if (myTruck.getConnectionPosition().getConnection().isAtEnd(myTruck)) {
				myTruck.addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(myTruck, true), this);
			} else {
				myTruck.addCommand(new MoveForwardCommand(myTruck), this);
			}
		}
	}

	private static final long evaporationMAX = 4000;
	private long evaporation = evaporationMAX;
	private TreeSet<Trajectory> suggestedRoutes = new TreeSet<Trajectory>();
	private Trajectory currentTrajectory;
	private JTextArea bovensteText;
	private JTextArea ondersteText;
	private boolean onRouteToDestination = false;
	private AStarRouter router = new AStarRouter.DistanceBasedAStar();

	public boolean confirmTruck(PDPPackage pdpPackage){
		if(currentTrajectory == null) return false;
		if(pdpPackage == currentTrajectory.getPdpPackage()){
			restoreEvaporation();
			return true;
		}
		return false;
	}

	public void suggestRoute(Trajectory route) {
		suggestedRoutes.add(route);
		updateVenster();
	}

	private void updateVenster() {
		ondersteText.setText("");
		for (Trajectory traj : suggestedRoutes) {
			ondersteText.append(traj.toString() + "\n");
		}
		if(currentTrajectory == null){
			bovensteText.setText("geen route");
		}else{
			bovensteText.setText(currentTrajectory.toString());
		}
	}

	public Trajectory getCurrentRoute() {
		return currentTrajectory;
		//TODO: check verschillende tijdslijnen
	}
}
