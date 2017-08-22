package com.trafalcraft.seller.inventaire;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

class ManageInventory {


    private final Map<String, Inventory> listInventory = Maps.newHashMap();

    public void addPlayerCache(String name) {
        if (!this.listInventory.containsKey(name)) {
            Inventory inventory = new Inventory();
            listInventory.put(name, inventory);
        }

    }

    public boolean contains(String name) {
        return this.listInventory.containsKey(name);
    }

    public void removeInventory(String name) {
        if (this.listInventory.containsKey(name)) {
            listInventory.remove(name);
        }
    }

    public Map<String, Inventory> listInventory() {
        return listInventory;
    }

    public Collection<Inventory> getAllInventory() {
        return listInventory.values();
    }

    public Inventory getInventory(String name) {
        return listInventory.get(name);
    }

    public void clear() {
        listInventory.clear();
    }
}
