package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * 
 * An entity is executing his failsafe mechanism
 *
 */
public class ExecutingFailSafeEvent extends Event {

	private int entityId;

	public ExecutingFailSafeEvent(PhysicalEntity<?> physicalEntity) {
		this.entityId = physicalEntity.getId();
	}
	
	protected ExecutingFailSafeEvent() {
	}

	public int getEntityId() {
		return entityId;
	}

	@Override
	public String toString() {
		return "Entity " + entityId + " is executing his failsafe mechanism";
	}
	
}
