package net.vidalmc.vidalrewards.spawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;
import net.vidalmc.vidalrewards.Main;

public class SpawnersUtil {

	public Main plugin;

	public SpawnersUtil(Main plugin) {
		this.plugin = plugin;
	}
	
	public void addSpawner(Player player, String spawner, int cost, List<String> required) {

		if (!plugin.spawner.containsKey(spawner)) {
			
			int guiPosition = plugin.spawner.size() + 1;

			String guiMaterial = plugin.getConfig().getString("gui.main.slots.spawners.material");

			if (player.getInventory().getItemInMainHand() != null && !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				guiMaterial = player.getInventory().getItemInMainHand().getType().toString().toLowerCase() + ":" + player.getInventory().getItemInMainHand().getDurability();
			}

			plugin.getConfig().set("spawners." + spawner, "");
			plugin.getConfig().set("spawners." + spawner + ".cost", cost);
			plugin.getConfig().set("spawners." + spawner + ".required", required);
			plugin.getConfig().set("spawners." + spawner + ".gui.position", guiPosition);
			plugin.getConfig().set("spawners." + spawner + ".gui.material", guiMaterial);
			plugin.getConfig().set("spawners." + spawner + ".gui.name", spawner);
			plugin.getConfig().set("spawners." + spawner + ".gui.lore", new ArrayList<String>());
			plugin.saveConfig();

			plugin.spawner.put(spawner, new Spawner(cost, required, guiPosition, guiMaterial, spawner, new ArrayList<String>()));
		}
	}
	
	public void deleteSpawner(String spawner) {

		if (plugin.spawner.containsKey(spawner)) {

			plugin.getConfig().set("spawners." + spawner, null);
			plugin.saveConfig();

			plugin.spawner.remove(spawner);
		}
	}

	public void giveSpawner(Player player, String mob) {

		if (plugin.spawner.containsKey(mob)) {

			player.getInventory().addItem(spawner(mob));
		}
	}

	public ItemStack spawner(String mob) {

		ItemStack spawner = new ItemStack(Material.MOB_SPAWNER, 1, (short) 0);

		ItemMeta meta = spawner.getItemMeta();

		mob = mob.substring(0, 1).toUpperCase() + mob.substring(1).toLowerCase();

		meta.setDisplayName(ChatColor.WHITE + mob + " Spawner");

		spawner.setItemMeta(meta);

		return spawner;
	}
}
