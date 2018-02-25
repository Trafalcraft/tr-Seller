package com.trafalcraft.seller.util;

import com.trafalcraft.seller.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public enum Msg {

    PREFIX("§9[§3r-Seller§9]> §2"),
    ERROR("§9[§4tr-Seller§9]> §c"),
    NO_PERMISSIONS("§4ERROR §9§l> §r§bYou don't have permission to do that!"),
    COMMAND_USE("§9[§4tr-Seller§9]> §r§cCommand use: §6$command"),
    //sellerName
    SELLER_NAME("Seller"),
    //sellerInteract
    NO_SHOP("The seller have a problem or is closed"),
    //seller inventory
    BUY_PRICE("Buying price : §6 $price"),
    SELLER_PRICE("Selling price : §6 $price"),
    LEFT_CLICK("§6Left click"),
    RIGHT_CLICK("§6Right click"),
    SHIFT_CLICK("§6Shift+click"),
    BUY_ITEM("buy $nbr items"),
    LEAVE_THE_SHOP("Leave the shop"),
    NEXT_PAGE_OF_SHOP("Next page"),
    PREVIOUS_PAGE_OF_SHOP("Previous page"),
    BUY_UNAVAILABLE_INVENTORY("§3You can't buy this item"),
    SELL_UNAVAILABLE_INVENTORY("§3You can't sell this item"),
    //transaction
    SUCCESS_BUY("§a====================================================\n"
            + "§3You spend §6$moneySpent for §6$amountItem $itemType\n"
            + "§3You have now $currentMoney \n"
            + "§a===================================================="),
    BUY_UNAVAILABLE("§a====================================================\n"
            + "§3You can't buy this item\n"
            + "§a===================================================="),
    FAILURE_BUY_NO_ENOUGH_MONEY("§a====================================================\n"
            + "§3You don't have enough, You need§6 $moneyMissing more"
            + "§3for $amount of §6$itemType\n"
            + "§a===================================================="),
    SUCCESS_SELL("§a====================================================\n"
            + "§3You have earned §6$moneySpent for §6$amountItem $itemType\n"
            + "§3You have now $currentMoney\n"
            + "§a===================================================="),
    SELL_UNAVAILABLE("§a====================================================\n"
            + "§3You can't sell this item\n"
            + "§a===================================================="),
    FAILURE_SELL_NO_ITEM("§a====================================================\n"
            + "§3You can't sell this item\n"
            + "§a===================================================="),
    //admin
    RELOAD("§a reload success"),
    HELP_HEADER("§3§l-------------------Seller-------------------"),
    HELP_FOOTER("§3/seller manageShop <Type> §b- Manage a shop and create his file."),
    HELP_MANAGE_SHOP("§3/seller removeShop <type> - Remove a shop and remove his file."),
    HELP_REMOVE_SHOP("§3/seller typeList §b- List the shops."),
    HELP_TYPE_LIST("§3/seller updateAllShops §b- Update all shops from file."),
    HELP_UPDATE_ALL_SHOPS("§3/seller reload - to reload the config file"),
    HELP_RELOAD("§3------------------------------------------------"),
    ///manageShop
    SAVE_SHOP("§aSave"),
    CANCEL_SHOP("§4Cancel"),
    ///COMMAND
    COMMAND_UPDATE_ALL_SHOPS("§aNPCs inventory updated"),
    COMMAND_REMOVE_SHOP("The file $fileName was removed"),
    COMMAND_SHOP_LIST("Different types of NPCs are : $shopList"),;


    private String value;

    Msg(String value) {
        this.value = value;
    }

    public static void sendHelp(Player sender) {
        sender.sendMessage("");
        sender.sendMessage(HELP_HEADER.toString());
        sender.sendMessage(HELP_MANAGE_SHOP.toString());
        sender.sendMessage(HELP_REMOVE_SHOP.toString());
        sender.sendMessage(HELP_TYPE_LIST.toString());
        sender.sendMessage(HELP_UPDATE_ALL_SHOPS.toString());
        sender.sendMessage(HELP_RELOAD.toString());
        sender.sendMessage(HELP_FOOTER.toString());
        sender.sendMessage("");
    }

    public static void load() {
        FileConfiguration config = Main.getPlugin().getConfig();
        PREFIX.replaceBy(config.getString("Msg.default.prefix"));
        ERROR.replaceBy(config.getString("Msg.default.error"));
        NO_PERMISSIONS.replaceBy(
                config.getString("Msg.default.no_permission"));
        COMMAND_USE
                .replaceBy(config.getString("Msg.default.command_use"));
        //complement
        SELLER_NAME
                .replaceBy(config.getString("Complement.seller_name"));
        //sellerInteract
        NO_SHOP.replaceBy(
                config.getString("Msg.sellerInteract.noShop"));
        //inventory vendeur
        BUY_PRICE.replaceBy(
                config.getString("Msg.sellerInventory.buy_price"));
        SELLER_PRICE.replaceBy(
                config.getString("Msg.sellerInventory.seller_price"));
        LEFT_CLICK.replaceBy(
                config.getString("Msg.sellerInventory.left_click"));
        RIGHT_CLICK.replaceBy(
                config.getString("Msg.sellerInventory.right_click"));
        SHIFT_CLICK.replaceBy(
                config.getString("Msg.sellerInventory.shift_click"));
        BUY_ITEM.replaceBy(
                config.getString("Msg.sellerInventory.buy_item"));
        LEAVE_THE_SHOP.replaceBy(
                config.getString("Msg.sellerInventory.leave_the_shop"));
        NEXT_PAGE_OF_SHOP.replaceBy(
                config.getString("Msg.sellerInventory.next_page_of_shop")
        );
        PREVIOUS_PAGE_OF_SHOP.replaceBy(
                config.getString("Msg.sellerInventory.previous_page_of_shop")
        );
        SAVE_SHOP.replaceBy(
                config.getString("Msg.admin.manageShop.save_button")
        );
        CANCEL_SHOP.replaceBy(
                config.getString("Msg.admin.manageShop.cancel_button")
        );
        BUY_UNAVAILABLE_INVENTORY.replaceBy(
                config.getString("Msg.sellerInventory.buy_unavailable_inventory")
        );
        SELL_UNAVAILABLE_INVENTORY.replaceBy(
                config.getString("Msg.sellerInventory.sell_unavailable_inventory")
        );
        //transaction
        StringBuilder text1 = new StringBuilder();
        for (String text : config.getStringList("Msg.transaction.success_buy")) {
            text1.append(text).append("\n");
        }
        SUCCESS_BUY.replaceBy(text1.toString());
        text1 = new StringBuilder();
        for (String text : config
                .getStringList("Msg.transaction.failure_buy_no_enough_money")) {
            text1.append(text).append("\n");
        }
        FAILURE_BUY_NO_ENOUGH_MONEY.replaceBy(text1.toString());
        text1 = new StringBuilder();
        for (String text : config.getStringList("Msg.transaction.buy_unavailable")) {
            text1.append(text).append("\n");
        }
        BUY_UNAVAILABLE.replaceBy(text1.toString());
        text1 = new StringBuilder();
        for (String text : config.getStringList("Msg.transaction.success_sell")) {
            text1.append(text).append("\n");
        }
        SUCCESS_SELL.replaceBy(text1.toString());
        text1 = new StringBuilder();
        for (String text : config.getStringList("Msg.transaction.failure_sell_no_item")) {
            text1.append(text).append("\n");
        }
        FAILURE_SELL_NO_ITEM.replaceBy(text1.toString());
        text1 = new StringBuilder();
        for (String text : config.getStringList("Msg.transaction.sell_unavailable")) {
            text1.append(text).append("\n");
        }
        SELL_UNAVAILABLE.replaceBy(text1.toString());
        //admin
        RELOAD.replaceBy(config.getString("Msg.admin.reload"));
        HELP_HEADER.replaceBy(config.getString("Msg.admin.help.header"));
        HELP_FOOTER.replaceBy(config.getString("Msg.admin.help.footer"));
        HELP_MANAGE_SHOP.replaceBy(config.getString("Msg.admin.help.manageShop"));
        HELP_REMOVE_SHOP.replaceBy(config.getString("Msg.admin.help.removeShop"));
        HELP_TYPE_LIST.replaceBy(config.getString("Msg.admin.help.typeList"));
        HELP_UPDATE_ALL_SHOPS.replaceBy(config.getString("Msg.admin.help.updateAllShops"));
        HELP_RELOAD.replaceBy(config.getString("Msg.admin.help.reload"));

        COMMAND_UPDATE_ALL_SHOPS.replaceBy(config.getString("Msg.admin.command.updateAllShops"));
        COMMAND_REMOVE_SHOP.replaceBy(config.getString("Msg.admin.command.removeShop"));
        COMMAND_SHOP_LIST.replaceBy(config.getString("Msg.admin.command.shopList"));

    }

    public String toString() {
        return value;
    }

    private void replaceBy(String value) {
        this.value = value.replace("&", "§");
    }

}
