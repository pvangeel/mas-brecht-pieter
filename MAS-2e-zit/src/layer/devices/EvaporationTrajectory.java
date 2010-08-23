package layer.devices;

import framework.core.VirtualClock;
import framework.utils.Utils;

public class EvaporationTrajectory extends Trajectory {
	
	private static final long evaporationDelta = Utils.minutesToMicroSeconds(20);
	private long evaporationStart = VirtualClock.currentTime();
	
	public EvaporationTrajectory(Trajectory trajectory){
		super(trajectory);
	}

	public boolean evaporate() {
		return VirtualClock.currentTime() - evaporationStart > evaporationDelta;
	}

	public void restoreEvaporation() {
		evaporationStart = VirtualClock.currentTime();
	}
	
	@Override
	public String toString() {
		return "E:" + (evaporationDelta - (VirtualClock.currentTime() - evaporationStart)) + " " + super.toString();
	}

}
