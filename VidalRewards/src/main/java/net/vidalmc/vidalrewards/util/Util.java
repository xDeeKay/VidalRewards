package net.vidalmc.vidalrewards.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.inventory.ItemStack;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.gui.Gui;
import net.vidalmc.vidalrewards.gui.Slot;
import net.vidalmc.vidalrewards.kits.Kit;
import net.vidalmc.vidalrewards.permissions.Permission;
import net.vidalmc.vidalrewards.spawners.Spawner;

public class Util {

	public Main plugin;

	public Util(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("unchecked")
	public void loadConfig() {

		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();

		plugin.kit.clear();
		if (plugin.getConfig().isSet("kits")) {

			Set<String> kits = plugin.getConfig().getConfigurationSection("kits").getKeys(false);

			if (!kits.isEmpty()) {

				for (String kit : kits) {

					int cost = plugin.getConfig().getInt("kits." + kit + ".cost");
					
					List<String> required = plugin.getConfig().getStringList("kits." + kit + ".required");

					List<ItemStack> items = (List<ItemStack>) plugin.getConfig().getList("kits." + kit + ".items");

					int guiPosition = plugin.getConfig().getInt("kits." + kit + ".gui.position");
					String guiMaterial = plugin.getConfig().getString("kits." + kit + ".gui.material");
					String guiName = plugin.getConfig().getString("kits." + kit + ".gui.name");
					List<String> guiLore = plugin.getConfig().getStringList("kits." + kit + ".gui.lore");

					plugin.kit.put(kit, new Kit(cost, required, items, guiPosition, guiMaterial, guiName, guiLore));
				}
			}
		}

		plugin.permission.clear();
		if (plugin.getConfig().isSet("permissions")) {

			Set<String> permissions = plugin.getConfig().getConfigurationSection("permissions").getKeys(false);

			if (!permissions.isEmpty()) {

				for (String permission : permissions) {

					int cost = plugin.getConfig().getInt("permissions." + permission + ".cost");

					String node = plugin.getConfig().getString("permissions." + permission + ".node");

					int guiPosition = plugin.getConfig().getInt("permissions." + permission + ".gui.position");
					String guiMaterial = plugin.getConfig().getString("permissions." + permission + ".gui.material");
					String guiName = plugin.getConfig().getString("permissions." + permission + ".gui.name");
					List<String> guiLore = plugin.getConfig().getStringList("permissions." + permission + ".gui.lore");

					plugin.permission.put(permission, new Permission(cost, node, guiPosition, guiMaterial, guiName, guiLore));
				}
			}
		}

		plugin.spawner.clear();
		if (plugin.getConfig().isSet("spawners")) {

			Set<String> spawners = plugin.getConfig().getConfigurationSection("spawners").getKeys(false);

			if (!spawners.isEmpty()) {

				for (String spawner : spawners) {

					int cost = plugin.getConfig().getInt("spawners." + spawner + ".cost");

					List<String> required = plugin.getConfig().getStringList("spawners." + spawner + ".required");
					
					int guiPosition = plugin.getConfig().getInt("spawners." + spawner + ".gui.position");
					String guiMaterial = plugin.getConfig().getString("spawners." + spawner + ".gui.material");
					String guiName = plugin.getConfig().getString("spawners." + spawner + ".gui.name");
					List<String> guiLore = plugin.getConfig().getStringList("spawners." + spawner + ".gui.lore");

					plugin.spawner.put(spawner, new Spawner(cost, required, guiPosition, guiMaterial, guiName, guiLore));
				}
			}
		}
		
		plugin.gui.clear();
		if (plugin.getConfig().isSet("gui")) {

			Set<String> guis = plugin.getConfig().getConfigurationSection("gui").getKeys(false);

			if (!guis.isEmpty()) {

				for (String gui : guis) {

					String title = plugin.getConfig().getString("gui." + gui + ".settings.title");
					int rows = plugin.getConfig().getInt("gui." + gui + ".settings.rows");

					HashMap<String, Slot> slots = new HashMap<String, Slot>();

					for (String slot : plugin.getConfig().getConfigurationSection("gui." + gui + ".slots").getKeys(false)) {

						int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");
						String material = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".material");
						String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name");
						List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");
						List<String> commands = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".commands");

						slots.put(slot, new Slot(position, material, name, lore, commands));
					}

					plugin.gui.put(gui, new Gui(title, rows, slots));
				}
			}
		}
	}

	public boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
