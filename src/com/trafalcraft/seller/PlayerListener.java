package com.trafalcraft.seller;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.util.Msg;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

        @EventHandler
        public void onNPCRightClick(NPCRightClickEvent e) {
                for (Trait trait : e.getNPC().getTraits()) {
                        if (trait.getName().equalsIgnoreCase("tr-seller")) {
                                String type = e.getNPC().getName().replaceAll("§[a-f0-9]", "");
                                YamlConfiguration yc = FileManager.getShop(type);
                                if (yc == null) {
                                        e.getClicker().sendMessage(Msg.ERROR + Msg.NO_SHOP.toString());
                                        return;
                                }
                                Inventory inventory = setupShopInventory(e.getClicker(), type, yc, 1);

                                e.getClicker().openInventory(inventory);
                                return;
                        }
                }
        }

        private Inventory setupShopInventory(Player player, String type, YamlConfiguration yc, int pageNumber) {
                int numberOfPages = yc.getConfigurationSection("page").getKeys(false).size();
                int sellerInventorySizeWithItemsAndMenu = 54;
                Inventory inventory = Bukkit
                        .createInventory(player, sellerInventorySizeWithItemsAndMenu,
                                "§r§4§4" + Msg.SELLER_NAME + "§r§4>§r§4" + type
                                        + "§r§4>§r§4" + pageNumber + "/" + numberOfPages);
                for (String itemPosString : yc.getConfigurationSection("page." + pageNumber + ".item").getKeys(false)) {
                        int itemPos = Integer.parseInt(itemPosString);
                        List<String> lore = new ArrayList<>();
                        int purchase = yc.getInt("page." + pageNumber + ".item." + itemPos + ".buy");
                        int sale = yc.getInt("page." + pageNumber + ".item." + itemPos + ".sell");
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
                        ItemStack item;
                        Object itemTest = yc.get("page." + pageNumber + ".item." + itemPos + ".itemStack");
                        if (itemTest instanceof ItemStack) {
                                item = ((ItemStack) itemTest).clone(); //clone() avoid lore duplication
                        } else {
                                item = new ItemStack(Material.AIR);
                        }
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                                lore.add("==============");
                                List<String> itemLore = meta.getLore();
                                if (itemLore != null) {
                                        lore.addAll(itemLore);
                                }
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                        }
                        inventory.setItem(itemPos, item);
                }
                if (pageNumber != 1) {
                        ItemStack oldPage = new ItemStack(Material.ARROW);
                        ItemMeta oldPageMeta = oldPage.getItemMeta();
                        oldPageMeta.setDisplayName(Msg.PREVIOUS_PAGE_OF_SHOP.toString());
                        oldPage.setItemMeta(oldPageMeta);
                        inventory.setItem(47, oldPage);
                }

                ItemStack leave = new ItemStack(Material.BARRIER);
                ItemMeta leaveMeta = leave.getItemMeta();
                leaveMeta.setDisplayName("§4" + Msg.LEAVE_THE_SHOP);
                leave.setItemMeta(leaveMeta);
                inventory.setItem(49, leave);

                if (pageNumber != numberOfPages) {
                        ItemStack nextPage = new ItemStack(Material.ARROW);
                        ItemMeta nextPageMeta = nextPage.getItemMeta();
                        nextPageMeta.setDisplayName(Msg.NEXT_PAGE_OF_SHOP.toString());
                        nextPage.setItemMeta(nextPageMeta);
                        inventory.setItem(51, nextPage);
                }

                return inventory;
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

                if (e.getInventory().getTitle().startsWith("§r§4§4" + Msg.SELLER_NAME)) {
                        e.setCancelled(true);
                        if (e.getRawSlot() < 0 || e.getRawSlot() >= 90) {
                                return;
                        }
                        ItemStack item = e.getCurrentItem();
                        if (item.getType() == Material.AIR) {
                                return;
                        }
                        if (e.getRawSlot() == 49) {
                                e.getWhoClicked().getOpenInventory().close();
                                return;
                        }
                        String[] inventoryTitleSplit = e.getInventory().getTitle().split("§r§4>§r§4");
                        String type = inventoryTitleSplit[inventoryTitleSplit.length - 2];
                        int page = Integer.parseInt(inventoryTitleSplit[inventoryTitleSplit.length - 1].split("/")[0]);
                        if (e.getRawSlot() == 47) {
                                YamlConfiguration yc = FileManager.getShop(type);
                                Inventory inv = setupShopInventory(p, type, yc, page - 1);
                                p.openInventory(inv);
                                return;
                        } else if (e.getRawSlot() == 51) {
                                YamlConfiguration yc = FileManager.getShop(type);
                                Inventory inv = setupShopInventory(p, type, yc, page + 1);
                                p.openInventory(inv);
                                return;
                        }
                        //sell
                        if (e.getRawSlot() > 53) {
                                if (e.isRightClick()) {
                                        return;
                                }
                                if (e.isLeftClick()) {
                                        Transactions t = new Transactions();
                                        t.sell(p, item, type, e.getRawSlot());
                                }
                        } else {
                                //Purchase
                                Transactions t = new Transactions();
                                if (e.isShiftClick()) {
                                        t.buy(p, type, page, e.getRawSlot(), 64);
                                } else if (e.isRightClick()) {
                                        t.buy(p, type, page, e.getRawSlot(), 8);

                                } else if (e.isLeftClick()) {
                                        t.buy(p, type, page, e.getRawSlot(), 1);

                                }
                        }
                }
        }

}
