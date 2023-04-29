package net.vidalmc.vidalrewards.permissions;

import java.util.List;

public class Permission {

	private int cost;

	private String node;

	private int guiPosition;
	private String guiMaterial;
	private String guiName;
	private List<String> guiLore;

	public Permission(Integer cost, String node, Integer guiPosition, String guiMaterial, String guiName, List<String> guiLore) {
		this.cost = cost;
		this.node = node;
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

	public String getNode() {
		return this.node;
	}

	public void setNode(String node) {
		this.node = node;
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
