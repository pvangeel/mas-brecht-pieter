package pclabs.gui.objects;

import java.util.List;

public class GUICommunicationCapability extends GUICapability {

	private final List<GUIRouter> routers;

	public GUICommunicationCapability(int id, List<GUIRouter> routers) {
		super(id);
		if (routers == null) {
			throw new IllegalArgumentException();
		}
		this.routers = routers;
		for (GUIRouter router : routers) {
			router.setCommunicationCapability(this);
		}
	}

	public List<GUIRouter> getRouters() {
		return routers;
	}

}
