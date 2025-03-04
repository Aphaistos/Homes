package fr.aphaistos.homes.json;

import java.util.Map.Entry;

import com.google.gson.internal.LinkedTreeMap;

public class HomePos {
	private final double x;
	private final double y;
	private final double z;
	
	public HomePos(final double x, final double y, final double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getZ() {
		return z;
	}

	public static HomePos fromLinkedTreeMap(LinkedTreeMap<String, Object> map) {
		double x = 0;
		double y = 0;
		double z = 0;
		for (Entry<String, Object> entry : map.entrySet())
			if (entry.getKey().equals("x"))
				x = (double) entry.getValue();
			else if (entry.getKey().equals("y"))
				y = (double) entry.getValue();
			else if (entry.getKey().equals("z"))
				z = (double) entry.getValue();
		return new HomePos(x, y, z);
	}
}