package gui.objects;

import layer.physical.events.PackageDeliveredEvent;
import layer.physical.events.PackagePickedEvent;
import framework.events.Event;
import framework.events.physical.EntityEntersConnectorOffRoadEvent;
import framework.events.physical.EntityEntersConnectorOnRoadEvent;
import framework.events.physical.EntityLeavesConnectorOffRoadEvent;
import framework.events.physical.EntityLeavesConnectorOnRoadEvent;
import framework.events.physical.PositionOnConnectionUpdatedEvent;
import framework.layer.physical.position.ContinuousPosition;

public abstract class GUIVehicle extends GUIConnectionEntity {

	public GUIVehicle(int id) {
		super(id);
	}

	@Override
	public void registerEvents() {
		super.registerEvents();
		getGui().getDispatcher().register(EntityEntersConnectorOffRoadEvent.class, this);
		getGui().getDispatcher().register(EntityEntersConnectorOnRoadEvent.class, this);
		getGui().getDispatcher().register(EntityLeavesConnectorOffRoadEvent.class, this);
		getGui().getDispatcher().register(EntityLeavesConnectorOnRoadEvent.class, this);
		getGui().getDispatcher().register(PositionOnConnectionUpdatedEvent.class, this);
		getGui().getDispatcher().register(PackagePickedEvent.class, this);
		getGui().getDispatcher().register(PackageDeliveredEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		super.handleEvent(event);
		if (event instanceof EntityEntersConnectorOffRoadEvent) {
			handleEntityEntersConnectorOffRoadEvent((EntityEntersConnectorOffRoadEvent) event);
		} else if (event instanceof EntityEntersConnectorOnRoadEvent) {
			handleEntityEntersConnectorOnRoadEvent((EntityEntersConnectorOnRoadEvent) event);
		} else if (event instanceof EntityLeavesConnectorOffRoadEvent) {
			handleEntityLeavesConnectorOffRoadEvent((EntityLeavesConnectorOffRoadEvent) event);
		} else if (event instanceof EntityLeavesConnectorOnRoadEvent) {
			handleEntityLeavesConnectorOnRoadEvent((EntityLeavesConnectorOnRoadEvent) event);
		} else if (event instanceof PositionOnConnectionUpdatedEvent) {
			handlePositionOnConnectionUpdatedEvent((PositionOnConnectionUpdatedEvent) event);
		}
	}

	private void handlePositionOnConnectionUpdatedEvent(PositionOnConnectionUpdatedEvent event) {
		if (event.getEntityId() == getId()) {
			GUIConnection connection = getGui().findSpecificGUIObject(GUIConnection.class, event.getConnectionId());
			setPosition(connection.getPositionAtDistance(event.getNewDistance(), event.getNewDirection()));
		}
	}

	private void handleEntityLeavesConnectorOnRoadEvent(EntityLeavesConnectorOnRoadEvent event) {
		if (event.getConnectionEntityId() == getId()) {
			GUIConnector connector = getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId());
			setPosition(new ContinuousPosition(connector.getPosition().getX(), connector.getPosition().getY()));
		}
	}

	private void handleEntityLeavesConnectorOffRoadEvent(EntityLeavesConnectorOffRoadEvent event) {
		if (event.getConnectionEntityId() == getId()) {
			GUIConnector connector = getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId());
			setPosition(new ContinuousPosition(connector.getPosition().getX(), connector.getPosition().getY()));
		}
	}

	private void handleEntityEntersConnectorOnRoadEvent(EntityEntersConnectorOnRoadEvent event) {
		if (event.getConnectionEntityId() == getId()) {
			GUIConnector connector = getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId());
			setPosition(new ContinuousPosition(connector.getPosition().getX(), connector.getPosition().getY()));
		}
	}

	private void handleEntityEntersConnectorOffRoadEvent(EntityEntersConnectorOffRoadEvent event) {
		if (event.getConnectionEntityId() == getId()) {
			GUIConnector connector = getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId());
			setPosition(new ContinuousPosition(connector.getPosition().getX(), connector.getPosition().getY()));
		}
	}

}
