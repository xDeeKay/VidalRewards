package net.vidalmc.vidalrewards.kits;

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

public class AddKitCommand implements CommandExecutor {

	public Main plugin;

	public Util util;
	public KitsUtil kitsUtil;

	public AddKitCommand(Main plugin) {
		this.plugin = plugin;
		this.util = this.plugin.util;
		this.kitsUtil = this.plugin.kitsUtil;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("addkit")) {
			if (sender instanceof Player) {

				Player player = (Player) sender;

				if (args.length >= 3) {

					String kit = args[0];
					String costString = args[1];
					String[] usernames = StringUtils.join(args, ' ', 2, args.length).split(" ");

					List<String> required = new ArrayList<String>();

					for (String username : usernames) {
						required.add(username);
					}

					if (!plugin.kit.containsKey(kit)) {

						if (util.isInt(costString)) {

							int cost = Integer.parseInt(costString);

							kitsUtil.addKit(player, kit, cost, required);

							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.add-kit").replace("%kit%", kit).replace("%cost%", costString)));

						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addkit <kit> <cost> <required>")));
						}
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-add-kit").replace("%kit%", kit)));
					}
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.incorrect-command").replace("%usage%", "/addkit <kit> <cost> <required>")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.not-player")));
			}
		}
		return true;
	}
}
