package framework.osm;

import java.util.HashMap;

public class OSMNodeBuilder extends OSMObjectBuilder<OSMNode> {

	public OSMNodeBuilder(OSMObjectPool objectPool) {
		super(objectPool);
	}

	@Override
	public void handle(String name, HashMap<String, String> arguments) {
		if (("tag").equals(name)) {
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void initiate(HashMap<String, String> arguments) {
		int id = extractInt("id", arguments);
		double latitude = extractDouble("lat", arguments);
		double longitude = extractDouble("lon", arguments);
		setOsmObject(new OSMNode(id, latitude, longitude));
	}

}
