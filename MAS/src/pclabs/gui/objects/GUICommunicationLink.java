package pclabs.gui.objects;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.events.deployment.CommunicationLinkAddedEvent;
import framework.events.deployment.CommunicationLinkRemovedEvent;
import framework.gui.GUIObject;

public class GUICommunicationLink extends GUIObject {

	private final long delay;
	private final double bandwidth;

	private GUIDevice device1;
	private GUIDevice device2;

	public GUICommunicationLink(int id, long delay, double bandwidth) {
		super(id);
		this.delay = delay;
		this.bandwidth = bandwidth;
	}

	public void connect(GUIDevice device1, GUIDevice device2) {
		if (device1 == null || device2 == null) {
			throw new IllegalArgumentException();
		}
		if (isConnectedOk()) {
			throw new IllegalStateException();
		}
		this.device1 = device1;
		this.device2 = device2;
	}

	public long getDelay() {
		return delay;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public GUIDevice getDevice1() {
		if (!isConnectedOk()) {
			throw new IllegalStateException();
		}
		return device1;
	}

	public GUIDevice getDevice2() {
		if (!isConnectedOk()) {
			throw new IllegalStateException();
		}
		return device2;
	}

	@Override
	protected void drawProtected(Graphics g) {
//		g.setColor(Color.red);
//		g.drawLine(convertX(getDevice1().getPosition().getX()), convertY(getDevice1().getPosition().getY()), convertX(getDevice2()
//				.getPosition().getX()), convertY(getDevice2().getPosition().getY()));
	}

	@Override
	public boolean isConnectedOk() {
		return device1 != null && device2 != null && device1.isConnectedOk() && device2.isConnectedOk();
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(CommunicationLinkAddedEvent.class, this);
		getGui().getDispatcher().register(CommunicationLinkRemovedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof CommunicationLinkAddedEvent) {
			handleCommunicationLinkAddedEvent((CommunicationLinkAddedEvent) event);
		} else if (event instanceof CommunicationLinkRemovedEvent) {
			handleCommunicationLinkRemovedEvent((CommunicationLinkRemovedEvent) event);
		}
	}

	private void handleCommunicationLinkRemovedEvent(CommunicationLinkRemovedEvent event) {
		if (event.getCommLinkId() == getId()) {
			if (getDevice1().getId() != event.getDevice1Id() || getDevice2().getId() != event.getDevice2Id()) {
				throw new IllegalStateException();
			}
			this.device1 = null;
			this.device2 = null;
		}
	}

	private void handleCommunicationLinkAddedEvent(CommunicationLinkAddedEvent event) {
		if (event.getCommunicationLinkId() == getId()) {
			GUIDevice device1 = getGui().findSpecificGUIObject(GUIDevice.class, event.getDevice1Id());
			GUIDevice device2 = getGui().findSpecificGUIObject(GUIDevice.class, event.getDevice2Id());
			connect(device1, device2);
		}
	}

}
