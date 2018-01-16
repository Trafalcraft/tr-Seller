package com.trafalcraft.seller.setup;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.util.Msg;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class SetupInventoryListener implements Listener {

        @EventHandler(priority = EventPriority.LOW)
        public void inventoryClick(InventoryClickEvent event) {
                Player p = (Player) event.getWhoClicked();
                String[] invTitleSplit = event.getInventory().getTitle().split("§r§4>§r§4");
                if (event.getInventory().getTitle().startsWith("§r§4§4§4Manage>" + Msg.SELLER_NAME)) {
                        String sellerType = invTitleSplit[invTitleSplit.length - 2];
                        int pageNumber = Integer.parseInt(invTitleSplit[invTitleSplit.length - 1]);
                        if (event.getRawSlot() < 0 || event.getRawSlot() >= 90) {
                                event.setCancelled(true);
                                return;
                        }
                        ItemStack item = event.getCurrentItem();
                        if (event.getRawSlot() == 50) {
                                p.getOpenInventory().close();
                        } else if (event.getRawSlot() == 48) {
                                try {
                                        FileManager.updateFile(event.getInventory(), sellerType);
                                        p.getOpenInventory().close();
                                        event.setCancelled(true);
                                } catch (IOException e) {
                                        p.sendMessage(Msg.ERROR.toString()
                                                + "An error as occurred in the config.yml please check logs!");
                                        e.printStackTrace();
                                }
                        } else if ((event.getRawSlot() == 47 || event.getRawSlot() == 51)
                                && item.getType() != Material.AIR) {
                                try {
                                        FileManager.updateFile(event.getInventory(), sellerType);
                                } catch (IOException e) {
                                        p.sendMessage(Msg.ERROR.toString()
                                                + "An error as occurred in the config.yml please check logs!");
                                        e.printStackTrace();
                                }
                                if (event.getRawSlot() == 47) {
                                        SetupInventoryManage.openManageInventory(p, sellerType, pageNumber - 1);
                                        event.setCancelled(true);
                                } else {
                                        SetupInventoryManage.openManageInventory(p, sellerType, pageNumber + 1);
                                        event.setCancelled(true);
                                }
                        } else if (event.getRawSlot() >= 45 && event.getRawSlot() <= 53) {
                                event.setCancelled(true);
                        }

                }
        }
}