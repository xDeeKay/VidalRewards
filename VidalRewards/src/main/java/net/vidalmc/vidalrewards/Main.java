package net.vidalmc.vidalrewards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.vidalmc.vidalrewards.gui.Gui;
import net.vidalmc.vidalrewards.gui.GuiListener;
import net.vidalmc.vidalrewards.gui.GuiUtil;
import net.vidalmc.vidalrewards.gui.Slot;
import net.vidalmc.vidalrewards.gui.VidalRewardsCommand;
import net.vidalmc.vidalrewards.kits.AddKitCommand;
import net.vidalmc.vidalrewards.kits.DeleteKitCommand;
import net.vidalmc.vidalrewards.kits.Kit;
import net.vidalmc.vidalrewards.kits.KitsCommand;
import net.vidalmc.vidalrewards.kits.KitsUtil;
import net.vidalmc.vidalrewards.permissions.AddPermissionCommand;
import net.vidalmc.vidalrewards.permissions.DeletePermissionCommand;
import net.vidalmc.vidalrewards.permissions.Permission;
import net.vidalmc.vidalrewards.permissions.PermissionsCommand;
import net.vidalmc.vidalrewards.permissions.PermissionsUtil;
import net.vidalmc.vidalrewards.spawners.AddSpawnerCommand;
import net.vidalmc.vidalrewards.spawners.DeleteSpawnerCommand;
import net.vidalmc.vidalrewards.spawners.Spawner;
import net.vidalmc.vidalrewards.spawners.SpawnersCommand;
import net.vidalmc.vidalrewards.spawners.SpawnersListener;
import net.vidalmc.vidalrewards.spawners.SpawnersUtil;
import net.vidalmc.vidalrewards.util.Util;

public class Main extends JavaPlugin {

	public Main main;

	public GuiUtil guiUtil;
	public KitsUtil kitsUtil;
	public PermissionsUtil permissionsUtil;
	public SpawnersUtil spawnersUtil;
	public Util util;
	
	public Map<String, Gui> gui = new HashMap<String, Gui>();
	public Map<String, Slot> slot = new HashMap<String, Slot>();

	public Map<String, Kit> kit = new HashMap<String, Kit>();
	public Map<String, Permission> permission = new HashMap<String, Permission>();
	public Map<String, Spawner> spawner = new HashMap<String, Spawner>();
	
	public Map<String, List<ItemStack>> skulls = new HashMap<String, List<ItemStack>>();

	private static Economy econ = null;
	private static net.milkbowl.vault.permission.Permission perms = null;

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public static net.milkbowl.vault.permission.Permission getPermissions() {
		return perms;
	}

	public void onEnable() {

		this.main = this;

		// Methods
		guiUtil = new GuiUtil(this);
		kitsUtil = new KitsUtil(this);
		permissionsUtil = new PermissionsUtil(this);
		spawnersUtil = new SpawnersUtil(this);
		util = new Util(this);

		// Commands
		this.getCommand("vidalrewards").setExecutor(new VidalRewardsCommand(this));
		
		this.getCommand("addkit").setExecutor(new AddKitCommand(this));
		this.getCommand("deletekit").setExecutor(new DeleteKitCommand(this));
		this.getCommand("kitpvp").setExecutor(new KitsCommand(this));
		
		this.getCommand("addpermission").setExecutor(new AddPermissionCommand(this));
		this.getCommand("deletepermission").setExecutor(new DeletePermissionCommand(this));
		this.getCommand("permissions").setExecutor(new PermissionsCommand(this));
		
		this.getCommand("addspawner").setExecutor(new AddSpawnerCommand(this));
		this.getCommand("deletespawner").setExecutor(new DeleteSpawnerCommand(this));
		this.getCommand("spawners").setExecutor(new SpawnersCommand(this));

		// Listeners
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GuiListener(this), this);
		pm.registerEvents(new SpawnersListener(this), this);

		// Loading
		util.loadConfig();

		if (!setupEconomy()) {
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupPermissions();
	}

	public void onDisable() {

	}
}
