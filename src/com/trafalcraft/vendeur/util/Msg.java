package com.trafalcraft.vendeur.util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.vendeur.Main;

public enum Msg {
	
	PREFIX("§9[§3r-Seller§9]> §2"),//§9[§3r-Vendeur§9]> §2
	ERROR("§9[§4tr-Seller§9]> §c"),
	NO_PERMISSIONS("§4ERROR §9§l> §r§bYou dont have permission to do that!"),
	COMMAND_USE("§9[§4tr-Seller§9]> §r§cCommand use: §6$command"),
	//sellerName
	SELLER_NAME("Seller"),
	MONEY("coins"),
	//inventaire vendeur
	BUY_PRICE("Buying price"),
	SELLER_PRICE("Selling price"),
	LEFT_CLICK("Left click"),
	RIGHT_CLICK("Right click"),
	SHIFT_CLICK("Shift+click"),
	BUY_ITEM("buy $nbr items"),
	LEAVE_THE_SHOP("Leave the shop"),
	//transaction
	SUCCESS_BUY("§a====================================================\n"
			+ "§3You spend §6$moneySpent "+Msg.MONEY+" for §6$amountItem $itemType\n"
			+ "§3You have now $currentMoney \n"
			+ "§a===================================================="),
	/*
	 * 	SUCCESS_BUY("§a====================================================\n"
			+ "§3Vous avez dépensez §6$moneySpent coins pour §6$amountItem $itemType\n"
			+ "§3Vous avez maintenant $currentMoney\n"
			+ "§a===================================================="),
	 */
	BUY_UNAVAILABLE("§a====================================================\n"
			+ "§3You can't buy this item\n"//§3Vous ne pouvez pas acheter cette item
			+ "§a===================================================="),
	FAILURE_BUY_NO_ENOUGHT_MONEY("§a====================================================\n"
					+ "§3You don't have enough, You need§6 $moneyMissing "+Msg.MONEY+" more"
					+ "§3for $amount of §6$itemType\n"
					+ "§a===================================================="),
	/*
	 * 	FAILURE_BUY("§a====================================================\n"
					+ "§3Vous n'avez pas assez, il vous manque§6 $moneyMissing Tcs"
					+ "§3pour un stack de §6$itemType\n"
					+ "§a===================================================="),
	 */
	SUCCESS_SELL("§a====================================================\n"
			+ "§3You have earned §6$moneySpent "+Msg.MONEY+" for §6$amountItem $itemType\n"
			+ "§3You have now $currentMoney\n"
			+ "§a===================================================="),
			
	/*
	 * 	SUCCESS_SELL("§a====================================================\n"
			+ "§3Vous avez gagné §6$moneySpent coins pour §6$amountItem $itemType\n"
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
	
	static JavaPlugin plugin = Main.getPlugin();
	  public static void sendHelp(Player sender){
	        sender.sendMessage("");
	        sender.sendMessage("§3§l-------------------Vendeur-------------------");
	        sender.sendMessage("§3/seller addType <Type> §b- Add a type of seller and create this file.");
	        sender.sendMessage("§3/seller typeList §b- List the types of seller.");
	        sender.sendMessage("§3/seller getMaterial §b- Retrieve the \"Bukkit\" value of the item.");
	        //sender.sendMessage("§4-Non disponible...");
	        sender.sendMessage("§3------------------------------------------------");
	        sender.sendMessage("");
		  }
	  
	  
	    private String value;
		private Msg(String value) {
			this.value = value;
	    }
		
	    public String toString(){
	    	return value;
	    }
	    public void replaceby(String value){
			this.value = value;
	    }
	    
	    public static void load(){
	    	PREFIX.replaceby(Main.getPlugin().getConfig().getString("Msg.default.prefix").replace("&", "§"));
	    	ERROR.replaceby(Main.getPlugin().getConfig().getString("Msg.default.error").replace("&", "§"));
	    	NO_PERMISSIONS.replaceby(Main.getPlugin().getConfig().getString("Msg.default.no_permission").replace("&", "§"));
	    	COMMAND_USE.replaceby(Main.getPlugin().getConfig().getString("Msg.default.command_use").replace("&", "§"));
	    	//complement
	    	SELLER_NAME.replaceby(Main.getPlugin().getConfig().getString("Complement.seller_name").replace("&", "§"));
	    	MONEY.replaceby(Main.getPlugin().getConfig().getString("Complement.money").replace("&", "§"));
	    	//inventaire vendeur
	    	BUY_PRICE.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.buy_price").replace("&", "§"));
	    	SELLER_PRICE.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.seller_price").replace("&", "§"));
	    	LEFT_CLICK.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.left_click").replace("&", "§"));
	    	RIGHT_CLICK.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.right_click").replace("&", "§"));
	    	SHIFT_CLICK.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.shift_click").replace("&", "§"));
	    	BUY_ITEM.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.buy_item").replace("&", "§"));
	    	LEAVE_THE_SHOP.replaceby(Main.getPlugin().getConfig().getString("Msg.sellerInventory.leave_the_shop").replace("&", "§"));
	    	//transaction
	    	String text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.success_buy")){
	    		text1+=text+"\n";
	    	}
	    	SUCCESS_BUY.replaceby(text1.replace("&", "§").replace("$moneyType", Msg.MONEY.toString()));
	    	text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.failure_buy_no_enought_money")){
	    		text1+=text+"\n";
	    	}
	    	FAILURE_BUY_NO_ENOUGHT_MONEY.replaceby(text1.replace("&", "§").replace("$moneyType", Msg.MONEY.toString()));
	    	text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.buy_unavailable")){
	    		text1+=text+"\n";
	    	}
	    	BUY_UNAVAILABLE.replaceby(text1.replace("&", "§"));
	    	text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.success_sell")){
	    		text1+=text+"\n";
	    	}
	    	SUCCESS_SELL.replaceby(text1.replace("&", "§").replace("$moneyType", Msg.MONEY.toString()));
	    	text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.failure_sell_no_item")){
	    		text1+=text+"\n";
	    	}
	    	FAILURE_SELL_NO_ITEM.replaceby(text1.replace("&", "§"));
	    	text1 = "";
	    	for(String text: Main.getPlugin().getConfig().getStringList("Msg.transaction.sell_unavailable")){
	    		text1+=text+"\n";
	    	}
	    	SELL_UNAVAILABLE.replaceby(text1.replace("&", "§"));
	    }
	    
	    /*public static void DefaultMsg(){
	    	//default
	    	Main.getPlugin().getConfig().set("Msg.default.prefix", PREFIX.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.error", ERROR.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.no_permission", NO_PERMISSIONS.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.default.command_use", COMMAND_USE.toString().replace("§", "&"));
	    	//sellerName
	    	Main.getPlugin().getConfig().set("Complement.seller_name", SELLER_NAME.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Complement.money", MONEY.toString().replace("§", "&"));
	    	//inventaire vendeur
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.buy_price", BUY_PRICE.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.seller_price", SELLER_PRICE.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.left_click", LEFT_CLICK.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.right_click", RIGHT_CLICK.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.shift_click", SHIFT_CLICK.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.buy_item", BUY_ITEM.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.sellerInventory.leave_the_shop", LEAVE_THE_SHOP.toString().replace("§", "&"));
	    	//transaction
	    	Main.getPlugin().getConfig().set("Msg.transaction.success_buy", SUCCESS_BUY.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.transaction.failure_buy_no_enought_money", FAILURE_BUY_NO_ENOUGHT_MONEY.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.transaction.buy_unavailable", BUY_UNAVAILABLE.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.transaction.success_sell", SUCCESS_SELL.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.transaction.failure_sell_no_item", FAILURE_SELL_NO_ITEM.toString().replace("§", "&"));
	    	Main.getPlugin().getConfig().set("Msg.transaction.sell_unavailable", SELL_UNAVAILABLE.toString().replace("§", "&"));
	    	
	    	
	    }*/
	
}
