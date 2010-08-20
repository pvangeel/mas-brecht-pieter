package configuration.directors;

import configuration.intructions.CreateCrossroadsInstruction;
import configuration.intructions.CreateTwoWayRoadInstruction;
import configuration.intructions.DeployCrossroadsAndFlagsInstruction;
import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.initialization.TimePattern;
import framework.initialization.spatial.SpatialPattern;
import framework.initialization.startup.StartupTimePattern;
import framework.instructions.deployment.DeployConnectionInstruction;
import framework.instructions.deployment.DeployConnectorInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.utils.IdGenerator;

public class RegularGridDirector
		extends
		InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	private int width;
	private int height;
	
	private long startX = 6694935603L;
	private long startY = 4002925795L;
	private long interval = 300000L;
	

	public RegularGridDirector(
			TimePattern timePattern,
			SpatialPattern<PhysicalConnectionStructure<Truck, Crossroads, Road>, ?> spatialPattern) {
		super(timePattern, spatialPattern);
		// TODO Auto-generated constructor stub
	}

	public RegularGridDirector(int width, int height) {
		super(new StartupTimePattern());
		this.width = width;
		this.height = height;

	}

	@Override
	protected void createAndDeploy() {
		deployNodes();
		deployWays();
	}

	private void deployWays() {
		long currentTime = VirtualClock.currentTime();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int id = baseID + x*width+y;
				
				if(y<width-1) {
					int roadId = IdGenerator.getIdGenerator().getNextId(Road.class);
					
					getInstructionManager().addInstruction(new CreateTwoWayRoadInstruction(currentTime, roadId));
					getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, roadId, id, id+1));
					System.out.println("creating road between " + id + " and " + (id + 1) + " with id " +roadId);
					
				}
				if(x<height-1) {
					int roadId = IdGenerator.getIdGenerator().getNextId(Road.class);
					
					getInstructionManager().addInstruction(new CreateTwoWayRoadInstruction(currentTime, roadId));
					getInstructionManager().addInstruction(new DeployConnectionInstruction<Truck, Crossroads, Road>(currentTime, roadId,id,id+width));
					System.out.println("creating road between " + id + " and " + (id + width) + " with id " +roadId);
				}
				
				
				
			}
		}
	}
	int baseID=0;//IdGenerator.getIdGenerator().getNextId(Crossroads.class);

	private void deployNodes() {
		long currentTime = VirtualClock.currentTime();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				//int nextId = IdGenerator.getIdGenerator().getNextId(Crossroads.class);
				int nextId = baseID + x*width +y;
//				System.out.println("next id: " + nextId);
				getInstructionManager().addInstruction(new CreateCrossroadsInstruction(currentTime, nextId));
				getInstructionManager().addInstruction(
						new DeployConnectorInstruction<Truck, Crossroads, Road>(
								currentTime, 
								nextId, 
								startX + x * interval, 
								startY + y * interval
						)
				);

			}
		}

	}

}
