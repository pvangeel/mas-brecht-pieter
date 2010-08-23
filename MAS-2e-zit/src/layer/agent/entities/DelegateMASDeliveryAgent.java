package layer.agent.entities;

import java.awt.GridLayout;
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

import framework.core.VirtualClock;
import framework.layer.agent.Agent;
import framework.layer.physical.command.move.EnterConnectorCommand;
import framework.utils.Utils;

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
	private static final long evaporationDelta = Utils.minutesToMicroSeconds(10);
	private long evaporationStart = 0;
	private Trajectory currentTrajectory;
	private JTextArea bovensteText;
	private JTextArea ondersteText;
	private JTextArea superondersteText;
	private boolean onRouteToDestination = false;
	private AStarRouter router = new AStarRouter.DistanceBasedAStar();
	
	
	private boolean withVisuals = false;

	public DelegateMASDeliveryAgent() {
		if(!withVisuals) return;
		JFrame jFrame = new JFrame("VehicleAgent");
		jFrame.setSize(400, 500);
		jFrame.setLocation(850, 400);
		jFrame.setLayout(new GridLayout(3, 1));
		bovensteText = new JTextArea();
		ondersteText = new JTextArea();
		superondersteText = new JTextArea();
		jFrame.add(bovensteText);
		jFrame.add(ondersteText);
		jFrame.add(superondersteText);
		jFrame.setVisible(true);
	}

	@Override
	public void executeDeploymentOptions() {
		// TODO Auto-generated method stub
	}

	@Override
	public void processTick(long timePassed) {
		if(myTruck == null) myTruck = (Truck) getDevice().getPhysicalEntity();
		
		checkForBetterRoute();
		checkEvaporation();
		drive();
		updateVenster();
	}

	private void checkForBetterRoute() {
		if(onRouteToDestination) return;
		if(!myTruck.isOnConnector()) return;

		TreeSet<Trajectory> suggestedRoutes = myTruck.getConnectorPosition().getConnector().getSuggestedRoutes();
		
		boolean hasBetter = hasBetterRoute(suggestedRoutes);

		if(hasBetter && tryToReserveBest(suggestedRoutes)){
			restoreEvaporation();
		}
	}

	private boolean hasBetterRoute(TreeSet<Trajectory> suggestedRoutes) {
		if(suggestedRoutes.size() == 0) return false;
		if(currentTrajectory == null) return true;

		return suggestedRoutes.first().isBetter(currentTrajectory);
	}

	//TODO nog maken
	private int timesChangedIntention;
	
	private boolean tryToReserveBest(TreeSet<Trajectory> suggestedRoutes) {
		//hasBetter == true
		if(currentTrajectory != null){
			//remove trajectories that are worse
			while(currentTrajectory.isBetter(suggestedRoutes.last())){
				suggestedRoutes.pollLast();
			}
		}
		
		while(!suggestedRoutes.isEmpty()){
			if(suggestedRoutes.first().getPdpPackage().reserve(this, suggestedRoutes.first())){
				currentTrajectory = new Trajectory(suggestedRoutes.first());
				return true;
			}
			suggestedRoutes.pollFirst();
		}
		return false;
	}

	private void restoreEvaporation() {
		evaporationStart = VirtualClock.currentTime();
	}

	private void checkEvaporation() {
		if(onRouteToDestination) return;
		if(currentTrajectory == null) return;
		if(VirtualClock.currentTime() - evaporationStart > evaporationDelta){
			currentTrajectory = null;
			nbTimesAfgedanktDoorPakje++;
			updateVenster();
		}
	}
	
	private int nbTimesAfgedanktDoorPakje;

	private void drive() {
		boolean perceiveOnConnector = myTruck.isOnConnector();
		boolean perceiveOnConnection = myTruck.isOnConnection();
		boolean allCommandsProcessed = myTruck.allCommandsProcessed();

		if(!allCommandsProcessed) return;
		
		if(perceiveOnConnection) handleRoad();
		
		if(currentTrajectory == null) return;
		if(perceiveOnConnector) handleCrossroads();
	}

	private void handleRoad() {
		if (myTruck.getConnectionPosition().getConnection().isAtEnd(myTruck)) {
			myTruck.addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(myTruck, true), this);
		} else {
			myTruck.addCommand(new MoveForwardCommand(myTruck), this);
		}
	}

	private void handleCrossroads() {
		if(pickUpOrDrop()) return;
		
		//checken of reeds pakje ligt op deze plek waarvan de prioriteit hoger is of gelijk aan huidige route
		//pakje is al opgepakt en huidige route is naar het volgende pakje
		if(!myTruck.hasPackage() && myTruck.getConnectorPosition().getConnector().hasPackage()){
			if(myTruck.getConnectorPosition().getConnector().getPackage().getPackagePriority() >= currentTrajectory.getPdpPackage().getPackagePriority()){
				currentTrajectory = new Trajectory(myTruck.getConnectorPosition().getConnector().getPackage());
				pickPackage();
			}
		}
		
		//Indien de truck op een crossroads staat en net een nieuwe trajectory heeft gekregen is de eerste in de trajectory nog de crossroads waar hij op staat
		Crossroads cr = currentTrajectory.getAndRemoveFirst();
		if(myTruck.getConnectorPosition().getConnector().equals(cr)) return;

		Road road = myTruck.getConnectorPosition().getConnector().getConnectionTo(cr);
		myTruck.addCommand(new LeaveCrossroadCommand(myTruck, road), this);
	}

	private boolean pickUpOrDrop(){
		if(currentTrajectory.size() != 0) return false;

		if(onRouteToDestination){
			dropPackage();
		}else{
			if(!myTruck.getConnectorPosition().getConnector().hasPackage()) { //Dit kan voorvallen als een truck een route heeft opgepikt die moest ge-evaporate worden
				currentTrajectory = null;
			}else{
				pickPackage();
			}
		}
		return true;
	}

	private void dropPackage() {
		//deze getPDPPackageInfo is soms null! edit:al lang niet meer gebeurd
		myTruck.addCommand(new DropPackage(myTruck, myTruck.getPDPPackageInfo()), this);
		onRouteToDestination = false;
		currentTrajectory = null;
	}

	private void pickPackage() {
		myTruck.addCommand(new PickPackage(myTruck), this);
		onRouteToDestination = true;
		currentTrajectory = router.calculateTrajectory(currentTrajectory.getPdpPackage(), VirtualClock.currentTime(), currentTrajectory.getPdpPackage().getOrigin(), currentTrajectory.getPdpPackage().getDestination());
	}



	public boolean confirmTruck(PDPPackage pdpPackage){
		if(currentTrajectory == null) return false;
		if(pdpPackage == currentTrajectory.getPdpPackage()){
			restoreEvaporation();
			return true;
		}
		return false;
	}

//	public void suggestRoute(Trajectory route) {
//		suggestedRoutes.add(route);
//		updateVenster();
//	}

	private void updateVenster() {
		if(!withVisuals) return;
		ondersteText.setText("");
		ondersteText.setText(  evaporationDelta - (VirtualClock.currentTime() - evaporationStart) + "");
		if(currentTrajectory == null){
			bovensteText.setText("geen route");
		}else{
			bovensteText.setText(currentTrajectory.toString());
		}
		superondersteText.setText(nbTimesAfgedanktDoorPakje + "");
	}

	public Trajectory getCurrentRoute() {
		return currentTrajectory;
		//TODO: check verschillende tijdslijnen
	}

	public boolean onRouteTo(PDPPackage pdpPackage) {
		if(currentTrajectory == null) return false;
		return currentTrajectory.getPdpPackage() == pdpPackage;
	}
}
