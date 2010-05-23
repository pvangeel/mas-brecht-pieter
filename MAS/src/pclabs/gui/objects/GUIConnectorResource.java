package pclabs.gui.objects;

import java.awt.Graphics;

import framework.events.Event;
import framework.events.physical.ResourceDeployedEvent;
import framework.layer.physical.position.ContinuousPosition;

public abstract class GUIConnectorResource extends GUIResource {

	private GUIConnector connector;

	public GUIConnectorResource(int id) {
		super(id);
	}

	public GUIConnector getConnector() {
		if (connector == null) {
			throw new IllegalStateException();
		}
		return connector;
	}

	@Override
	public ContinuousPosition getPosition() {
		return getConnector().getPosition();
	}

	@Override
	protected void drawProtected(Graphics g) {
	}

	@Override
	public boolean isConnectedOk() {
		return isDeployedOnConnector() && getConnector().isConnectedOk();
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(ResourceDeployedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof ResourceDeployedEvent) {
			handleResourceDeployedEvent((ResourceDeployedEvent) event);
		} else {
			throw new IllegalStateException();
		}
	}

	private void handleResourceDeployedEvent(ResourceDeployedEvent event) {
		if (event.getResourceId() == getId()) {
			deployOnConnector(getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId()));
		}
	}

	private boolean isDeployedOnConnector() {
		return connector != null;
	}

	private void deployOnConnector(GUIConnector connector) {
		if (isDeployedOnConnector()) {
			throw new IllegalStateException();
		}
		if (connector == null) {
			throw new IllegalArgumentException();
		}
		this.connector = connector;
	}

}
