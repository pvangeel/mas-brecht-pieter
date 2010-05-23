package pclabs3.layer.physical;


import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.ConnectorPosition;

public class SiteLocation extends Connector<NullConnectionEntity, SiteLocation, NullConnection> {

	private static final int ON_ROAD_CAPACITY = 2;
	private static final int OFF_ROAD_CAPACITY = 1;
	
	public SiteLocation() {
		super(ON_ROAD_CAPACITY, OFF_ROAD_CAPACITY);
//		EventBroker.getEventBroker().notifyAll(new CrossRoadsCreatedEvent(this));
	}

	@Override
	protected boolean canEnterOffroad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canEnterOnroad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canLeaveOffroad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean canLeaveOnroad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void executeSpecificDeploymentOptions(
			NullConnectionEntity connectionEntity,
			ConnectorPosition<NullConnectionEntity, SiteLocation, NullConnection> pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEnterOffroad(NullConnectionEntity connectionEntity,
			NullConnection fromConnection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onEnterOnroad(NullConnectionEntity connectionEntity,
			NullConnection fromConnection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLeaveOffroad(NullConnectionEntity connectionEntity,
			NullConnection toConnection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onLeaveOnroad(NullConnectionEntity connectionEntity,
			NullConnection toConnection) {
		// TODO Auto-generated method stub
		
	}

	
}
