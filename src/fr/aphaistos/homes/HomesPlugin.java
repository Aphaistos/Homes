package fr.aphaistos.homes;

import org.bukkit.plugin.java.JavaPlugin;

public class HomesPlugin extends JavaPlugin {
	
	private final HomeHandler handler = new HomeHandler(getLogger(), "plugins/homes.json");
	
	@Override
	public void onEnable() {
		handler.readData();
		getCommand("home").setExecutor(new HomeCommand(handler));
	}
}