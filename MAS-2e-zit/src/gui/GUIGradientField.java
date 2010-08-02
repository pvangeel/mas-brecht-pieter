package gui;

import java.awt.Color;
import java.awt.Graphics;

import framework.events.Event;
import framework.gui.GUIObject;
import framework.layer.physical.position.ContinuousPosition;
import framework.utils.Utils;

public class GUIGradientField extends GUIObject {

	private int id;
	private final double rectSin = 0.707106781186548;
	
	private final Color gradientColor = new Color(255, 0, 0);
	private int doubleRadius;
	private int p1x;
	private int p1y;
	private ContinuousPosition position;
	private long radius;

	public GUIGradientField(int id, ContinuousPosition position, long radius) {
		super(id);
		this.id = id;
		this.position = position;
		this.radius = radius;
	}

	@Override
	public void drawProtected(Graphics g) {
		if(g.getColor() != gradientColor)
			g.setColor(gradientColor);

//		Graphics2D g2d = (Graphics2D) g;
//		int p2x = convertX(position.getX());
//		int p2y = convertY(position.getY());
//		float[] dist = {0.15f, 0.7f};
//		
//	    Color[] colors = {color1, color2};
//	    g2d.setPaint(new RadialGradientPaint(p2x, p2y, radius, dist, colors));
		if(position != null && p1x == 0 && p1y == 0) {
			int radiusProjection = (int) Math.floor(getGui().milliMeterToPixels(radius) * rectSin);
			doubleRadius = radiusProjection + radiusProjection;
			
			p1x = convertX(position.getX()) - radiusProjection;
			p1y = convertY(position.getY()) - radiusProjection;
		}
//		g.fillOval(p1x, p1y, doubleRadius, doubleRadius);
		g.drawOval(p1x, p1y, doubleRadius, doubleRadius);
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
		GUIGradientField other = (GUIGradientField) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
