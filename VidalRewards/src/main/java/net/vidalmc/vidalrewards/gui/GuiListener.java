package net.vidalmc.vidalrewards.gui;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.kits.KitsUtil;
import net.vidalmc.vidalrewards.permissions.PermissionsUtil;
import net.vidalmc.vidalrewards.spawners.SpawnersUtil;

public class GuiListener implements Listener {

	public Main plugin;

	public GuiUtil guiUtil;
	public KitsUtil kitsUtil;
	public PermissionsUtil permissionsUtil;
	public SpawnersUtil spawnersUtil;

	public GuiListener(Main plugin) {
		this.plugin = plugin;
		this.guiUtil = this.plugin.guiUtil;
		this.kitsUtil = this.plugin.kitsUtil;
		this.permissionsUtil = this.plugin.permissionsUtil;
		this.spawnersUtil = this.plugin.spawnersUtil;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {

		Player player = (Player) event.getWhoClicked();

		Inventory inventory = event.getInventory();

		ItemStack item = event.getCurrentItem();

		if (inventory.getHolder() instanceof GuiInventoryHolder) {

			event.setCancelled(true);

			if (event.getRawSlot() < inventory.getSize() && item != null && !item.getType().equals(Material.AIR)) {

				int position = event.getSlot() + 1;

				String type = item.getType().toString();
				int durability = item.getDurability();
				String material = type + ":" + durability;

				String name = ChatColor.translateAlternateColorCodes('&', item.getItemMeta().getDisplayName());
				
				if (item.getType().equals(Material.SKULL_ITEM)) {
					SkullMeta meta = (SkullMeta) item.getItemMeta();
					if (meta.hasOwner()) {
						String owner = meta.getOwner();
						material = "player_skull" + ":" + owner;
					}
				}

				for (String gui : plugin.gui.keySet()) {

					for (String slot : plugin.gui.get(gui).getSlots().keySet()) {

						if (plugin.gui.get(gui).getSlots().get(slot).getPosition() == position && 
								plugin.gui.get(gui).getSlots().get(slot).getMaterial().equalsIgnoreCase(material) && 
								ChatColor.translateAlternateColorCodes('&', plugin.gui.get(gui).getSlots().get(slot).getName()).equals(name)) {

							List<String> commands = plugin.gui.get(gui).getSlots().get(slot).getCommands();

							for (String command : commands) {
								plugin.getServer().dispatchCommand(player, command.replace("%player%", player.getName()));
							}
						}
					}
					
					if (gui.equals("kits")) {
						for (String kit : plugin.kit.keySet()) {

							if (plugin.kit.get(kit).getGuiPosition() == position && 
									plugin.kit.get(kit).getGuiMaterial().equalsIgnoreCase(material) && 
									ChatColor.translateAlternateColorCodes('&', plugin.kit.get(kit).getGuiName()).equals(name)) {

								int kitSize = plugin.kit.get(kit).getItems().size();

								int i = 0;
								int freeSpace = 0;
								for (ItemStack is : player.getInventory().getContents()) {
									if (i <= 35) {
										i++;
										if (is == null) {
											freeSpace++;
										}
									}
								}

								if (freeSpace >= kitSize) {
									
									List<String> required = plugin.kit.get(kit).getRequired();
									
									String requiredString = StringUtils.join(required, ", ");

									boolean canBuy = true;
									for (String requiredKit : required) {
										if (Main.getPermissions().has(player, "vidalrewards.kit." + requiredKit)) {
											continue;
										}
										canBuy = false;
									}

									if (canBuy) {
										
										int cost = plugin.kit.get(kit).getCost();

										if (Main.getEconomy().getBalance(player.getName()) >= cost) {

											kitsUtil.giveKit(player, kit);
											permissionsUtil.givePermission(player, "vidalrewards.kit." + kit);
											Main.getEconomy().withdrawPlayer(player.getName(), cost);
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.buy-kit").replace("%kit%", kit).replace("%cost%", String.valueOf(cost))));
											guiUtil.openGui(player, "kits");

										} else {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.insufficient-funds").replace("%cost%", String.valueOf(cost))));
										}
									} else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-buy-kit").replace("%kit%", kit).replace("%required%", requiredString)));
									}
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.full-inventory").replace("%size%", String.valueOf(kitSize))));
								}
							}
						}
					}
					
					if (gui.equals("permissions")) {
						for (String permission : plugin.permission.keySet()) {

							if (plugin.permission.get(permission).getGuiPosition() == position && 
									plugin.permission.get(permission).getGuiMaterial().equalsIgnoreCase(material) && 
									ChatColor.translateAlternateColorCodes('&', plugin.permission.get(permission).getGuiName()).equals(name)) {

								String node = plugin.permission.get(permission).getNode();

								if (!Main.getPermissions().has(player, node)) {

									int cost = plugin.permission.get(permission).getCost();

									if (Main.getEconomy().getBalance(player.getName()) >= cost) {

										permissionsUtil.givePermission(player, node);
										Main.getEconomy().withdrawPlayer(player.getName(), cost);
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.buy-permission").replace("%permission%", permission).replace("%cost%", String.valueOf(cost))));
										guiUtil.openGui(player, "permissions");

									} else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.insufficient-funds").replace("%cost%", String.valueOf(cost))));
									}
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-buy-permission").replace("%permission%", permission)));
								}
							}
						}
					}
					
					if (gui.equals("spawners")) {
						for (String spawner : plugin.spawner.keySet()) {

							if (plugin.spawner.get(spawner).getGuiPosition() == position && 
									plugin.spawner.get(spawner).getGuiMaterial().equalsIgnoreCase(material) && 
									ChatColor.translateAlternateColorCodes('&', plugin.spawner.get(spawner).getGuiName()).equals(name)) {

								if (player.getInventory().firstEmpty() != -1) {

									List<String> required = plugin.spawner.get(spawner).getRequired();
									
									String requiredString = StringUtils.join(required, ", ");

									boolean canBuy = true;
									for (String requiredSpawner : required) {
										if (Main.getPermissions().has(player, "vidalrewards.spawner." + requiredSpawner)) {
											continue;
										}
										canBuy = false;
									}
									
									if (canBuy) {
										
										int cost = plugin.spawner.get(spawner).getCost();

										if (Main.getEconomy().getBalance(player.getName()) >= cost) {

											spawnersUtil.giveSpawner(player, spawner);
											permissionsUtil.givePermission(player, "vidalrewards.spawner." + spawner);
											Main.getEconomy().withdrawPlayer(player.getName(), cost);
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.buy-spawner").replace("%spawner%", spawner).replace("%cost%", String.valueOf(cost))));
											guiUtil.openGui(player, "spawners");

										} else {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.insufficient-funds").replace("%cost%", String.valueOf(cost))));
										}
									} else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.cant-buy-spawner").replace("%spawner%", spawner).replace("%required%", requiredString)));
									}
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.full-inventory").replace("%size%", String.valueOf(1))));
								}
							}
						}
					}
				}
			}
		}
	}
}
