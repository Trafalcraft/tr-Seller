package com.trafalcraft.seller;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.util.Msg;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    private final int sellerInventorySizeWithItemsAndMenu = 54;
    private JavaPlugin plugin = Main.getPlugin();

    @EventHandler
    public void onPlayerInteractWithNpc(NPCRightClickEvent e) {
        for (Trait trait : e.getNPC().getTraits()) {
            if (trait.getName().equalsIgnoreCase("tr-seller")) {

                return;
            }
        }
    }

    //TODO add multi-page
    @EventHandler
    public void onNPCRightClick(NPCRightClickEvent e) {
        //if(e.getNPC().getName().contains("§r§4+"+Msg.SELLER_NAME)){
        for (Trait trait : e.getNPC().getTraits()) {
            if (trait.getName().equalsIgnoreCase("tr-seller")) {
                String Type = e.getNPC().getName().replaceAll("§[a-f0-9]", "");
                YamlConfiguration yc = FileManager.getShop(Type);
                if (yc == null) {
                    e.getClicker().sendMessage(Msg.ERROR + Msg.NO_SHOP.toString());
                    return;
                }
                Inventory inventory = Bukkit.createInventory(e.getClicker(), sellerInventorySizeWithItemsAndMenu, "§r§4§4" + Msg.SELLER_NAME + ">" + Type);
                for (int f = 1; f <= sellerInventorySizeWithItemsAndMenu - 9; f++) {
                    List<String> lore = new ArrayList<>();
                    int purchase = yc.getInt("item.name." + f + ".buy");
                    int sale = yc.getInt("item.name." + f + ".sell");
                    if (purchase != 0) {
                        lore.add(Msg.BUY_PRICE + ": §6" + Main.getEcon().format(purchase));
                    } else {
                        lore.add(Msg.BUY_UNAVAILABLE_INVENTORY.toString());
                    }
                    if (sale != 0) {
                        lore.add(Msg.SELLER_PRICE + ": §6" + Main.getEcon().format(sale));
                    } else {
                        lore.add(Msg.SELL_UNAVAILABLE_INVENTORY.toString());
                    }
                    lore.add("");
                    lore.add("§6" + Msg.LEFT_CLICK + ": ");
                    lore.add(Msg.BUY_ITEM.toString().replace("$nbr", "1") + ": ");
                    lore.add("§6" + Msg.RIGHT_CLICK + ": ");
                    lore.add(Msg.BUY_ITEM.toString().replace("$nbr", "8") + ": ");
                    lore.add("§6" + Msg.SHIFT_CLICK + ": ");
                    lore.add(Msg.BUY_ITEM.toString().replace("$nbr", "64") + ": ");
                    lore.addAll(yc.getStringList("item.name." + f + ".lore"));
                    ItemStack item;
                    if (yc.getString("item.name." + f + ".type") != null) {
                        Material m = Material.matchMaterial(yc.getString("item.name." + f + ".type"));
                        item = new ItemStack(m);
                    } else {

                        item = new ItemStack(Material.AIR);
                    }
                    //ItemStack item2 = new ItemStack(item);
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        String dn = yc.getString("item.name." + f + ".DisplayName");
                        dn = dn.replaceAll("&", "§");
                        meta.setDisplayName("§6" + dn);
                        meta.setLore(lore);
                        item.setDurability((short) yc.getInt("item.name." + f + ".data"));
                        item.setItemMeta(meta);
                        for (String s : yc.getStringList("item.name." + f + ".enchantment")) {
                            try {
                                item.addUnsafeEnchantment(Enchantment.getByName(s.split(":")[0]), Integer.parseInt(s.split(":")[1]));
                            } catch (NumberFormatException nbf) {
                                Bukkit.getLogger().warning("Problem in the shop " + Type + " and the enchantment " + s.split(":")[0] + ", the level is not valid.");
                            }
                        }
                    }
                    inventory.addItem(item);
                }
                //
                /*ItemStack oldPage = new ItemStack(Material.SKULL_ITEM);
		    	oldPage.setDurability((short) 3);
		    	ItemMeta oldPageMeta = oldPage.getItemMeta();
		    	oldPageMeta.setDisplayName("§aPrevious page");
		    	((SkullMeta) oldPageMeta).setOwner("MHF_ArrowLeft");
		    	oldPage.setItemMeta(oldPageMeta);
		    	inventory.setItem(45, oldPage);*/
                //
                ItemStack leave = new ItemStack(Material.BARRIER);
                ItemMeta leaveMeta = leave.getItemMeta();
                leaveMeta.setDisplayName("§4" + Msg.LEAVE_THE_SHOP);
                leave.setItemMeta(leaveMeta);
                inventory.setItem(49, leave);
                //
		    	/*ItemStack nextPage = new ItemStack(Material.SKULL_ITEM);
		    	nextPage.setDurability((short) 3); 
		    	SkullMeta nextPageMeta = (SkullMeta) nextPage.getItemMeta();
		    	nextPageMeta.setDisplayName("§aNext page");
		    	nextPageMeta.setOwner("MHF_ArrowRight");
		    	nextPage.setItemMeta(nextPageMeta);
			    inventory.setItem(53, nextPage);*/

                e.getClicker().openInventory(inventory);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getTitle().startsWith("§r§4§4" + Msg.SELLER_NAME)) {
            e.setCancelled(true);
        }
    }


    @EventHandler(priority = EventPriority.LOW)
    public void inventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        double bs = Main.getEcon().getBalance(p.getPlayer());
        if (e.getInventory().getTitle().startsWith("§r§4§4" + Msg.SELLER_NAME)) {
            if (e.getRawSlot() < 0 || e.getRawSlot() >= 90) {
                e.setCancelled(true);
                return;
            }
            ItemStack item = e.getCurrentItem();
            //int item = e.getCurrentItem().getTypeId();
            //sell
            if(e.getRawSlot() == 49){
                e.getWhoClicked().getOpenInventory().close();
            }
            if (e.getRawSlot() > 53) {
                if (item.getType() == Material.AIR) {
                    e.setCancelled(true);
                    return;
                }
                switch (e.getRawSlot()) {
                    case 45:

                        e.setCancelled(true);
                        return;
                    case 49:
                        p.closeInventory();
                        e.setCancelled(true);
                        return;
                    case 53:

                        e.setCancelled(true);
                        return;
                }
                if (e.isRightClick()) {
                    e.setCancelled(true);
                    return;
                }
                if (e.isLeftClick()) {
                    Transactions t = new Transactions();
                    t.sell(item, (Player) e.getWhoClicked(), e.getRawSlot(), e.getInventory().getTitle().split(">")[e.getInventory().getTitle().split(">").length - 1]);
                }
                e.setCancelled(true);
                return;
            } else {
                //Purchase
                e.setCancelled(true);
                Transactions t = new Transactions();
                if (e.isShiftClick()) {
                    t.buy(item, p, e.getRawSlot(), 64, bs, e.getInventory().getTitle().split(">")[e.getInventory().getTitle().split(">").length - 1]);
                } else if (e.isRightClick()) {
                    t.buy(item, p, e.getRawSlot(), 8, bs, e.getInventory().getTitle().split(">")[e.getInventory().getTitle().split(">").length - 1]);
                } else if (e.isLeftClick()) {
                    t.buy(item, p, e.getRawSlot(), 1, bs, e.getInventory().getTitle().split(">")[e.getInventory().getTitle().split(">").length - 1]);
                }
            }
            e.setCancelled(true);
        }
    }


}
