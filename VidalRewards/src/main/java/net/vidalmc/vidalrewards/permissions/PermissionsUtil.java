package net.vidalmc.vidalrewards.permissions;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.vidalmc.vidalrewards.Main;

public class PermissionsUtil {

	public Main plugin;

	public PermissionsUtil(Main plugin) {
		this.plugin = plugin;
	}
	
	public void addPermission(Player player, String permission, int cost, String node) {

		if (!plugin.permission.containsKey(permission)) {

			int guiPosition = plugin.permission.size() + 1;

			String guiMaterial = plugin.getConfig().getString("gui.main.slots.permissions.material");

			if (player.getInventory().getItemInMainHand() != null && !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				guiMaterial = player.getInventory().getItemInMainHand().getType().toString().toLowerCase() + ":" + player.getInventory().getItemInMainHand().getDurability();
			}

			plugin.getConfig().set("permissions." + permission, "");
			plugin.getConfig().set("permissions." + permission + ".cost", cost);
			plugin.getConfig().set("permissions." + permission + ".node", node);
			plugin.getConfig().set("permissions." + permission + ".gui.position", guiPosition);
			plugin.getConfig().set("permissions." + permission + ".gui.material", guiMaterial);
			plugin.getConfig().set("permissions." + permission + ".gui.name", permission);
			plugin.getConfig().set("permissions." + permission + ".gui.lore", new ArrayList<String>());
			plugin.saveConfig();

			plugin.permission.put(permission, new Permission(cost, node, guiPosition, guiMaterial, permission, new ArrayList<String>()));
		}
	}
	
	public void deletePermission(String permission) {

		if (plugin.permission.containsKey(permission)) {

			plugin.getConfig().set("permissions." + permission, null);
			plugin.saveConfig();

			plugin.permission.remove(permission);
		}
	}

	public void givePermission(Player player, String node) {
		if (!Main.getPermissions().has(player, node)) {
			Main.getPermissions().playerAdd(player, node);
		}
	}
}
