package fr.aphaistos.homes.json;

import java.util.Map.Entry;

import com.google.gson.internal.LinkedTreeMap;

public class Home {
	
	private final String name;
	private final HomePos pos;
	
	public Home(final String name, final HomePos pos) {
		super();
		this.name = name;
		this.pos = pos;
	}
	
	public String getName() {
		return name;
	}
	public HomePos getPos() {
		return pos;
	}
	
	@SuppressWarnings("unchecked")
	public static Home fromLinkedTreeMap(LinkedTreeMap<String, Object> map) {
		String name = null;
		HomePos pos = null;
		for(Entry<String, Object> entry : map.entrySet())
			if(entry.getKey().equals("name"))
				name = (String) entry.getValue();
			else if (entry.getKey().equals("pos"))
				pos = HomePos.fromLinkedTreeMap((LinkedTreeMap<String, Object>) entry.getValue());
		return new Home(name, pos);
	}
}