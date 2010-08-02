package framework.utils;

public class UnitConvertor {

	public static int getSpeedKmPerHour(double speedMillimeterPerMicrosecond) {
		return (int) Math.round(speedMillimeterPerMicrosecond * 3600D);
	}

	public static double getSpeedMillimeterPerMicrosecond1(int speedKmPerHour) {
		return ((double) speedKmPerHour) / 3600D;
	}

	public static int getSpeedMetersPerSecond(double speedMillimeterPerMicrosecond) {
		return (int) Math.round(speedMillimeterPerMicrosecond * 1000D);
	}

	public static double getSpeedMillimeterPerMicrosecond2(int speedMetersPerSecond) {
		return ((double) speedMetersPerSecond) / 1000;
	}

}
