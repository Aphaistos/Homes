package fr.aphaistos.homes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aphaistos.homes.json.Home;
import fr.aphaistos.homes.json.HomePos;

public class HomeCommand implements CommandExecutor {
	
	private HomeHandler handler;
	
	public HomeCommand(HomeHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String command_str, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("§cHome command needs to be executed by a player.");
			return true;
		}
		UUID uuid = player.getUniqueId();
		if (args.length == 0) {
			Home home = handler.getHome(uuid, "main");
			if (home != null) {
				HomePos home_pos = home.getPos();
				Location home_loc = new Location(player.getWorld(), home_pos.getX(), home_pos.getY(), home_pos.getZ());
				player.sendMessage("§6Teleporting to §cmain§6...");
				player.teleport(home_loc);
				return false;
			}
			player.sendMessage("§4You didn't set your main home.");
			player.sendMessage("§4Use the command: §c/home set main");
			return true;
		} else if (args[0].equals("set")) {
			Location player_loc = player.getLocation();
			double x = player_loc.getX();
			double y = player_loc.getY();
			double z = player_loc.getZ();
			if (args.length == 2) {
				String home_name = args[1];
				player.sendMessage("§6Set home §c" + home_name + " §6(x=§a" + x + "§6, y=§a" + y + "§6, z=§a" + z +"§6).");
				this.handler.setHome(uuid, new Home(home_name, new HomePos(x, y, z)));
				return false;
			}
			
			player.sendMessage("§6Set home §cmain §6(x=§a" + x + "§6, y=§a" + y + "§6, z=§a" + z +"§6).");
			this.handler.setHome(uuid, new Home("main", new HomePos(x, y, z)));
		} else if (args[0].equals("show")) {
			ArrayList<Home> homes = handler.getHomes(uuid);
			player.sendMessage("§6You have currently set §c" + homes.size() + "§6 homes:");
			for (Home home : homes) {
				HomePos home_pos = home.getPos();
				player.sendMessage("§6- §c" + home.getName() + "§6 (x=§a" + home_pos.getX() + "§6,y=§a" + home_pos.getY() + "§6,z=§a" + home_pos.getZ() + "§6)");
			}
		} else {
			String home_name = args[0];
			Home home = handler.getHome(uuid, home_name);
			if (home == null) {
				player.sendMessage("§4Home `§c" + home_name + "§4` isn't set.");
				player.sendMessage("§4Use the command: §c/home set " + home_name);
				return true;
			}
			HomePos home_pos = home.getPos();
			Location home_loc = new Location(player.getWorld(), home_pos.getX(), home_pos.getY(), home_pos.getZ());
			player.sendMessage("§6Teleporting to §c" + home_name + "§6...");
			player.teleport(home_loc);
		}
		return false;
	}

}