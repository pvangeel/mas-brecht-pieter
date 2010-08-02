package framework.layer.physical.position;

/**
 * possible directions on a Connection
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public enum Direction {

	TO_CONNECTOR_1 {
		@Override
		public Direction opposite() {
			return TO_CONNECTOR_2;
		}
	},
	TO_CONNECTOR_2 {
		@Override
		public Direction opposite() {
			return TO_CONNECTOR_1;
		}
	};

	public abstract Direction opposite();

}
