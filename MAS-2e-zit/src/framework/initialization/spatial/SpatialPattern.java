package framework.initialization.spatial;

import framework.layer.physical.PhysicalStructure;
import framework.layer.physical.position.Position;

/**
 * 
 * A spatial pattern return a sequence of positions in the physical world
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class SpatialPattern<S extends PhysicalStructure<?>, Pos extends Position> {

	public abstract Pos getNextPosition(S structure);

}
