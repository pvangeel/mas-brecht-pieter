package framework.layer.physical.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import framework.events.EventBroker;
import framework.events.physical.CommandCompletedEvent;
import framework.events.physical.CommandIssuedEvent;
import framework.events.physical.CommandsClearedEvent;
import framework.events.physical.ExecutingFailSafeEvent;
import framework.events.physical.IllegalCommandEvent;
import framework.layer.TickListener;
import framework.layer.agent.Agent;
import framework.layer.deployment.devices.Device;
import framework.layer.deployment.devices.InactiveCapabilityException;
import framework.layer.physical.PhysicalStructure;
import framework.layer.physical.command.Command;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;
import framework.layer.physical.position.Position;
import framework.utils.IdGenerator;

/**
 * Any entity that can be found in the physical world, usually buildings or
 * machines. Devices are not physical entities since they are not capable of
 * independent existence. They are always attached to a physical entity.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class PhysicalEntity<E extends PhysicalEntity<E>> implements TickListener {
	private static Logger logger = Logger.getLogger(PhysicalEntity.class);
	
	private final int id = IdGenerator.getIdGenerator().getNextId(PhysicalEntity.class);

	private PhysicalStructure<?> structure;

	private final LinkedList<Command<? extends E>> commands = new LinkedList<Command<? extends E>>();

	public abstract Position getPosition();

	private long availableTime;
	private final Set<Device> attachedDevices = new HashSet<Device>();
	private Command<? extends E> failSafeCommand;;

	public Command<? extends E> getFailSafeCommand() {
		if (failSafeCommand == null) {
			failSafeCommand = loadFailSafeCommand();
		}
		return failSafeCommand;
	}

	protected abstract Command<? extends E> loadFailSafeCommand();

	public int getId() {
		return id;
	}

	public void setStructure(PhysicalStructure<?> structure) {
		this.structure = structure;
	}

	public PhysicalStructure<?> getStructure() {
		if (structure == null) {
			throw new IllegalStateException();
		}
		return structure;
	}

	/**
	 * The given agent issues the given command to this vehicle
	 * 
	 * @param command
	 *            the command to be executed
	 * @param commandingAgent
	 *            the agent calling the addCommand method
	 */
	public void addCommand(Command<E> command, Agent commandingAgent) {
		if (command == null || commandingAgent == null) {
			throw new IllegalArgumentException();
		}
		if (!canCommandPhysicalEntity(commandingAgent)) {
			throw new IllegalStateException();
		}
		commands.add(command);
		EventBroker.getEventBroker().notifyAll(new CommandIssuedEvent(commandingAgent, this, command));
	}

	/**
	 * return true if one the the devices attached to this agent has the given
	 * agent running on it
	 */
	private boolean canCommandPhysicalEntity(Agent commandingAgent) {
		for (Device d : getAttachedDevices()) {
			if (d.hasAgentRunningOnIt(commandingAgent)) {
				return true;
			}
		}
		return false;
	}

	public void removeAllCommands(Agent commandingAgent) {
		if (commandingAgent == null) {
			throw new IllegalArgumentException();
		}
		if (!canCommandPhysicalEntity(commandingAgent)) {
			throw new IllegalStateException();
		}
		commands.clear();
		EventBroker.getEventBroker().notifyAll(new CommandsClearedEvent(commandingAgent, this));

	}
	
//	public int commandsToProcess(Agent commandingAgent) {
//		if (commandingAgent == null) {
//			throw new IllegalArgumentException();
//		}
//		if (!canCommandPhysicalEntity(commandingAgent)) {
//			throw new IllegalStateException();
//		}
//
//		return commands.size();
//	}

	public boolean allCommandsProcessed() {
		if(commands.isEmpty()){
			return true;
		}
		
		return false;
	}

	public void processTick(long timePassed) {
		availableTime = timePassed;
		while (!commands.isEmpty()) {
			try {
				Command<? extends E> command = commands.get(0);
				command.execute();
//				commands.remove(0);
				EventBroker.getEventBroker().notifyAll(new CommandCompletedEvent(this, command));
			} catch (CommandUncompletedException e) { //FIXME MARIO changed the default behavior of 
				//problematic commands. If there is a commanduncompletedException, then the command 
				//is dropped, and the agent need to issue it again.
				// command remains in queue
//				return;
				logger.error("Could not execute command", e);
			} catch (IllegalCommandException e) {
				notifyDevicesOfIllegalCommand(e.getCommand());
				EventBroker.getEventBroker().notifyAll(new IllegalCommandEvent(this, e.getCommand()));
			}
			
			//always excludes a command from the list of commands, even if it was not
			//possible to execute the command
			commands.remove(0);
		}
		// only getting here when all command were proccessed succesfully and no
		// new instructions are available
		// System.err.println("WARNING: " + this + " executing failsafe");
		EventBroker.getEventBroker().notifyAll(new ExecutingFailSafeEvent(this));
		try {
			if (getFailSafeCommand() != null) {
				getFailSafeCommand().execute();
			}
		} catch (IllegalCommandException e) {
			// ignore because failSafe is such a general command it's bound to
			// make some wrong decisions
		} catch (CommandUncompletedException e) {
			// ignore
		}
	}

	private void notifyDevicesOfIllegalCommand(Command<?> command) {
		for (Device device : attachedDevices) {
			try {
				device.getCommunicationCapability().addInboxCommunicationPackage(new IllegalCommandMessage(command));
			} catch (InactiveCapabilityException e) {
				// if one of the device has no communication capability or it
				// has been deactivated, there's nothing we can do to notify the
				// agents on it
			}
		}
	}

	protected void consumeTime(long time) {
		if (availableTime - time < 0) {
			throw new IllegalArgumentException();
		}
		availableTime -= time;
	}

	public boolean hasAvailableTimeLeft() {
		return availableTime > 0;
	}

	public long getAvailableTime() {
		return availableTime;
	}

	/**
	 * may only be called by DeploymentStructure.attachDevice()
	 */
	public void attachDevice(Device device) {
		if (device == null) {
			throw new IllegalArgumentException();
		}
		attachedDevices.add(device);
	}

	/**
	 * may only be called by DeploymentStructure.detachDevice()
	 */
	public void detachDevice(Device device) {
		attachedDevices.remove(device);
	}

	public Set<Device> getAttachedDevices() {
		return attachedDevices;
	}
	
	public Set<Agent> getAgentsOnAttachDevices() {
		HashSet<Agent> agents = new HashSet<Agent>();
		for(Device d : attachedDevices){
			agents.addAll(d.getAgents());
		}
		return agents;
	}

	public boolean hasAttachedDevices() {
		return !attachedDevices.isEmpty();
	}

	public Collection<PhysicalEntity<?>> getEntitiesInRange(long range) {
		Collection<PhysicalEntity<?>> entitiesInRange = new HashSet<PhysicalEntity<?>>();
		for (PhysicalEntity<?> entity : getStructure().getAllPhysicalEntities()) {
			if (!entity.equals(this)) {
				if (getPosition().getDistanceTo(entity.getPosition()) <= range) {
					entitiesInRange.add(entity);
				}
			}
		}
		return entitiesInRange;
	}
}
