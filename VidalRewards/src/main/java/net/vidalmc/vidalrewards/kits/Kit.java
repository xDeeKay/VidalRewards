package net.vidalmc.vidalrewards.kits;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Kit {

	private int cost;
	
	private List<String> required;

	private List<ItemStack> items;

	private int guiPosition;
	private String guiMaterial;
	private String guiName;
	private List<String> guiLore;

	public Kit(Integer cost, List<String> required, List<ItemStack> items, Integer guiPosition, String guiMaterial, String guiName, List<String> guiLore) {
		this.cost = cost;
		this.required = required;
		this.items = items;
		this.guiPosition = guiPosition;
		this.guiMaterial = guiMaterial;
		this.guiName = guiName;
		this.guiLore = guiLore;
	}

	public int getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}
	
	public List<String> getRequired() {
		return this.required;
	}

	public void setRequired(List<String> required) {
		this.required = required;
	}

	public List<ItemStack> getItems() {
		return this.items;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public int getGuiPosition() {
		return this.guiPosition;
	}

	public void setGuiPosition(Integer guiPosition) {
		this.guiPosition = guiPosition;
	}

	public String getGuiMaterial() {
		return this.guiMaterial;
	}

	public void setGuiMaterial(String guiMaterial) {
		this.guiMaterial = guiMaterial;
	}

	public String getGuiName() {
		return this.guiName;
	}

	public void setGuiName(String guiName) {
		this.guiName = guiName;
	}

	public List<String> getGuiLore() {
		return this.guiLore;
	}

	public void setGuiLore(List<String> guiLore) {
		this.guiLore = guiLore;
	}
}
