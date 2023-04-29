package net.vidalmc.vidalrewards.spawners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import net.vidalmc.vidalrewards.Main;

public class SpawnersListener implements Listener {

	public Main plugin;

	public SpawnersUtil spawnersUtil;

	public SpawnersListener(Main plugin) {
		this.plugin = plugin;
		this.spawnersUtil = this.plugin.spawnersUtil;
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {

		Player player = event.getPlayer();
		
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		for (String spawner : plugin.spawner.keySet()) {

			String material = plugin.spawner.get(spawner).getGuiMaterial();

			String[] materialParts = material.split(":");
			String itemMaterialString = materialParts[0];
			String itemDamageString = materialParts[1];

			if (itemMaterialString.equalsIgnoreCase("player_skull")) {
				
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

				SkullMeta meta = (SkullMeta) item.getItemMeta();

				meta.setOwner(itemDamageString);

				meta.setDisplayName(spawner);

				List<String> loreList = new ArrayList<String>();
				//loreList.addAll(lore);
				meta.setLore(loreList);
				
				item.setAmount(1);

				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

				item.setItemMeta(meta);
				
				//if (glowing == true) {
				//	item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
				//}
				
				items.add(item);
				
				System.out.println("added skull " + spawner + " for " + player.getName());
			}
		}
		
		plugin.skulls.put(player.getName(), items);
		
		System.out.println("final skulls: " + items);
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {

		Block block = event.getBlock();

		if (block.getType().equals(Material.MOB_SPAWNER)) {

			ItemStack item = event.getItemInHand();

			if (item.getItemMeta().hasDisplayName()) {

				String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());

				if (itemName.endsWith("Spawner")) {

					String[] itemNameParts = itemName.split(" ");
					String mob = itemNameParts[0];

					BlockState blockState = block.getState();
					CreatureSpawner spawner = (CreatureSpawner) blockState;
					spawner.setSpawnedType(EntityType.valueOf(mob.toUpperCase()));
					blockState.update();
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {

		Block block = event.getBlock();

		if (block.getType().equals(Material.MOB_SPAWNER)) {

			BlockState blockState = block.getState();
			CreatureSpawner spawner = (CreatureSpawner) blockState;
			String mob = spawner.getSpawnedType().toString();

			event.setExpToDrop(0);

			block.getWorld().dropItemNaturally(block.getLocation(), spawnersUtil.spawner(mob));
		}
	}
}
