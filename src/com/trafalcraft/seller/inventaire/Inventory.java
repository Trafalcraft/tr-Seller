package com.trafalcraft.seller.inventaire;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<ItemStack> inventory = new ArrayList<ItemStack>();

    public void addItemToInventory(ItemStack item) {
        inventory.add(item);
    }

    public void removeItemToInventory(ItemStack item) {
        inventory.remove(item);
    }
}
