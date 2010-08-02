package framework.layer.physical.position;

import framework.layer.physical.position.Position;

/**
 * 
 * A position based on longitude and latitude
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class LLPosition extends Position {

	private static final long earthRadius = 6367449000L;

	private double latitude;
	private double longitude;

	private long x;
	private long y;

	public LLPosition(double latitude, double longitude) {
		if (latitude < -90 || latitude > 90 || longitude < -90 || longitude > 90) {
			throw new IllegalArgumentException();
		}
		this.latitude = latitude;
		this.longitude = longitude;
		updateCartesian();
	}

	private void updateCartesian() {
		if (latitude >= 0 && longitude >= 0) {
			updateNE();
		} else if (latitude >= 0 && longitude < 0) {
			updateNW();
		} else if (latitude < 0 && longitude >= 0) {
			updateSE();
		} else {
			updateSW();
		}
	}

	private void updateSW() {
		normalCalculations(-1 * latitude, -1 * longitude);
		x = earthRadius - x;
		y = earthRadius + (earthRadius - y);
	}

	private void updateSE() {
		normalCalculations(-1 * latitude, longitude);
		x = earthRadius+x;
		y = earthRadius + (earthRadius - y);
	}

	private void updateNW() {
		normalCalculations(latitude, -1 * longitude);
		x = earthRadius - x;
	}

	private void updateNE() {
		normalCalculations(latitude, longitude);
		x = earthRadius + x;
	}

	private void normalCalculations(double latitude, double longitude) {
		double lat = gradeToRadian(latitude);
		double lon = gradeToRadian(longitude);
		y = (long) Math.round(earthRadius * Math.cos(lat) * Math.cos(lon));
		x = (long) Math.round(earthRadius * Math.cos(lat) * Math.sin(lon));
	}

	private double gradeToRadian(double grade) {
		return grade * Math.PI / 180;
	}

	@Override
	public long getX() {
		return x;
	}

	@Override
	public long getY() {
		return y;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public static void main(String[] args) {
		LLPosition p1 = new LLPosition(5, 5);
		System.out.println(p1.getX() + " " + p1.getY());
		LLPosition p2 = new LLPosition(5, -5);
		System.out.println(p2.getX() + " " + p2.getY());
		LLPosition p3 = new LLPosition(-5, 5);
		System.out.println(p3.getX() + " " + p3.getY());
		LLPosition p4 = new LLPosition(-5, -5);
		System.out.println(p4.getX() + " " + p4.getY());

		LLPosition p = new LLPosition(53, 2);
		System.out.println(p.getX() + " " + p.getY());
	}

}
