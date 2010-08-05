package layer.physical.commands;

import java.util.List;

import layer.devices.AStarRouter;
import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackageDTO;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

import framework.core.VirtualClock;
import framework.layer.agent.Agent;
import framework.layer.physical.command.Command;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;
import framework.layer.physical.command.move.EnterConnectorCommand;

public class PickAndDeliverPackage extends Command<Truck> {

	private PDPPackageDTO p;
	
	//TODO: ROUTERING MOET DMV VAN ANTS EN NIET DOOR EXTERNE ROUTER
	private AStarRouter router;
	private Agent commandingAgent;

	public PickAndDeliverPackage(Truck dv, PDPPackageDTO p, AStarRouter router, Agent commandingAgent) {
		super(dv);
		
		this.p = p;
		this.router = router;
		this.commandingAgent = commandingAgent;
	}
	
	@Override
	public void execute() throws IllegalCommandException,
			CommandUncompletedException {

		Crossroads destinationToPick = p.getOrigin();
		Crossroads destinationToDeliver = p.getDestination();
				
		List<Crossroads> pathToPick = router.calculateTrajectory(VirtualClock.currentTime(), getEntity().getConnectorPosition().getConnector(), destinationToPick).getTrajectory();
		List<Crossroads> pathToDeliver = router.calculateTrajectory(VirtualClock.currentTime(), destinationToPick, destinationToDeliver).getTrajectory();
		
		Crossroads previous = getEntity().getConnectorPosition().getConnector();
		for(int i=0; i<pathToPick.size(); i++) {
			getEntity().addCommand(new LeaveCrossroadCommand(getEntity(), previous.getConnectionTo(pathToPick.get(0))), commandingAgent);
			getEntity().addCommand(new MoveForwardCommand(getEntity()), commandingAgent);
			getEntity().addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(getEntity(), true), commandingAgent);
			
			previous = pathToPick.remove(0); 
		}
		
		previous = destinationToPick;
		for(int i=0; i<pathToDeliver.size(); i++) {
			getEntity().addCommand(new LeaveCrossroadCommand(getEntity(), previous.getConnectionTo(pathToDeliver.get(0))), commandingAgent);
			getEntity().addCommand(new MoveForwardCommand(getEntity()), commandingAgent);
			getEntity().addCommand(new EnterConnectorCommand<Truck, Crossroads, Road>(getEntity(), true), commandingAgent);
			
			previous = pathToDeliver.remove(0);
		}
		
	}

}
