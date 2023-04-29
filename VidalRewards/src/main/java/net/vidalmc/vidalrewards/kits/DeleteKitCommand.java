package net.vidalmc.vidalrewards.kits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class DeleteKitCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public KitsUtil kitsUtil;

	public DeleteKitCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.kitsUtil = this.plugin.kitsUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("deletekit")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String kit = args[0];

					if (plugin.kit.containsKey(kit)) {

						kitsUtil.deleteKit(kit);

						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.delete-kit").replace("%kit%", kit)));

					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-delete-kit").replace("%kit%", kit)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/deletekit <kit>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
