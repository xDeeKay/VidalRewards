package net.vidalmc.vidalrewards.permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class AddPermissionCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public PermissionsUtil permissionsUtil;

	public AddPermissionCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.permissionsUtil = this.plugin.permissionsUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("addpermission")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 3) {

					String permission = args[0];
					String costString = args[1];
					String node = args[2];

					if (!plugin.permission.containsKey(permission)) {

						if (util.isInt(costString)) {

							int cost = Integer.parseInt(costString);

							permissionsUtil.addPermission(player, permission, cost, node);

							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.add-permission").replace("%permission%", permission).replace("%cost%", costString).replace("%node%", node)));

						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addpermission <permission> <cost> <node>")));
						}
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-add-permission").replace("%permission%", permission)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addpermission <permission> <cost> <node>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
