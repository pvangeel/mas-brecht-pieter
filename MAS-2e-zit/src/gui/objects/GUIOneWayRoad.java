package gui.objects;

import java.awt.Color;

public class GUIOneWayRoad extends GUIRoad {

	public GUIOneWayRoad(int id) {
		super(id);
	}
	
	@Override
	protected Color getColor() {
		return Color.red;
	}

}
