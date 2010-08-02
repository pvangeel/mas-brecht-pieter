package framework.layer.physical;

import java.util.Collection;
import java.util.HashSet;

import framework.layer.physical.entities.MovingEntity;
import framework.layer.physical.entities.PhysicalEntity;
import framework.layer.physical.entities.Resource;

/**
 * 
 * A structure containing all the existing entities in a physical layer
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class PhysicalStructure<M extends MovingEntity<M>> {

	private PhysicalLayer<?> physicalLayer;

	private final Collection<M> movingEntities = new HashSet<M>();
	private final Collection<Resource<?>> resources = new HashSet<Resource<?>>();

	public void addMovingEntity(M movingEntity) {
		if (movingEntity == null) {
			throw new IllegalArgumentException();
		}
		movingEntities.add(movingEntity);
		movingEntity.setStructure(this);
		getPhysicalLayer().getRegistry().register(movingEntity);
	}

	public void addResource(Resource<?> resource) {
		if (resource == null) {
			throw new IllegalArgumentException();
		}
		resources.add(resource);
		resource.setStructure(this);
		getPhysicalLayer().getRegistry().register(resource);
	}

	public boolean hasMovingEntity(M entity) {
		if (entity == null) {
			throw new IllegalArgumentException();
		}
		return movingEntities.contains(entity);
	}

	public boolean hasResource(Resource<?> resource) {
		if (resource == null) {
			throw new IllegalArgumentException();
		}
		return resources.contains(resource);
	}

	public void removeMovingEntity(M movingEntity) {
		if (movingEntity == null) {
			throw new IllegalArgumentException();
		}
		movingEntities.remove(movingEntity);
		movingEntity.setStructure(null);
		getPhysicalLayer().getRegistry().unregister(movingEntity);
	}

	public void removeResource(Resource<?> resource) {
		if (resource == null) {
			throw new IllegalArgumentException();
		}
		resources.remove(resource);
		resource.setStructure(null);
		getPhysicalLayer().getRegistry().unregister(resource);
	}

	public Collection<M> getMovingEntities() {
		return movingEntities;
	}

	public Collection<Resource<?>> getResources() {
		return resources;
	}

	protected PhysicalLayer<?> getPhysicalLayer() {
		if (this.physicalLayer == null) {
			throw new IllegalStateException();
		}
		return physicalLayer;
	}

	public void setPhysicalLayer(PhysicalLayer<?> physicalLayer) {
		if (physicalLayer == null) {
			throw new IllegalArgumentException();
		}
		if (this.physicalLayer != null) {
			throw new IllegalStateException();
		}
		this.physicalLayer = physicalLayer;
	}

	public Collection<PhysicalEntity<?>> getAllPhysicalEntities() {
		HashSet<PhysicalEntity<?>> allEntities = new HashSet<PhysicalEntity<?>>();
		allEntities.addAll(movingEntities);
		allEntities.addAll(resources);
		return allEntities;
	}
}
