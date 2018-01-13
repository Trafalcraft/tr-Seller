package com.trafalcraft.seller;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.util.Msg;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

class Transactions {

    private int sellerInventorySizeWithItems = 44;

        public void buy(ItemStack item, Player p, int slot, int amount, double moneyP, String type) {
                YamlConfiguration yamlShop = FileManager.getShop(type);
        if (item.getType() == Material.AIR || amount < 1) {
                return;
        }
        for (int i = 1; i < sellerInventorySizeWithItems; i++) {
            if (yamlShop.getString("item.name." + i + ".type") != null &&
                    (item.getType() == Material.matchMaterial(yamlShop.getString("item.name." + i + ".type"))) &&
                    (item.getDurability() == (short) yamlShop.getInt("item.name." + i + ".data"))) {
                if (yamlShop.getInt("item.name." + i + ".buy") == 0) {
                    p.sendMessage(Msg.BUY_UNAVAILABLE.toString());
                    return;
                }
                int price = yamlShop.getInt("item.name." + i + ".buy");
                if (moneyP >= price * amount) {
                    Main.getEcon().withdrawPlayer(p, price * amount);
                    p.sendMessage(Msg.SUCCESS_BUY.toString().replace("$moneySpent", Main.getEcon().format((price * amount)) + "")
                            .replace("$amountItem", amount + "")
                            .replace("$itemType", yamlShop.getString("item.name." + i + ".DisplayName"))
                            .replace("$currentMoney", Main.getEcon().format(Main.getEcon().getBalance(p.getPlayer()))));
                    for (int i1 = 0; i1 < amount; i1++) {
                        ItemStack item2 = new ItemStack(item.getType());
                        item2.setDurability((short) yamlShop.getInt("item.name." + i + ".data"));
                        p.getInventory().addItem(item2);
                    }
                    return;
                } else {
                    p.sendMessage(Msg.FAILURE_BUY_NO_ENOUGH_MONEY.toString().replace("$moneyMissing", "" + Main.getEcon().format((price * amount) - moneyP))
                            .replace("$itemType", yamlShop.getString("item.name." + i + ".DisplayName"))
                            .replace("$amount", amount + ""));
                }
            }
        }
    }

    public void sell(ItemStack item, Player p, int slot, String Type) {
        YamlConfiguration yc = FileManager.getShop(Type);
        int NotForSale = 0;
        for (int i = 1; i < sellerInventorySizeWithItems; i++) {
            NotForSale = NotForSale + 1;
            if ((yc.getString("item.name." + i + ".type") != null) &&
                    (item.getType() == Material.matchMaterial(yc.getString("item.name." + i + ".type"))) &&
                    (item.getDurability() == (short) yc.getInt("item.name." + i + ".data"))) {
                if (yc.getInt("item.name." + i + ".sell") == 0) {
                    p.sendMessage(Msg.SELL_UNAVAILABLE.toString());
                    return;
                }
                int price = yc.getInt("item.name." + i + ".sell");
                Main.getEcon().depositPlayer(p, price * item.getAmount());
                p.sendMessage(Msg.SUCCESS_SELL.toString().replace("$moneySpent", Main.getEcon().format(price * item.getAmount()) + "")
                        .replace("$amountItem", item.getAmount() + "")
                        .replace("$itemType", yc.getString("item.name." + i + ".DisplayName"))
                        .replace("$currentMoney", Main.getEcon().format(Main.getEcon().getBalance(p.getPlayer()))));
                if (slot >= 81 && slot <= 89) {
                    int slot2 = slot - 81;
                    p.getInventory().setItem(slot2, new ItemStack(Material.AIR));
                }
                if (slot <= 80 && slot >= sellerInventorySizeWithItems +1) {
                    int slot2 = slot - sellerInventorySizeWithItems -1;
                    p.getInventory().setItem(slot2, new ItemStack(Material.AIR));
                }
                return;
            }
            if (NotForSale == sellerInventorySizeWithItems -1) {
                p.sendMessage(Msg.FAILURE_SELL_NO_ITEM.toString());
                return;
            }
        }
    }
}
