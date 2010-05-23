package pclabs.gui.objects;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.events.physical.ConnectorAddedEvent;
import framework.gui.GUIObject;
import framework.layer.physical.position.ContinuousPosition;

public abstract class GUIConnector extends GUIObject {

	private final static int radius = 1;
	private final static int diameter = 2 * radius;

	private ContinuousPosition position;

	public GUIConnector(int id) {
		super(id);
	}

	public void setPosition(ContinuousPosition position) {
		if (position == null) {
			throw new IllegalArgumentException();
		}
		this.position = position;
	}

	public ContinuousPosition getPosition() {
		if (position == null) {
			throw new IllegalStateException();
		}
		return position;
	}

	@Override
	protected void drawProtected(Graphics g) {
		g.setColor(Color.black);
		g.drawOval(convertX(getPosition().getX()) - radius, convertY(getPosition().getY()) - radius, diameter, diameter);
	}

	@Override
	public boolean isConnectedOk() {
		return hasPosition();
	}

	public boolean hasPosition() {
		return position != null;
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(ConnectorAddedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof ConnectorAddedEvent) {
			handleConnectorAddedEvent((ConnectorAddedEvent) event);
		}
	}

	private void handleConnectorAddedEvent(ConnectorAddedEvent event) {
		if (event.getConnectorId() == getId()) {
			setPosition(new ContinuousPosition(event.getPositionX(), event.getPositionY()));
		}
	}

}
