package fr.aphaistos.homes.json;

import java.util.Map.Entry;

import com.google.gson.internal.LinkedTreeMap;

public class HomePos {
	private final int x;
	private final int y;
	private final int z;
	
	public HomePos(final int x, final int y, final int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}

	public static HomePos fromLinkedTreeMap(LinkedTreeMap<String, Object> map) {
		int x = 0;
		int y = 0;
		int z = 0;
		for (Entry<String, Object> entry : map.entrySet())
			if (entry.getKey().equals("x"))
				x = (int) ((double) entry.getValue());
			else if (entry.getKey().equals("y"))
				y = (int) ((double) entry.getValue());
			else if (entry.getKey().equals("z"))
				z = (int) ((double) entry.getValue());
		return new HomePos(x, y, z);
	}
}