package framework.utils;

public class Utils {

	public static double fromKmHToMmMicroSec(int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}
		return speed / 3600D;
	}

	public static int fromMmMicroSecToKmH(double speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}
		return (int) Math.round(speed * 3600);
	}

	public static long secondsToMicroSeconds(int seconds) {
		return seconds * 1000L * 1000L;
	}

	public static long minutesToMicroSeconds(int minutes) {
		return secondsToMicroSeconds(minutes * 60);
	}

	public static long metersToMillimeter(int meters) {
		return meters * 1000L;
	}

	public static double millimeterToMeter(long millimeter) {
		return millimeter / 1000D;
	}

	public static int microsecondsToSeconds(long microSeconds) {
		return (int) Math.round((double) microSeconds / 1000000D);
		
	}

}
