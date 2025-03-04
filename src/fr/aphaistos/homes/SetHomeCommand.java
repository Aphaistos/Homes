package fr.aphaistos.homes;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.aphaistos.homes.json.Home;
import fr.aphaistos.homes.json.HomePos;

public class SetHomeCommand implements CommandExecutor {
	
	private HomeHandler handler;
	
	public SetHomeCommand(HomeHandler handler) {
		this.handler = handler;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String command_str, String[] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage("§cHome command needs to be executed by a player.");
			return true;
		}
		UUID uuid = player.getUniqueId();
		Location player_loc = player.getLocation();
		double x = player_loc.getX();
		double y = player_loc.getY();
		double z = player_loc.getZ();

		if (args.length == 0) {
			player.sendMessage("§6Set home §cmain §6(x=§a" + x + "§6, y=§a" + y + "§6, z=§a" + z +"§6).");
			this.handler.setHome(uuid, new Home("main", new HomePos(x, y, z)));
			return false;
		} else if (args.length == 1) {
			String home_name = args[1];
			player.sendMessage("§6Set home §c" + home_name + " §6(x=§a" + x + "§6, y=§a" + y + "§6, z=§a" + z +"§6).");
			this.handler.setHome(uuid, new Home(home_name, new HomePos(x, y, z)));
			return false;
		}
		return true;
	}

}
