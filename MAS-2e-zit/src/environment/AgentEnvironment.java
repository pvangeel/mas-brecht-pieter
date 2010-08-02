package environment;

import java.util.List;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackageDTO;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;

import framework.layer.physical.position.ConnectionElementPosition;

public interface AgentEnvironment {
	public List<PDPPackageDTO> searchPackages();

	public Crossroads getStrongestFieldOrigin(
			ConnectionElementPosition<Truck, Crossroads, Road> position);
}
