package com.trafalcraft.vendeur.inventaire;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class Inventaire {

	private ArrayList<ItemStack> inventaire = new ArrayList<ItemStack>();
	
	public void addItemToinventory(ItemStack item){
		inventaire.add(item);
	}
	
	public void removeItemToinventory(ItemStack item){
		inventaire.remove(item);
	}
}
