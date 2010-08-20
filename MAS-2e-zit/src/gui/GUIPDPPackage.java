package gui;

import java.awt.Graphics;
import java.awt.Image;

import layer.physical.entities.PDPPackage;

import framework.events.Event;
import framework.graphics.SpriteStore;
import framework.gui.GUIObject;
import framework.layer.physical.position.ContinuousPosition;

public class GUIPDPPackage extends GUIObject {

	private static final Image image = SpriteStore.get().getImage("graphics/deliverypackage.png");
	private static final int imageWidth = image.getWidth(null);
	private static final int imageHeight = image.getHeight(null);
	private ContinuousPosition position;
	private PDPPackage pdpPackage;
	private int id;

	public GUIPDPPackage(PDPPackage pdpPackage, ContinuousPosition position) {
		super(pdpPackage.getId());
		this.pdpPackage = pdpPackage;
		this.id = pdpPackage.getId();
		this.position = position;
	}

	@Override
	public void drawProtected(Graphics g) {
		g.drawImage(image, convertX(position.getX()) - imageWidth / 2, convertY(position.getY()) - imageHeight / 2, null);
		g.drawString("p:" + pdpPackage.getPackagePriority(), convertX(position.getX()) + 5, convertY(position.getY()) + 20);
	}

	@Override
	public boolean isConnectedOk() {
		return true;
	}

	@Override
	public void registerEvents() {
		// TODO Auto-generated method stub
		// TODO handle package PICKED and Delivered
		
	}

	@Override
	public void handleEvent(Event event) {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GUIPDPPackage other = (GUIPDPPackage) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
