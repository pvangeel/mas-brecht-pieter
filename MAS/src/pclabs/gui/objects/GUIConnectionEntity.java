package pclabs.gui.objects;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.events.physical.ConnectionEntityDeployedEvent;
import framework.layer.physical.position.ContinuousPosition;

public abstract class GUIConnectionEntity extends GUIPhysicalEntity {

	private final static int size = 10;
	private final static int halfSize = size / 2;

	private ContinuousPosition position;

	public GUIConnectionEntity(int id) {
		super(id);
	}

	public void setPosition(ContinuousPosition position) {
		if (position == null) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	@Override
	public ContinuousPosition getPosition() {
		if (position == null) {
			throw new IllegalStateException();
		}
		return position;
	}

	public boolean hasPosition() {
		return position != null;
	}

	@Override
	public void drawProtected(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(convertX(getPosition().getX()) - halfSize, convertY(getPosition().getY()) - halfSize, size, size);
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(ConnectionEntityDeployedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof ConnectionEntityDeployedEvent) {
			handleConnectionEntityDeployedEvent((ConnectionEntityDeployedEvent) event);
		}
	}

	private void handleConnectionEntityDeployedEvent(ConnectionEntityDeployedEvent event) {
		if (event.getEntityId() == getId()) {
			if (event.getConnectorId() != -1) {
				GUIConnector connector = getGui().findSpecificGUIObject(GUIConnector.class, event.getConnectorId());
				setPosition(new ContinuousPosition(connector.getPosition().getX(), connector.getPosition().getY()));
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}

	@Override
	public boolean isConnectedOk() {
		return hasPosition();
	}
}
