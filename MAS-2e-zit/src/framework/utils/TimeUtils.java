package framework.utils;

public class TimeUtils {

	public static TimeObject getTimeObject(long time) {
		return new TimeObject(time);
	}

	public static class TimeObject {

		public static final long millisecmicrosecs = 1000;
		public static final long secmicrosecs = 1000 * millisecmicrosecs;
		public static final long minutemicrosecs = 60 * secmicrosecs;
		public static final long hourmicrosecs = 60 * minutemicrosecs;
		public static final long daymicrosecs = 24 * hourmicrosecs;

		private final int days;
		private final int hours;
		private final int minutes;
		private final int seconds;
		private final int milliseconds;
		private final int microseconds;
		
		private final long initialValue;

		public TimeObject(long microsec) {
			this.initialValue = microsec;
			days = (int) (microsec / daymicrosecs);
			hours = (int) ((microsec % daymicrosecs) / hourmicrosecs);
			minutes = (int) ((microsec % hourmicrosecs) / minutemicrosecs);
			seconds = (int) ((microsec % minutemicrosecs) / secmicrosecs);
			milliseconds = (int) ((microsec % secmicrosecs) / millisecmicrosecs);
			microseconds = (int) (microsec % millisecmicrosecs);
		}

		public int getDays() {
			return days;
		}

		public int getHours() {
			return hours;
		}

		public int getMinutes() {
			return minutes;
		}

		public int getSeconds() {
			return seconds;
		}

		public int getMilliseconds() {
			return milliseconds;
		}

		public int getMicroseconds() {
			return microseconds;
		}

		@Override
		public String toString() {
			return getDays() + " days, " + getHours() + " hours, " + getMinutes() + " minutes, " + getSeconds()
					+ " seconds, " + getMilliseconds() + " milliseconds, " + getMicroseconds() + " microseconds";
		}
		
		public long getInitialValue(){
			return initialValue;
		}
		
		public int hoursDifference(TimeObject other){
			if(initialValue > other.getInitialValue()){
				return new TimeObject(initialValue - other.getInitialValue()).getHours();
			}else{
				return -1 * new TimeObject(other.getInitialValue() - initialValue).getHours();
			}
		}

	}
}
