package gui.objects;

import java.awt.Graphics;
import java.awt.Image;

import layer.physical.events.PackageDeliveredEvent;
import layer.physical.events.PackagePickedEvent;

import framework.events.Event;
import framework.graphics.SpriteStore;
import framework.layer.physical.entities.ConnectionEntity;

public class GUIDeliveryVehicle extends GUIVehicle {

	private static final Image image = SpriteStore.get().getImage("graphics/deliverytruck.png");
	private static final int imageWidth = image.getWidth(null);
	private static final int imageHeight = image.getHeight(null);
	
	boolean packagePicked = false;

	public GUIDeliveryVehicle(int id) {
		super(id);
	}

	@Override
	public void drawProtected(Graphics g) {
		g.drawImage(image, convertX(getPosition().getX()) - imageWidth / 2, convertY(getPosition().getY()) - imageHeight / 2, null);
		if(packagePicked)
			g.drawString("X", convertX(getPosition().getX()) - imageWidth / 2, convertY(getPosition().getY()) - imageHeight / 2);
	}
	
	@Override
	public void handleEvent(Event event) {
		super.handleEvent(event);
		if(event instanceof PackagePickedEvent){
			if(((PackagePickedEvent) event).getTruck().getId() == getId())
				packagePicked = true;
		}
			
		if(event instanceof PackageDeliveredEvent)
			if(((PackageDeliveredEvent) event).getTruck().getId() == getId())
				packagePicked = false;
	}
}
