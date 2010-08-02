package gui.objects;

import framework.gui.GUIObject;
import framework.layer.physical.position.ContinuousPosition;

public abstract class GUIPhysicalEntity extends GUIObject {

	public GUIPhysicalEntity(int id) {
		super(id);
	}
	
	public abstract ContinuousPosition getPosition();

}
