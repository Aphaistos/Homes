package fr.aphaistos.homes;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class HomeEvents implements Listener {
	
	private HomeHandler home_handler;
	
	public HomeEvents(HomeHandler home_handler) {
		this.home_handler = home_handler;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		UUID uuid = event.getPlayer().getUniqueId();
		this.home_handler.addOwner(uuid);
	}
	
}