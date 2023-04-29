package net.vidalmc.vidalrewards.permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class DeletePermissionCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public PermissionsUtil permissionsUtil;

	public DeletePermissionCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.permissionsUtil = this.plugin.permissionsUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("deletepermission")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 1) {

					String permission = args[0];

					if (plugin.permission.containsKey(permission)) {

						permissionsUtil.deletePermission(permission);

						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.delete-permission").replace("%permission%", permission)));

					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-delete-permission").replace("%permission%", permission)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/deletepermission <permission>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
