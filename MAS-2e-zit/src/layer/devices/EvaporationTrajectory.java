package layer.devices;

public class EvaporationTrajectory extends Trajectory {
	
	private static final long evaporationMAX = 2000;
	private long evaporation = evaporationMAX;
	
	public EvaporationTrajectory(Trajectory trajectory){
		super(trajectory);
	}

	public boolean evaporate() {
		evaporation--;
		if(evaporation <= 0) return true;
		return false;
	}

	public void restoreEvaporation() {
		evaporation = evaporationMAX;
	}
	
	@Override
	public String toString() {
		return "E:" + evaporation + " " + super.toString();
	}

}
