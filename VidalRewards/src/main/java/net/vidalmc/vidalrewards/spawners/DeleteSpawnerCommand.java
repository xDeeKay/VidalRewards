package net.vidalmc.vidalrewards.spawners;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class DeleteSpawnerCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public SpawnersUtil spawnersUtil;

	public DeleteSpawnerCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.spawnersUtil = this.plugin.spawnersUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("deletespawner")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String spawner = args[0];

					if (plugin.spawner.containsKey(spawner)) {

						spawnersUtil.deleteSpawner(spawner);

						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.delete-spawner").replace("%spawner%", spawner)));

					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-delete-spawner").replace("%spawner%", spawner)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/deletespawner <spawner>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
