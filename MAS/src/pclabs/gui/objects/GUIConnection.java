package pclabs.gui.objects;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.events.physical.ConnectionAddedEvent;
import framework.events.physical.ConnectionRemovedEvent;
import framework.gui.GUIObject;
import framework.layer.physical.position.ContinuousPosition;
import framework.layer.physical.position.Direction;

public class GUIConnection extends GUIObject {

	private GUIConnector connector1;
	private GUIConnector connector2;

	public GUIConnection(int id) {
		super(id);
	}

	public void connect(GUIConnector connector1, GUIConnector connector2) {
		if (connector1 == null || connector2 == null) {
			throw new IllegalArgumentException();
		}
		if (isConnectedOk()) {
			throw new IllegalStateException();
		}
		this.connector1 = connector1;
		this.connector2 = connector2;
	}

	public GUIConnector getConnector1() {
		if (connector1 == null) {
			throw new IllegalStateException();
		}
		return connector1;
	}

	public GUIConnector getConnector2() {
		if (connector2 == null) {
			throw new IllegalStateException();
		}
		return connector2;
	}

	@Override
	protected void drawProtected(Graphics g) {
		g.setColor(getColor());
		g.drawLine(convertX(getConnector1().getPosition().getX()), convertY(getConnector1().getPosition().getY()), convertX(getConnector2()
				.getPosition().getX()), convertY(getConnector2().getPosition().getY()));
	}
	
	protected Color getColor() {
		return Color.black;
	}

	@Override
	public boolean isConnectedOk() {
		return connector1 != null && connector2 != null && getConnector1().hasPosition() && getConnector2().hasPosition();
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(ConnectionAddedEvent.class, this);
		getGui().getDispatcher().register(ConnectionRemovedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof ConnectionAddedEvent) {
			handleConnectionAddedEvent((ConnectionAddedEvent) event);
		} else if (event instanceof ConnectionRemovedEvent) {
			handleConnectionRemovedEvent((ConnectionRemovedEvent) event);
		}
	}

	private void handleConnectionRemovedEvent(ConnectionRemovedEvent event) {
		if (event.getConnectionId() == getId()) {
			getGui().removeGUIObject(this);
		}
	}

	private void handleConnectionAddedEvent(ConnectionAddedEvent event) {
		if (event.getConnectionId() == getId()) {
			connect(getGui().findSpecificGUIObject(GUIConnector.class, event.getConnector1Id()), getGui().findSpecificGUIObject(
					GUIConnector.class, event.getConnector2Id()));
		}
	}

	public ContinuousPosition getPositionAtDistance(long newDistance, Direction direction) {
		ContinuousPosition p1;
		ContinuousPosition p2;
		if (direction == Direction.TO_CONNECTOR_2) {
			p1 = getConnector1().getPosition();
			p2 = getConnector2().getPosition();
		} else if (direction == Direction.TO_CONNECTOR_1) {
			p1 = getConnector2().getPosition();
			p2 = getConnector1().getPosition();
		} else {
			throw new IllegalStateException();
		}
		long[] proj = p1.getProjectedXY(p2, newDistance);
		return new ContinuousPosition(proj[0], proj[1]);
	}

}
