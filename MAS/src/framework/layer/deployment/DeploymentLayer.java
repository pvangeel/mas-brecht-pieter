package framework.layer.deployment;

import framework.layer.Layer;

public class DeploymentLayer extends Layer {

	private final DeploymentStructure deploymentStructure = new DeploymentStructure(this);
	
	public DeploymentLayer() {
		super(200);
	}
	
	public DeploymentStructure getDeploymentStructure() {
		return deploymentStructure;
	}

}
