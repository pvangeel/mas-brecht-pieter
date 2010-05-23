package pclabs4.environment;

import java.util.List;

import pclabs.physicallayer.entities.PDPPackageDTO;

public interface AgentEnvironment {
	public List<PDPPackageDTO> searchPackages();
}
