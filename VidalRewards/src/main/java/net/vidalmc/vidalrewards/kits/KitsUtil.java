package net.vidalmc.vidalrewards.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.vidalmc.vidalrewards.Main;

public class KitsUtil {

	public Main plugin;

	public KitsUtil(Main plugin) {
		this.plugin = plugin;
	}

	public void addKit(Player player, String kit, int cost, List<String> required) {

		if (!plugin.kit.containsKey(kit)) {

			ItemStack[] inventory = player.getInventory().getContents();

			List<ItemStack> items = new ArrayList<ItemStack>();

			for (ItemStack item : inventory) {
				if (item != null) {
					items.add(item);
				}
			}

			int guiPosition = plugin.kit.size() + 1;

			String guiMaterial = plugin.getConfig().getString("gui.main.slots.kits.material");

			if (!items.isEmpty()) {
				guiMaterial = items.get(0).getType().toString().toLowerCase() + ":" + items.get(0).getDurability();
			}

			plugin.getConfig().set("kits." + kit, "");
			plugin.getConfig().set("kits." + kit + ".cost", cost);
			plugin.getConfig().set("kits." + kit + ".required", required);
			plugin.getConfig().set("kits." + kit + ".items", items);
			plugin.getConfig().set("kits." + kit + ".gui.position", guiPosition);
			plugin.getConfig().set("kits." + kit + ".gui.material", guiMaterial);
			plugin.getConfig().set("kits." + kit + ".gui.name", kit);
			plugin.getConfig().set("kits." + kit + ".gui.lore", new ArrayList<String>());
			plugin.saveConfig();

			plugin.kit.put(kit, new Kit(cost, required, items, guiPosition, guiMaterial, kit, new ArrayList<String>()));
		}
	}
	
	public void deleteKit(String kit) {

		if (plugin.kit.containsKey(kit)) {

			plugin.getConfig().set("kits." + kit, null);
			plugin.saveConfig();

			plugin.kit.remove(kit);
		}
	}

	public void giveKit(Player player, String kit) {

		if (plugin.kit.containsKey(kit)) {

			List<ItemStack> items = plugin.kit.get(kit).getItems();

			for (ItemStack item : items) {
				player.getInventory().addItem(item);
			}
		}
	}
}
