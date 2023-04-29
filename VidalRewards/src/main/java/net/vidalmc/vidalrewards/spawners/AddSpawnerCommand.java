package net.vidalmc.vidalrewards.spawners;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class AddSpawnerCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public SpawnersUtil spawnersUtil;

	public AddSpawnerCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.spawnersUtil = this.plugin.spawnersUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("addspawner")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length >= 3) {

					String spawner = args[0];
					String costString = args[1];
					String[] usernames = StringUtils.join(args, ' ', 2, args.length).split(" ");
					
					List<String> required = new ArrayList<String>();
					
					for (String username : usernames) {
						required.add(username);
					}

					if (!plugin.spawner.containsKey(spawner)) {

						if (util.isInt(costString)) {

							int cost = Integer.parseInt(costString);

							spawnersUtil.addSpawner(player, spawner, cost, required);

							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.add-spawner").replace("%spawner%", spawner).replace("%cost%", costString)));

						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addspawner <spawner> <cost> <required>")));
						}
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-add-spawner").replace("%spawner%", spawner)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addspawner <spawner> <cost> <required>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
