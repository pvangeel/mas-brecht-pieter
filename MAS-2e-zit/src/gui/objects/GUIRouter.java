package gui.objects;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.gui.GUIObject;
import framework.layer.physical.position.Position;

public abstract class GUIRouter extends GUIObject {

	private GUICommunicationCapability communicationCapability;
	private final static int radius = 1;
	private final static int diameter = 2 * radius;
	
	public GUIRouter(int id) {
		super(id);
	}
	
	public void setCommunicationCapability(GUICommunicationCapability communicationCapability) {
		if (communicationCapability == null) {
			throw new IllegalArgumentException();
		}
		if (this.communicationCapability != null) {
			throw new IllegalStateException();
		}
		this.communicationCapability = communicationCapability;
	}
	
	public GUICommunicationCapability getCommunicationCapability() {
		if(!hasCommunicationCapability()) {
			throw new IllegalStateException();
		}
		return communicationCapability;
	}

	public boolean hasCommunicationCapability() {
		return communicationCapability != null;
	}
	
	@Override
	public void drawProtected(Graphics g) {
		g.setColor(Color.black);
		g.fillOval(convertX(getPosition().getX()) - radius, convertY(getPosition().getY()) - radius, diameter, diameter);
	}
	
	@Override
	public boolean isConnectedOk() {
		return hasCommunicationCapability() && getCommunicationCapability().isConnectedOk();
	}
	
	protected Position getPosition() {
		return getCommunicationCapability().getDevice().getEntity().getPosition();
	}

	@Override
	public void registerEvents() {

	}

	@Override
	public void handleEvent(Event event) {

	}

}
