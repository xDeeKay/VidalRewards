package net.vidalmc.vidalrewards.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.vidalmc.vidalrewards.Main;
import net.vidalmc.vidalrewards.kits.Kit;
import net.vidalmc.vidalrewards.permissions.Permission;
import net.vidalmc.vidalrewards.spawners.Spawner;

public class GuiUtil {

	public Main plugin;

	public GuiUtil(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public ItemStack item(String material, String name, List<String> lore, int amount, boolean glowing) {

		ItemStack item;

		String[] materialParts = material.split(":");
		String itemMaterialString = materialParts[0];
		String itemDamageString = materialParts[1];

		if (itemMaterialString.equalsIgnoreCase("player_skull")) {

			item = plugin.skulls.get("xDeeKay").get(1);
			

			

		} else {

			Material itemMaterial = Material.valueOf(itemMaterialString.toUpperCase());
			
			int itemDamage = Integer.parseInt(itemDamageString);

			item = new ItemStack(itemMaterial, 1, (short) itemDamage);

			ItemMeta meta = item.getItemMeta();

			meta.setDisplayName(name);

			List<String> loreList = new ArrayList<String>();
			loreList.addAll(lore);
			meta.setLore(loreList);

			item.setAmount(amount);

			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

			item.setItemMeta(meta);

			if (glowing == true) {
				item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
			}
		}

		return item;
	}

	public void itemModule(Inventory inventory, int position, String material, String name, List<String> lore, boolean glowing) {

		position = position - 1;

		name = ChatColor.translateAlternateColorCodes('&', name);

		List<String> loreList = new ArrayList<String>();
		for (String loreLine : lore) {
			loreList.add(ChatColor.translateAlternateColorCodes('&', loreLine));
		}

		inventory.setItem(position, item(material, name, loreList, 1, glowing));
	}

	public void openGui(Player player, String gui) {

		if (plugin.gui.containsKey(gui)) {

			String title = ChatColor.translateAlternateColorCodes('&', plugin.gui.get(gui).getTitle());
			int rows = plugin.gui.get(gui).getRows() * 9;

			Inventory inventory = plugin.getServer().createInventory(new GuiInventoryHolder(), rows, title);

			if (gui.equals("kits")) {
				for (Map.Entry<String, Kit> kits : plugin.kit.entrySet()) {

					String name = kits.getKey();
					Kit kit = kits.getValue();

					int guiPosition = kit.getGuiPosition();
					String guiMaterial = kit.getGuiMaterial();
					String guiName = kit.getGuiName();
					List<String> guiLore = kit.getGuiLore();

					boolean glowing = false;
					if (player.hasPermission("vidalrewards.kit." + name)) {
						glowing = true;
					}

					itemModule(inventory, guiPosition, guiMaterial, guiName, guiLore, glowing);
				}
			}

			if (gui.equals("permissions")) {
				for (Map.Entry<String, Permission> permissions : plugin.permission.entrySet()) {

					Permission permission = permissions.getValue();

					int guiPosition = permission.getGuiPosition();
					String guiMaterial = permission.getGuiMaterial();
					String guiName = permission.getGuiName();
					List<String> guiLore = permission.getGuiLore();

					itemModule(inventory, guiPosition, guiMaterial, guiName, guiLore, false);
				}
			}

			if (gui.equals("spawners")) {
				for (Map.Entry<String, Spawner> spawners : plugin.spawner.entrySet()) {

					String mob = spawners.getKey();
					Spawner spawner = spawners.getValue();

					int guiPosition = spawner.getGuiPosition();
					String guiMaterial = spawner.getGuiMaterial();
					String guiName = spawner.getGuiName();
					List<String> guiLore = spawner.getGuiLore();

					boolean glowing = false;
					if (player.hasPermission("vidalrewards.spawner." + mob)) {
						glowing = true;
					}

					itemModule(inventory, guiPosition, guiMaterial, guiName, guiLore, glowing);
				}
			}

			// Slots

			HashMap<String, Slot> slots = plugin.gui.get(gui).getSlots();

			for (String slot : slots.keySet()) {

				int position = plugin.getConfig().getInt("gui." + gui + ".slots." + slot + ".position");
				String material = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".material");
				String name = plugin.getConfig().getString("gui." + gui + ".slots." + slot + ".name");
				List<String> lore = plugin.getConfig().getStringList("gui." + gui + ".slots." + slot + ".lore");

				itemModule(inventory, position, material, name, lore, false);
			}

			player.openInventory(inventory);
		}
	}
}
