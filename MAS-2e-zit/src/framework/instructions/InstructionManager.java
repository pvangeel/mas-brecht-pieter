package framework.instructions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import framework.core.SimulationCore;
import framework.core.VirtualClock;
import framework.layer.agent.AgentStructure;
import framework.layer.deployment.DeploymentStructure;
import framework.layer.physical.PhysicalStructure;
import framework.utils.ReflectionUtils;

/**
 * 
 * A manager that holds and executes instructions at the appropriate time
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class InstructionManager<P extends PhysicalStructure<?>> {

	private final Collection<InstructionListener> instructionListeners = new HashSet<InstructionListener>();
	private final HashMap<Integer, Collection<Object>> createdObjects = new HashMap<Integer, Collection<Object>>();

	private final LinkedList<Instruction<? super P>> instructions = new LinkedList<Instruction<? super P>>();

	private final SimulationCore<P> simulationCore;

	public InstructionManager(SimulationCore<P> simulationCore) {
		if (simulationCore == null) {
			throw new IllegalArgumentException();
		}
		this.simulationCore = simulationCore;
		instructionManager = this;
	}

	private static InstructionManager<?> instructionManager;

	public static InstructionManager<?> getInstructionManager() {
		if (instructionManager == null) {
			throw new IllegalStateException();
		}
		return instructionManager;
	}

	public AgentStructure getAgentStructure() {
		return simulationCore.getAgentLayer().getAgentStructure();
	}

	public DeploymentStructure getDeploymentStructure() {
		return simulationCore.getDeploymentLayer().getDeploymentStructure();
	}

	public P getPhysicalStructure() {
		return simulationCore.getPhysicalLayer().getPhysicalStructure();
	}

	public void addInstruction(Instruction<? super P> instruction) {
		if (instruction == null) {
			throw new IllegalArgumentException();
		}
		instructions.add(instruction);
		instruction.setInstructionManager(this);
	}

	public void register(InstructionListener instructionListener) {
		if (instructionListener == null) {
			throw new IllegalArgumentException();
		}
		instructionListeners.add(instructionListener);
	}

	public void unregister(InstructionListener instructionListener) {
		if (instructionListener == null) {
			throw new IllegalArgumentException();
		}
		instructionListeners.remove(instructionListener);
	}

	public Collection<InstructionListener> getInstructionListeners() {
		return instructionListeners;
	}

	public <T> T findSpecificObject(Class<T> c, int id) {
		T o = findObject(c, id);
		if (o == null) {
			throw new IllegalArgumentException("could not find " + c.getSimpleName() + " with id " + id);
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	private <T> T findObject(Class<T> c, int id) {
		if (createdObjects.containsKey(id)) {
			for (Object obj : createdObjects.get(id)) {
				if (c.isInstance(obj)) {
					return (T) obj;
				}
			}
		}
		return null;
	}

	public void addCreatedObject(int id, Object object) {
		// System.out.println("InstructionManager.addCreatedObject() "+object.getClass().getSimpleName()+" "+id);
		if (object == null) {
			throw new IllegalArgumentException();
		}
		Object o = findObject(object.getClass(), id);
		if (o != null) {
			// object already exists
			throw new IllegalArgumentException("object already exists " + object.getClass().getSimpleName() + " id " + id);
		}
		Collection<Object> objects;
		if (createdObjects.containsKey(id)) {
			objects = createdObjects.get(id);
		} else {
			objects = new HashSet<Object>();
		}
		objects.add(object);
		createdObjects.put(id, objects);
	}

	public void executePendingInstructions() {
		long lowestExecutionTime = findLowestExecutionTime();
		while (lowestExecutionTime <= VirtualClock.getClock().getCurrentTime()) {
			executeInstructionsBefore(lowestExecutionTime);
			lowestExecutionTime = findLowestExecutionTime();
		}
	}

	private long findLowestExecutionTime() {
		long lowestExecutionTime = Long.MAX_VALUE;
		for (Instruction<? super P> instruction : instructions) {
			if (instruction.getExecutionTime() < lowestExecutionTime) {
				lowestExecutionTime = instruction.getExecutionTime();
			}
		}
		return lowestExecutionTime;
	}

	private void executeInstructionsBefore(long executionTime) {
		for (Instruction<? super P> instruction : new ArrayList<Instruction<? super P>>(instructions)) {
			if (instruction.getExecutionTime() <= executionTime) {
				instruction.execute();
				instructions.remove(instruction);
				// notify instructionListeners (bvb een logger)
				for (InstructionListener l : instructionListeners) {
					l.handleInstruction(instruction);
				}
			}
		}
	}

	public void terminate() {
		for (InstructionListener l : instructionListeners) {
			l.terminate();
		}
		instructionListeners.clear();
		instructionManager = null;
	}

	public int getMappedId(Object object) {
		for (Integer id : createdObjects.keySet()) {
			if (createdObjects.get(id).contains(object)) {
				return id;
			}
		}
		throw new IllegalArgumentException();
	}

	public <T> int getMappedId(Class<T> c, int id) {
		try {
			for (int mappedId : createdObjects.keySet()) {
				for (Object object : createdObjects.get(mappedId)) {
					if (c.isInstance(object)) {
						int objectId;
						Field field = ReflectionUtils.getField(c, "id");
						objectId = (Integer) field.get(object);
						if (objectId == id) {
							return mappedId;
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		} catch (SecurityException e) {
			throw new IllegalArgumentException();
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException();
		}
		throw new IllegalArgumentException();
	}

}
