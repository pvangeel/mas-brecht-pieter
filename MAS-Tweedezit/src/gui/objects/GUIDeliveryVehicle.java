package gui.objects;

import java.awt.Graphics;
import java.awt.Image;

import framework.graphics.SpriteStore;

public class GUIDeliveryVehicle extends GUIVehicle {

	private static final Image image = SpriteStore.get().getImage("graphics/deliverytruck.png");
	private static final int imageWidth = image.getWidth(null);
	private static final int imageHeight = image.getHeight(null);

	public GUIDeliveryVehicle(int id) {
		super(id);
	}

	@Override
	public void drawProtected(Graphics g) {
		g.drawImage(image, convertX(getPosition().getX()) - imageWidth / 2, convertY(getPosition().getY()) - imageHeight / 2, null);
	}
}
