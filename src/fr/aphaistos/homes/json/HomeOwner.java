package fr.aphaistos.homes.json;

import java.util.ArrayList;
import java.util.Map.Entry;

import com.google.gson.internal.LinkedTreeMap;

public class HomeOwner {
	private final String uuid;
	private final ArrayList<Home> homes;
	
	public HomeOwner(final String uuid, final ArrayList<Home> homes) {
		super();
		this.uuid = uuid;
		this.homes = homes;
	}
	
	public String getUuid() {
		return uuid;
	}
	public ArrayList<Home> getHomes() {
		return homes;
	}
	
	@SuppressWarnings("unchecked")
	public static HomeOwner fromLinkedTreeMap(LinkedTreeMap<String, Object> map) {
		String uuid = null;
		ArrayList<Home> homes = null;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equals("uuid")) {
				uuid = (String) entry.getValue();
			}
			if (entry.getKey().equals("homes")) {
				homes = new ArrayList<Home>();
				ArrayList<LinkedTreeMap<String, Object>> homes_data = (ArrayList<LinkedTreeMap<String, Object>>) entry.getValue();
				for (LinkedTreeMap<String, Object> home_data : homes_data)
					homes.add(Home.fromLinkedTreeMap(home_data));
			}
		}
		
		return new HomeOwner(uuid, homes);
	}
}