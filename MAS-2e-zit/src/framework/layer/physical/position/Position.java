package framework.layer.physical.position;

public abstract class Position {

	public abstract long getX();

	public abstract long getY();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + getX() + ":" + getY() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position c = (Position) obj;
			return (c.getX() == getX() && c.getY() == getY());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public long getDistanceTo(Position position) {
		if (position == null) {
			throw new IllegalArgumentException();
		}
		return (long) Math.round(Math.sqrt(Math.pow(getX() - position.getX(), 2) + Math.pow(getY() - position.getY(), 2)));
	}

	public long[] getProjectedXY(Position toPosition, long distance) {
		double angle = getAngle(toPosition);
		long xDiff;
		long yDiff;
		if (angle == Double.NaN) {
			xDiff = 0;
			yDiff = 0;
		} else {
			xDiff = (long) Math.round(distance * Math.cos(angle));
			yDiff = (long) Math.round(distance * Math.sin(angle));
		}
		long[] proj = new long[2];
		proj[0] = getX() + xDiff;
		proj[1] = getY() + yDiff;
		return proj;
	}

	public double getAngle(Position endPosition) {
		return getAngle(getX(), getY(), endPosition.getX(), endPosition.getY());
	}

	private double getAngle(double x0, double y0, double x1, double y1) {
		double angle = 0;
		if (x0 != x1) {
			angle = Math.atan((y1 - y0) / (x1 - x0));
			if (Double.isNaN(angle)) {
				System.out.println("MathX.getAngle. angle berekend door ATAN is NaN");
			}
		} else {
			if (y1 > y0) {
				return Math.PI / 2;
			} else if (y1 < y0) {
				return -Math.PI / 2;
			} else {
				// Zowel x als y zijn dezelfde dus de hoek is onbepaald.
				return Double.NaN;
			}
		}
		// check voor kwadrant 2 en 3
		if (x1 < x0) {
			if (y1 >= y0) {
				// 2de kwadrant
				return Math.PI + angle;
			} else {
				// 3de kwadrant
				return -Math.PI + angle;
			}
		} else {
			return angle;
		}
	}

}
