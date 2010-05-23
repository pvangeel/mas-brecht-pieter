package framework.initialization.spatial.continuous;

import framework.initialization.InitializationDirector;
import framework.initialization.TimePattern;
import framework.layer.physical.PhysicalContinuousStructure;
import framework.layer.physical.position.ContinuousPosition;

public abstract class ContinuousPhysicalInitializationDirector extends InitializationDirector<PhysicalContinuousStructure<?>> {

	/**
	 * @pre	param spatialPattern must be a spatial pattern that returns positions of type ContinuousPosition.
	 */
	public ContinuousPhysicalInitializationDirector(TimePattern timePattern, ContinuousSpatialPattern spatialPattern) {
		super(timePattern, spatialPattern);
	}

	@Override
	protected void createAndDeploy() {
		ContinuousPosition position = (ContinuousPosition) getSpatialPattern().getNextPosition(getPhysicalStructure());
		deployNewEntityAtPosition(position);
	}

	protected abstract void deployNewEntityAtPosition(ContinuousPosition position);

}
