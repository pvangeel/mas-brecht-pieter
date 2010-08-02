package framework.layer.physical.connections;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import framework.events.EventBroker;
import framework.events.physical.EntityEntersConnectorOffRoadEvent;
import framework.events.physical.EntityEntersConnectorOnRoadEvent;
import framework.events.physical.EntityLeavesConnectorOffRoadEvent;
import framework.events.physical.EntityLeavesConnectorOnRoadEvent;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.entities.StaticResource;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectorPosition;
import framework.layer.physical.position.ContinuousPosition;
import framework.utils.IdGenerator;

/**
 * A connector serves as a node in a traffic network. It has one or more
 * incoming and outgoing roads. Connectors have an onroad part and an offroad
 * part. Both can be accessed from the incoming roads and after leaving, the
 * ConnectionEntity ends up on one of the outgoing roads. The offroad and onroad
 * parts can be blocked. Connectors can contain resources. accessed from the
 * onroad or offroad part of the connector. the onroad and offroad parts of a
 * connector have a capacity, which allows programmers to constructs parking
 * lots etc. Connectors can be blocking, which means that vehicles need to slow
 * down when they approach and enter them (e.g. crossroads)
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Connector<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionElement<E, Ctr, Cnn, PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private final int id = IdGenerator.getIdGenerator().getNextId(Connector.class);

	private ContinuousPosition position;
	private final Set<Cnn> incomingConnections = new HashSet<Cnn>();
	private final Set<Cnn> outgoingConnections = new HashSet<Cnn>();

	// Maximum number of vehicles in the connector on the road
	private final int onroadCapacity;

	// Maximum number of vehicles in the connector next to the road
	private final int offroadCapacity;

	private final LinkedList<E> onroadEntities = new LinkedList<E>();
	private final Set<E> offroadEntities = new HashSet<E>();
	private final Set<StaticResource<Ctr>> resources = new HashSet<StaticResource<Ctr>>();

	private double slowdownSpeed = -1;

	public Connector(int onroadCapacity, int offroadCapacity) {
		if (onroadCapacity < 0 || offroadCapacity < 0) {
			throw new IllegalArgumentException();
		}
		this.onroadCapacity = onroadCapacity;
		this.offroadCapacity = offroadCapacity;
	}

	public int getId() {
		return id;
	}

	public ContinuousPosition getPosition()
	{
		if (position == null) {
			throw new IllegalStateException();
		}
		return position;
	}

	public void setPosition(ContinuousPosition position) {
		if (position == null) {
			throw new IllegalArgumentException();
		}
//		if (this.position != null) {
//			throw new IllegalStateException("field can only be set once");
//		}
		this.position = position;
	}

	public int getOnroadCapacity() {
		return onroadCapacity;
	}

	public int getOffroadCapacity() {
		return offroadCapacity;
	}

	public LinkedList<E> getOnroadEntities() {
		return onroadEntities;
	}

	public Set<E> getOffroadEntities() {
		return offroadEntities;
	}

	public Set<StaticResource<Ctr>> getResources() {
		return resources;
	}

	public double getSlowdownSpeed() {
		if (!mustSlowDown()) {
			throw new IllegalStateException();
		}
		return slowdownSpeed;
	}

	public void mustSlowDownTo(double speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}
		slowdownSpeed = speed;
	}

	public void mustNotSlowDown() {
		slowdownSpeed = -1;
	}

	public boolean mustSlowDown() {
		return slowdownSpeed != -1;
	}

	public void addConnection(Cnn connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		if (!connection.hasConnector(this)) {
			throw new IllegalStateException();
		}

		if (connection.canTravelTo((Ctr)this)) {
			incomingConnections.add(connection);
		} // geen else if
		if (connection.canTravelFrom((Ctr)this)) {
			outgoingConnections.add(connection);
		}
	}

	/**
	 * Method can only be used by Connection.destroy()
	 */
	public void removeConnection(Connection<E, Ctr, Cnn> connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		if (!connection.hasConnector(this)) {
			throw new IllegalStateException();
		}
		incomingConnections.remove(connection);
		outgoingConnections.remove(connection);
	}

	@SuppressWarnings("unchecked")
	public void enterOnroad(E connectionEntity) throws CannotEnterOnroadException,
			IllegalEnterException {
		if (connectionEntity == null || onroadEntities.contains(connectionEntity)) {
			throw new IllegalArgumentException();
		}
		if (isIllegalEnter(connectionEntity)) {
			throw new IllegalEnterException();
		}
		if (getOnroadCapacity() == 0) {
			throw new IllegalEnterException();
		}
		if (isBlocked()) {
			throw new IllegalEnterException();
		}
		if (!canEnterOnroad() || isOnroadFull()) {
			throw new CannotEnterOnroadException();
		}
		Cnn fromConnection = connectionEntity.getConnectionPosition().getConnection();
		fromConnection.leaveConnection(connectionEntity, (Ctr) this, true);
		onroadEntities.add(connectionEntity);
		onEnterOnroad(connectionEntity, fromConnection);
		EventBroker.getEventBroker().notifyAll(
				new EntityEntersConnectorOnRoadEvent(connectionEntity, this));
	}

	protected abstract void onEnterOnroad(E connectionEntity, Cnn fromConnection);

	private boolean isIllegalEnter(E connectionEntity) {
		return !connectionEntity.isOnConnection()
				|| !connectionEntity.getConnectionPosition().getConnection().canTravelTo((Ctr)this)
				|| !connectionEntity.getConnectionPosition().getConnection().isAtEnd(
						connectionEntity);
	}

	private boolean isOnroadFull() {
		return onroadEntities.size() >= onroadCapacity;
	}

	protected abstract boolean canEnterOnroad();

	@SuppressWarnings("unchecked")
	public void enterOffroad(E connectionEntity) throws CannotEnterOffroadException,
			IllegalEnterException {
		// System.out.println("Connector.enterOffroad() "+connectionEntity);
		if (connectionEntity == null || offroadEntities.contains(connectionEntity)) {
			throw new IllegalArgumentException();
		}
		if (isIllegalEnter(connectionEntity)) {
			throw new IllegalEnterException();
		}
		if (getOffroadCapacity() == 0) {
			throw new IllegalEnterException();
		}
		if (isBlocked()) {
			throw new IllegalEnterException();
		}
		if (!canEnterOffroad() || isOffroadFull()) {
			throw new CannotEnterOffroadException();
		}
		Cnn fromConnection = connectionEntity.getConnectionPosition().getConnection();
		fromConnection.leaveConnection(connectionEntity, (Ctr) this, false);
		offroadEntities.add(connectionEntity);
		onEnterOffroad(connectionEntity, fromConnection);
		EventBroker.getEventBroker().notifyAll(
				new EntityEntersConnectorOffRoadEvent(connectionEntity, this));
	}

	protected abstract void onEnterOffroad(E connectionEntity, Cnn fromConnection);

	protected abstract boolean canEnterOffroad();

	private boolean isOffroadFull() {
		return offroadEntities.size() >= offroadCapacity;
	}

	@SuppressWarnings("unchecked")
	public void addResource(StaticResource<Ctr> resource) {
		if (resource == null || resources.contains(resource)) {
			throw new IllegalArgumentException();
		}
		resources.add(resource);
		resource.setConnector((Ctr) this);
	}

	public void leaveOnroad(E connectionEntity, Cnn toConnection)
			throws CannotLeaveOnroadException, IllegalLeaveException {
		// System.out.println("Connector.leaveOnroad() "+connectionEntity);
		if (connectionEntity == null || toConnection == null
				|| !onroadEntities.contains(connectionEntity)) {
			throw new IllegalArgumentException();
		}
		if (isIllegalLeave(connectionEntity, toConnection)) {
			throw new IllegalArgumentException();
		}
		if (toConnection.isBlocked()) {
			throw new IllegalLeaveException();
		}
		if (!canLeaveOnroad()) {
			throw new CannotLeaveOnroadException();
		}
		onroadEntities.remove(connectionEntity);
		toConnection.enterConnection(connectionEntity, (Ctr)this);
		onLeaveOnroad(connectionEntity, toConnection);
		EventBroker.getEventBroker().notifyAll(
				new EntityLeavesConnectorOnRoadEvent(connectionEntity, this, toConnection));
	}

	protected abstract void onLeaveOnroad(E connectionEntity, Cnn toConnection);

	private boolean isIllegalLeave(E connectionEntity, Connection<E, Ctr, Cnn> toConnection) {
		return !connectionEntity.isOnConnector() || !toConnection.canTravelFrom((Ctr)this);
	}

	protected abstract boolean canLeaveOnroad();

	public void leaveOffroad(E connectionEntity, Cnn toConnection)
			throws CannotLeaveOffroadException, IllegalLeaveException {
		// System.out.println("Connector.leaveOffroad() "+connectionEntity);
		if (connectionEntity == null || toConnection == null
				|| !offroadEntities.contains(connectionEntity)) {
			throw new IllegalArgumentException();
		}
		if (isIllegalLeave(connectionEntity, toConnection)) {
			throw new IllegalArgumentException();
		}
		if (toConnection.isBlocked()) {
			throw new IllegalLeaveException();
		}
		if (!canLeaveOffroad()) {
			throw new CannotLeaveOffroadException();
		}
		offroadEntities.remove(connectionEntity);
		toConnection.enterConnection(connectionEntity,(Ctr) this);
		onLeaveOffroad(connectionEntity, toConnection);
		EventBroker.getEventBroker().notifyAll(
				new EntityLeavesConnectorOffRoadEvent(connectionEntity, this, toConnection));
	}

	protected abstract void onLeaveOffroad(E connectionEntity, Cnn toConnection);

	protected abstract boolean canLeaveOffroad();

	public boolean hasIncomingConnections() {
		return !incomingConnections.isEmpty();
	}

	public boolean hasOutgoingConnections() {
		return !outgoingConnections.isEmpty();
	}

	public Set<Connection<E, Ctr, Cnn>> getConnections() {
		Set<Connection<E, Ctr, Cnn>> set = new HashSet<Connection<E, Ctr, Cnn>>();
		set.addAll(incomingConnections);
		set.addAll(outgoingConnections);
		return set;
	}

	public Set<Cnn> getOutgoingConnections() {
		return outgoingConnections;
	}

	public Set<Cnn> getIncomingConnections() {
		return incomingConnections;
	}

	public Cnn getOutgoingConnectionTo(Ctr connector) {
		for (Cnn connection : outgoingConnections) {
			if (connection.canTravelTo(connector)) {
				return connection;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public void deploy(E connectionEntity, ConnectionElementPosition<E, Ctr, Cnn> position)
			throws IllegalDeploymentException {
		if (position == null || !position.isOnConnector()) {
			throw new IllegalArgumentException();
		}
		ConnectorPosition<E, Ctr, Cnn> pos = (ConnectorPosition<E, Ctr, Cnn>) position;
		if (!pos.getConnector().equals(this)) {
			throw new IllegalArgumentException();
		}
		if (pos.isOnroad()) {
			if (!canEnterOnroad() || isOnroadFull()) {
				throw new IllegalDeploymentException();
			}
			onroadEntities.add(connectionEntity);
		} else {
			if (!canEnterOffroad() || isOffroadFull()) {
				throw new IllegalDeploymentException();
			}
			offroadEntities.add(connectionEntity);
		}
		executeSpecificDeploymentOptions(connectionEntity, pos);
		connectionEntity.setConnectionElementPosition(pos);
	}

	/**
	 * True if a connection exists between this connector and the given other
	 * connector
	 * 
	 * True if this connector = otherConnector
	 */
	public boolean isConnected(Connector<E, Ctr, Cnn> otherConnector) {
		for (Connection<E, Ctr, Cnn> cnn : getConnections()) {
			if (cnn.getConnector1().equals(otherConnector)
					|| cnn.getConnector2().equals(otherConnector)) {
				return true;
			}
		}
		return false;
	}

	public Cnn getConnectionTo(Ctr otherConnector) {
		for (Cnn cnn : getOutgoingConnections()) {
			if(cnn.getOtherConnector((Ctr)this).equals(otherConnector)){
				return cnn;
			}
		}
		return null;
	}
	
	public Cnn getConnectionFrom(Ctr otherConnector) {
		for (Cnn cnn : getIncomingConnections()) {
			if(cnn.getOtherConnector((Ctr)this).equals(otherConnector)){
				return cnn;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Connector) {
			Connector c = (Connector) obj;
			return c.getId() == getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ("Connector" + getId()).hashCode();
	}

	protected abstract void executeSpecificDeploymentOptions(E connectionEntity,
			ConnectorPosition<E, Ctr, Cnn> pos);

	public static class CannotEnterOnroadException extends Exception {
	}

	public static class CannotEnterOffroadException extends Exception {
	}

	public static class CannotLeaveOnroadException extends Exception {
	}

	public static class CannotLeaveOffroadException extends Exception {
	}

	public static class IllegalEnterException extends Exception {
	}

	public static class IllegalLeaveException extends Exception {
	}
}
