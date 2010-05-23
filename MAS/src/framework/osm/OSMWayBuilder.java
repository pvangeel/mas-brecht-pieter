package framework.osm;

import java.util.ArrayList;
import java.util.HashMap;

public class OSMWayBuilder extends OSMObjectBuilder<OSMWay> {

	private static ArrayList<StringDuo> usedTags;

	public static ArrayList<StringDuo> getUsedTags() {
		if (usedTags == null) {
			usedTags = new ArrayList<StringDuo>();
			usedTags.add(new StringDuo("highway", "motorway"));
			usedTags.add(new StringDuo("highway", "motorway_link"));
			usedTags.add(new StringDuo("highway", "trunk"));
			usedTags.add(new StringDuo("highway", "trunk_link"));
			usedTags.add(new StringDuo("highway", "primary"));
			usedTags.add(new StringDuo("highway", "primary_link"));
			usedTags.add(new StringDuo("highway", "motorway_junction"));
			usedTags.add(new StringDuo("highway", "secondary"));
			usedTags.add(new StringDuo("highway", "secondary_link"));
			usedTags.add(new StringDuo("highway", "tertiary"));
//			usedTags.add(new StringDuo("highway", "unclassified"));
			usedTags.add(new StringDuo("highway", "road"));
			usedTags.add(new StringDuo("highway", "living_street"));
			usedTags.add(new StringDuo("highway", "residential"));
			usedTags.add(new StringDuo("junction", "roundabout"));
//			usedTags.add(new StringDuo("bridge", "yes"));
//			usedTags.add(new StringDuo("tunnel", "yes"));
		}
		return usedTags;
	}

	public OSMWayBuilder(OSMObjectPool objectPool) {
		super(objectPool);
	}

	@Override
	public void handle(String name, HashMap<String, String> arguments) {
		if (("tag").equals(name)) {
//			String key = extractString("k", arguments);
//			if (key.equals("oneway")) {
//				boolean oneWay = extractBoolean("v", arguments);
//				getOsmObject().setOneWay(oneWay);
//			}
			checkForUsedTags(arguments);
		} else if (("nd").equals(name)) {
			int nodeId = extractInt("ref", arguments);
			if (getObjectPool().hasOSMNode(nodeId)) {
				getOsmObject().addNode(getObjectPool().getOSMNode(nodeId));
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	private void checkForUsedTags(HashMap<String, String> arguments) {
		String key = extractString("k", arguments);
		String value = extractString("v", arguments);
//		boolean notFiltered = false;
		for (StringDuo stringDuo : getUsedTags()) {
			if (stringDuo.key.equals(key) && stringDuo.value.equals(value)) {
				getOsmObject().setUsed(true);
//				notFiltered = true;
			}
		}
//		if (!notFiltered) {
//			System.out.println("OSMWayBuilder.checkForUsedTags() IGNORED " + key + "." + value);
//		}
	}

	@Override
	public void initiate(HashMap<String, String> arguments) {
		int id = extractInt("id", arguments);
		setOsmObject(new OSMWay(id));
	}

	@Override
	public void terminate() {
		if (getOsmObject().isUsed()) {
			super.terminate();
		}
	}

	private static class StringDuo {
		private final String key;
		private final String value;

		public StringDuo(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

}
