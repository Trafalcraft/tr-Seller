package com.trafalcraft.seller.util;

import com.trafalcraft.seller.Main;
import org.bukkit.entity.Player;

public enum Msg {

        PREFIX("§9[§3r-Seller§9]> §2"),//§9[§3r-Vendeur§9]> §2
        ERROR("§9[§4tr-Seller§9]> §c"),
        NO_PERMISSIONS("§4ERROR §9§l> §r§bYou don't have permission to do that!"),
        COMMAND_USE("§9[§4tr-Seller§9]> §r§cCommand use: §6$command"),
        //sellerName
        SELLER_NAME("Seller"),
        //sellerInteract
        NO_SHOP("The seller have a problem or is closed"),
        //seller inventory
        BUY_PRICE("Buying price"),
        SELLER_PRICE("Selling price"),
        LEFT_CLICK("Left click"),
        RIGHT_CLICK("Right click"),
        SHIFT_CLICK("Shift+click"),
        BUY_ITEM("buy $nbr items"),
        LEAVE_THE_SHOP("Leave the shop"),
        NEXT_PAGE_OF_SHOP("Next page"),
        PREVIOUS_PAGE_OF_SHOP("Last page"),
        BUY_UNAVAILABLE_INVENTORY("§3You can't buy this item"),
        SELL_UNAVAILABLE_INVENTORY("§3You can't sell this item"),
        //transaction
        SUCCESS_BUY("§a====================================================\n"
                + "§3You spend §6$moneySpent for §6$amountItem $itemType\n"
                + "§3You have now $currentMoney \n"
                + "§a===================================================="),
        /*
         * 	SUCCESS_BUY("§a====================================================\n"
                + "§3Vous avez dépensez §6$moneySpent pour §6$amountItem $itemType\n"
                + "§3Vous avez maintenant $currentMoney\n"
                + "§a===================================================="),
         */
        BUY_UNAVAILABLE("§a====================================================\n"
                + "§3You can't buy this item\n"//§3Vous ne pouvez pas acheter cette item
                + "§a===================================================="),
        FAILURE_BUY_NO_ENOUGH_MONEY("§a====================================================\n"
                + "§3You don't have enough, You need§6 $moneyMissing more"
                + "§3for $amount of §6$itemType\n"
                + "§a===================================================="),
        /*
         * 	FAILURE_BUY("§a====================================================\n"
                        + "§3Vous n'avez pas assez, il vous manque§6 $moneyMissing Tcs"
                        + "§3pour un stack de §6$itemType\n"
                        + "§a===================================================="),
         */
        SUCCESS_SELL("§a====================================================\n"
                + "§3You have earned §6$moneySpent for §6$amountItem $itemType\n"
                + "§3You have now $currentMoney\n"
                + "§a===================================================="),

        /*
         * 	SUCCESS_SELL("§a====================================================\n"
                + "§3Vous avez gagné §6$moneySpent pour §6$amountItem $itemType\n"
                + "§3Vous avez maintenant $currentMoney\n"
                + "§a===================================================="),
         */
        SELL_UNAVAILABLE("§a====================================================\n"
                + "§3You can't sell this item\n"
                + "§a===================================================="),
        FAILURE_SELL_NO_ITEM("§a====================================================\n"
                + "§3You can't sell this item\n"
                + "§a====================================================");
    /*
	 * 	FAILURE_SELL("§a====================================================\n"
			+ "§3Vous ne pouvez pas vendre cette item\n"
			+ "§a====================================================");
	 */
    private String value;

        Msg(String value) {
                this.value = value;
        }

        public static void sendHelp(Player sender) {
                sender.sendMessage("");
                sender.sendMessage("§3§l-------------------Seller-------------------");
                sender.sendMessage("§3/seller manageShop <Type> §b- Manage a shop and create his file.");
                sender.sendMessage("§3/seller removeShop <type> - Remove a shop and remove his file.");
                sender.sendMessage("§3/seller typeList §b- List the shops.");
                sender.sendMessage("§3/seller updateAllShops §b- Update all shops from file.");
                sender.sendMessage("§3/seller reload - to reload the config file");
                sender.sendMessage("§3------------------------------------------------");
                sender.sendMessage("");
        }

        public static void load() {
                PREFIX.replaceBy(Main.getPlugin().getConfig().getString("Msg.default.prefix").replace("&", "§"));
                ERROR.replaceBy(Main.getPlugin().getConfig().getString("Msg.default.error").replace("&", "§"));
                NO_PERMISSIONS.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.default.no_permission").replace("&", "§"));
                COMMAND_USE
                        .replaceBy(Main.getPlugin().getConfig().getString("Msg.default.command_use").replace("&", "§"));
                //complement
                SELLER_NAME
                        .replaceBy(Main.getPlugin().getConfig().getString("Complement.seller_name").replace("&", "§"));
                //sellerInteract
                NO_SHOP.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInteract.noShop").replace("&", "§"));
                //inventory vendeur
                BUY_PRICE.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.buy_price").replace("&", "§"));
                SELLER_PRICE.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.seller_price").replace("&", "§"));
                LEFT_CLICK.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.left_click").replace("&", "§"));
                RIGHT_CLICK.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.right_click").replace("&", "§"));
                SHIFT_CLICK.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.shift_click").replace("&", "§"));
                BUY_ITEM.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.buy_item").replace("&", "§"));
                LEAVE_THE_SHOP.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.leave_the_shop").replace("&", "§"));
                NEXT_PAGE_OF_SHOP.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.next_page_of_shop")
                                .replace("&", "§"));
                PREVIOUS_PAGE_OF_SHOP.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.previous_page_of_shop")
                                .replace("&", "§"));
                BUY_UNAVAILABLE_INVENTORY.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.buy_unavailable_inventory")
                                .replace("&", "§"));
                SELL_UNAVAILABLE_INVENTORY.replaceBy(
                        Main.getPlugin().getConfig().getString("Msg.sellerInventory.sell_unavailable_inventory")
                                .replace("&", "§"));
                //transaction
                StringBuilder text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig().getStringList("Msg.transaction.success_buy")) {
                        text1.append(text).append("\n");
                }
                SUCCESS_BUY.replaceBy(text1.toString().replace("&", "§"));
                text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig()
                        .getStringList("Msg.transaction.failure_buy_no_enough_money")) {
                        text1.append(text).append("\n");
                }
                FAILURE_BUY_NO_ENOUGH_MONEY.replaceBy(text1.toString().replace("&", "§"));
                text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig().getStringList("Msg.transaction.buy_unavailable")) {
                        text1.append(text).append("\n");
                }
                BUY_UNAVAILABLE.replaceBy(text1.toString().replace("&", "§"));
                text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig().getStringList("Msg.transaction.success_sell")) {
                        text1.append(text).append("\n");
                }
                SUCCESS_SELL.replaceBy(text1.toString().replace("&", "§"));
                text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig().getStringList("Msg.transaction.failure_sell_no_item")) {
                        text1.append(text).append("\n");
                }
                FAILURE_SELL_NO_ITEM.replaceBy(text1.toString().replace("&", "§"));
                text1 = new StringBuilder();
                for (String text : Main.getPlugin().getConfig().getStringList("Msg.transaction.sell_unavailable")) {
                        text1.append(text).append("\n");
                }
                SELL_UNAVAILABLE.replaceBy(text1.toString().replace("&", "§"));
        }

        public String toString() {
                return value;
        }

        private void replaceBy(String value) {
                this.value = value;
        }

}
