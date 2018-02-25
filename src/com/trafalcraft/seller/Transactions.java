package com.trafalcraft.seller;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.util.Msg;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class Transactions {

        public void buy(Player p, String type, int pageNumber, int slot, int amount) {
                YamlConfiguration yamlShop = FileManager.getShop(type);
                int buyPrice = yamlShop.getInt("page." + pageNumber + ".item." + slot + ".buy") * amount;
                ItemStack item = (ItemStack) yamlShop.get("page." + pageNumber + ".item." + slot + ".itemStack");
                if (buyPrice == 0) {
                        p.sendMessage(Msg.BUY_UNAVAILABLE.toString());
                        return;
                }
                String itemName;
                if (item.getItemMeta().getDisplayName() != null) {
                        itemName = item.getItemMeta().getDisplayName();
                } else {
                        itemName = WordUtils.capitalizeFully(item.getType().name().replace("_", ""));
                }
                double playerBalance = Main.getEcon().getBalance(p.getPlayer());
                if (playerBalance >= buyPrice) {
                        Main.getEcon().withdrawPlayer(p, buyPrice);

                        p.sendMessage(Msg.SUCCESS_BUY.toString()
                                .replace("$moneySpent", Main.getEcon().format(buyPrice) + "")
                                .replace("$amountItem", amount + "")
                                .replace("$itemType", itemName)
                                .replace("$currentMoney", Main.getEcon()
                                        .format(Main.getEcon().getBalance(p.getPlayer()))));
                        for (int i1 = 0; i1 < amount; i1++) {
                                p.getInventory().addItem(item);
                        }

                } else {
                        p.sendMessage(Msg.FAILURE_BUY_NO_ENOUGH_MONEY.toString()
                                .replace("$moneyMissing",
                                        "" + Main.getEcon().format(buyPrice - playerBalance))
                                .replace("$itemType", itemName)
                                .replace("$amount", amount + ""));
                }
        }

        public void sell(Player p, ItemStack item, String type, int slot) {
                YamlConfiguration yamlShop = FileManager.getShop(type);
                for (String page : yamlShop.getConfigurationSection("page").getKeys(false)) {
                        for (String itemIndex : yamlShop
                                .getConfigurationSection("page." + page + ".item").getKeys(false)) {
                                ItemStack shopItem = (ItemStack) yamlShop
                                        .get("page." + page + ".item." + itemIndex + ".itemStack");
                                item.getItemMeta().setDisplayName(null);
                                ItemStack tempItem = item.clone();
                                tempItem.setAmount(1);
                                if (tempItem.equals(shopItem)) {
                                        int sellPrice = yamlShop
                                                .getInt("page." + page + ".item." + itemIndex + ".sell");
                                        String itemName;
                                        if (shopItem.getItemMeta().getDisplayName() != null) {
                                                itemName = shopItem.getItemMeta().getDisplayName();
                                        } else {
                                                itemName = WordUtils
                                                        .capitalizeFully(shopItem.getType().name().replace("_", ""));
                                        }
                                        if (sellPrice != 0) {
                                                Main.getEcon().depositPlayer(p, sellPrice * item.getAmount());
                                                p.sendMessage(Msg.SUCCESS_SELL.toString()
                                                        .replace("$moneySpent",
                                                                Main.getEcon()
                                                                        .format(sellPrice * item.getAmount())
                                                                        + "")
                                                        .replace("$amountItem", item.getAmount() + "")
                                                        .replace("$itemType", itemName)
                                                        .replace("$currentMoney",
                                                                Main.getEcon().format(Main.getEcon()
                                                                        .getBalance(p.getPlayer()))));
                                                if (slot >= 81 && slot <= 89) {
                                                        int slot2 = slot - 81;
                                                        p.getInventory()
                                                                .setItem(slot2, new ItemStack(Material.AIR));
                                                }
                                                int sellerInventorySizeWithItems = 44;
                                                if (slot <= 80 && slot >= sellerInventorySizeWithItems + 1) {
                                                        int slot2 = slot - sellerInventorySizeWithItems - 1;
                                                        p.getInventory()
                                                                .setItem(slot2, new ItemStack(Material.AIR));
                                                }
                                                return;

                                        }
                                }
                        }
                }
                p.sendMessage(Msg.FAILURE_SELL_NO_ITEM.toString());
        }
}
