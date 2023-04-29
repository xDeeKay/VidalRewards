package net.vidalmc.vidalrewards.permissions;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.gui.GuiUtil;
import net.vidalmc.vidalrewards.util.Util;

public class PermissionsCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public GuiUtil guiUtil;

	public PermissionsCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("permissions")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "permissions");

				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/permissions")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
