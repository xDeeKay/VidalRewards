package net.vidalmc.vidalrewards.gui;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.util.Util;

public class VidalRewardsCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public GuiUtil guiUtil;

	public VidalRewardsCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.guiUtil = this.plugin.guiUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("vidalrewards") || cmd.getName().equalsIgnoreCase("vr")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length == 0) {
					guiUtil.openGui(player, "main");
					
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("reload")) {
						if (player.hasPermission("vidalrewards.reload")) {
							plugin.reloadConfig();
							util.loadConfig();
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.plugin-reload")));
							
						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
						}
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/vidalrewards reload")));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/vidalrewards")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
