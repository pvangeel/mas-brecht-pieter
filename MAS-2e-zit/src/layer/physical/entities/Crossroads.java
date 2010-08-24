package layer.physical.entities;


import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import layer.devices.EvaporationTrajectory;
import layer.devices.Trajectory;
import layer.physical.events.CrossRoadsCreatedEvent;
import layer.physical.events.PackageCreatedEvent;
import layer.physical.events.PackageDeliveredEvent;
import layer.physical.events.PackagePickedEvent;
import environment.Environment;
import framework.events.EventBroker;
import framework.layer.TickListener;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.ConnectorPosition;

public class Crossroads extends Connector<Truck, Crossroads, Road> implements TickListener {

	private String flag;
	
	private PDPPackage pdpPackage;
	
	private JTextArea bovensteText;
	private static final int ON_ROAD_CAPACITY = 2;
	private static final int OFF_ROAD_CAPACITY = 1;
	
	public String toString(){
		return "cr" + getId();
	}
	
	private boolean withVisuals = false;
	
	
	public Crossroads() {
		super(ON_ROAD_CAPACITY, OFF_ROAD_CAPACITY);
		EventBroker.getEventBroker().notifyAll(new CrossRoadsCreatedEvent(this));
		createIndividualWindow();
	}
	
	private void createIndividualWindow() {
		if(!withVisuals) return;
		JFrame jFrame = new JFrame("Crossroads: " + getId());
		jFrame.setSize(250, 100);
		jFrame.setLocation(850, 400);
		jFrame.setLayout(new GridLayout(1, 1));
		bovensteText = new JTextArea();
		jFrame.add(bovensteText);
		jFrame.setVisible(true);
	}

	@Override
	protected boolean canEnterOffroad() {
		return true;
	}

	@Override
	protected boolean canEnterOnroad() {
		return true;
	}

	@Override
	protected boolean canLeaveOffroad() {
		return true;
	}

	@Override
	protected boolean canLeaveOnroad() {
		return true;
	}

	@Override
	protected void executeSpecificDeploymentOptions(Truck connectionEntity, ConnectorPosition<Truck, Crossroads, Road> pos) {
	}

	@Override
	protected void onEnterOffroad(Truck connectionEntity, Road fromConnection) {

	}

	@Override
	protected void onEnterOnroad(Truck connectionEntity, Road fromConnection) {
	}

	@Override
	protected void onLeaveOffroad(Truck connectionEntity, Road toConnection) {
	}

	@Override
	protected void onLeaveOnroad(Truck connectionEntity, Road toConnection) {
	}

	public String getFlag() {
		return flag;
	}
	
	public void catchFlag() {
		flag = null;
	}
	
	public void setFlag(String flagCode) {
		this.flag = flagCode;
	}

	/**
	 * Calculates the euclidean distance based on the latitude/longitude coordinates
	 * 
	 * @param end
	 * @return
	 */
	public long distanceTo(Crossroads end) {
		return getPosition().getDistanceTo(end.getPosition());
	}

	/**
	 * By convention, this method should be called just by a command
	 */
	public PDPPackage pickPackage(Truck truck) {
		PDPPackage ret = this.pdpPackage; //esta com NULL por aqui
		this.pdpPackage = null;
		if( ret != null){
			Environment.getInstance().pickPackage(ret.getDTO());
			EventBroker.getEventBroker().notifyAll(new PackagePickedEvent(ret, ret.getDestination().getPosition(), truck));
			ret.packagePicked();
		}
		return ret;
	}
	
	public boolean hasPackage() {
		if(pdpPackage != null)
			return true;
		else
			return false;
	}
	
	public PDPPackage getPackage(){
		return pdpPackage;
	}
	
	
	
	/**
	 * This method is used just to inform the environment about a delivery
	 */
	public void receivePackage(PDPPackage p) {
		Environment.getInstance().deliverPackage(p.getDTO());
		EventBroker.getEventBroker().notifyAll(new PackageDeliveredEvent(p.getId(), getOnroadEntities().getFirst()));
		p.packageDelivered();
	}

	public void addPackage(PDPPackage p) {
		this.pdpPackage = p;
		Environment.getInstance().addPackage(p.getDTO());
		EventBroker.getEventBroker().notifyAll(new PackageCreatedEvent(p, this.getPosition()));
		p.packageAdded();
	}
	
	private void updateVenster() {
		if(!withVisuals) return;
		bovensteText.setText("");
		for (Trajectory trajectory : suggestedRoutes) {
			bovensteText.append(trajectory + "\n");
		}
	}

	public void suggestRoute(Trajectory route) {
		Trajectory trajectory = getPathWithSameDestination(route);
		if(trajectory == null){
			addRouteToSuggestedRoutes(route);
		}else{
			if(route.isBetter(trajectory)){
				suggestedRoutes.remove(trajectory);
				addRouteToSuggestedRoutes(route);
			}else{
				((EvaporationTrajectory) trajectory).restoreEvaporation();
			}
		}
	}

	private void addRouteToSuggestedRoutes(Trajectory route) {
		suggestedRoutes.add(new EvaporationTrajectory(route));
		updateVenster();
	}

	private Trajectory getPathWithSameDestination(Trajectory route) {
		for (Trajectory trajectory : suggestedRoutes) {
			if(trajectory.getPdpPackage() == route.getPdpPackage()){
				return trajectory;
			}
		}
		return null;
	}

	private TreeSet<Trajectory> suggestedRoutes = new TreeSet<Trajectory>();

	public TreeSet<Trajectory> getSuggestedRoutes() {
		return (TreeSet<Trajectory>) suggestedRoutes.clone();
	}

	@Override
	public void processTick(long timePassed) {
		List<Trajectory> list = new ArrayList<Trajectory>();
		for (Trajectory trajectory : suggestedRoutes) {
			if(((EvaporationTrajectory) trajectory).evaporate()) list.add(trajectory);
//			if(trajectory.getPdpPackage().isPackagePicked()) list.add(trajectory);
		}
		for (Trajectory trajectory : list) {
			suggestedRoutes.remove(trajectory);
		}
		updateVenster();
	}

	

}
