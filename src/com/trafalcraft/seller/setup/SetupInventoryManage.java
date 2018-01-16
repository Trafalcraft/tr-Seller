package com.trafalcraft.seller.setup;

import com.trafalcraft.seller.Main;
import com.trafalcraft.seller.util.Msg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class SetupInventoryManage {

        public static void openManageInventory(Player p, String type, int pageNumber) {
                Inventory inventory = Bukkit
                        .createInventory(p, 54,
                                "§r§4§4§4Manage>" + Msg.SELLER_NAME + "§r§4>§r§4" + type + "§r§4>§r§4" + pageNumber);
                File file = new File(Main.getPlugin().getDataFolder() + "//shops//", type + ".yml");
                YamlConfiguration yc;
                if (file.exists()) {
                        yc = YamlConfiguration.loadConfiguration(file);
                        for (int i = 0; i < 45; i++) {
                                Object itemTest = yc.get("page." + pageNumber + ".item." + i + ".itemStack");
                                if (itemTest instanceof ItemStack) {
                                        ItemStack item = (ItemStack) itemTest;
                                        inventory.setItem(i, item);
                                }
                        }
                }
                if (pageNumber != 1) {
                        ItemStack oldPage = new ItemStack(Material.ARROW);
                        ItemMeta oldPageMeta = oldPage.getItemMeta();
                        oldPageMeta.setDisplayName("§aPrevious page");
                        oldPage.setItemMeta(oldPageMeta);
                        inventory.setItem(47, oldPage);
                }
                ItemStack save = new ItemStack(Material.STAINED_CLAY);
                save.setDurability((short) 13);
                ItemMeta saveMeta = save.getItemMeta();
                saveMeta.setDisplayName("§aSave");
                save.setItemMeta(saveMeta);
                inventory.setItem(48, save);
                ItemStack close = new ItemStack(Material.STAINED_CLAY);
                close.setDurability((short) 14);
                ItemMeta closeMeta = close.getItemMeta();
                closeMeta.setDisplayName("§4Cancel");
                close.setItemMeta(closeMeta);
                inventory.setItem(50, close);
                ItemStack nextPage = new ItemStack(Material.ARROW);
                ItemMeta nextPageMeta = nextPage.getItemMeta();
                nextPageMeta.setDisplayName("§aNext page");
                nextPage.setItemMeta(nextPageMeta);
                inventory.setItem(51, nextPage);
                p.openInventory(inventory);
        }
}
