package layer.physical.entities;


import layer.physical.events.CrossRoadsCreatedEvent;
import layer.physical.events.PackageCreatedEvent;
import layer.physical.events.PackageDeliveredEvent;
import layer.physical.events.PackagePickedEvent;
import environment.Environment;
import framework.events.EventBroker;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.ConnectorPosition;

public class Crossroads extends Connector<Truck, Crossroads, Road> {

	private String flag;
	private PDPPackage pdpPackage;
	private static final int ON_ROAD_CAPACITY = 2;
	private static final int OFF_ROAD_CAPACITY = 1;
	
	public String toString(){
		return "cr" + getId();
	}
	
	public Crossroads() {
		super(ON_ROAD_CAPACITY, OFF_ROAD_CAPACITY);
		EventBroker.getEventBroker().notifyAll(new CrossRoadsCreatedEvent(this));
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
			EventBroker.getEventBroker().notifyAll(new PackagePickedEvent(ret.getId(), ret.getDestination().getPosition(), truck));
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
		EventBroker.getEventBroker().notifyAll(new PackageCreatedEvent(p.getId(), this.getPosition()));
		p.packageAdded();
	}

	
}
