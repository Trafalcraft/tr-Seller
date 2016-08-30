package com.trafalcraft.vendeur;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.trafalcraft.vendeur.fichier.FileManager;
import com.trafalcraft.vendeur.util.Msg;

public class transactions {
	
	public void buy(ItemStack item,Player p,int slot,int amount, double moneyP, String Type){
		YamlConfiguration yc = FileManager.getShop(Type);
		if(item.getType() == Material.AIR || amount <1){
			   return;
		}
		for(int i = 1; i<53; i++){
	        if(yc.getString("item.name."+i+".type") != null &&
	        		(item.getType() == Material.matchMaterial(yc.getString("item.name." + i + ".type"))) &&
	        		(item.getDurability() == (short)yc.getInt("item.name." + i + ".data"))){
			            if (yc.getInt("item.name."+i+".buy") == 0)
			            {
			            	p.sendMessage(Msg.BUY_UNAVAILABLE.toString());
			              return;
			            }
			   			   int prix = yc.getInt("item.name."+i+".buy");
			   			   if(moneyP >= prix*amount){
						       p.sendMessage(Msg.SUCCESS_BUY.toString().replace("$moneySpent", (prix*amount)+"")
						    		   .replace("$amountItem", amount+"")
						    		   .replace("$itemType", yc.getString("item.name."+i+".Displayname"))
						    		   .replace("$currentMoney",  Main.getEcon().format(Main.getEcon().getBalance(p.getPlayer()))));
							   for(int i1 = 0; i1<amount;i1++){
								   ItemStack item2 = new ItemStack(item.getType());
								   item2.setDurability((short) yc.getInt("item.name."+i+".data"));
								   p.getInventory().addItem(item2);
							   }
							   return;
					   }else{
						   p.sendMessage(Msg.FAILURE_BUY_NO_ENOUGHT_MONEY.toString().replace("$moneyMissing", ""+(((prix*amount)-moneyP)))
								   .replace("$itemType", yc.getString("item.name."+i+".Displayname"))
								   .replace("$amount",amount+""));
					   }
				}   
			   }
				return;
	}

	public boolean sell(ItemStack item,Player p,int slot, String Type){
		YamlConfiguration yc = FileManager.getShop(Type);
			int pasenvente = 0;
			   for(int i = 1; i<53; i++){
				   pasenvente = pasenvente+1;
			        if((yc.getString("item.name." + i + ".type") != null) && 
			                (item.getType() == Material.matchMaterial(yc.getString("item.name." + i + ".type"))) && 
			                (item.getDurability() == (short)yc.getInt("item.name." + i + ".data"))){
			            if (yc.getInt("item.name."+i+".sell") == 0)
			            {
							   p.sendMessage(Msg.SELL_UNAVAILABLE.toString());
			              return false;
			            }
				   			   int prix = yc.getInt("item.name."+i+".sell");
						       p.sendMessage(Msg.SUCCESS_SELL.toString().replace("$moneySpent", (prix*item.getAmount())+"")
						    		   .replace("$amountItem", item.getAmount()+"")
						    		   .replace("$itemType", yc.getString("item.name."+i+".Displayname"))
						    		   .replace("$currentMoney",  Main.getEcon().format(Main.getEcon().getBalance(p.getPlayer()))));
							   pasenvente = 0;
							   if(slot >= 81 && slot <= 89){
								   int slot2 = slot-81;
							       p.getInventory().setItem(slot2, new ItemStack(Material.AIR));
							   }
							   if(slot <= 80 && slot >= 54){
								   int slot2 = slot-45;
							       p.getInventory().setItem(slot2, new ItemStack(Material.AIR));
							   }
							   return true;
			   }
				   if(pasenvente == 52){
					   p.sendMessage(Msg.FAILURE_SELL_NO_ITEM.toString());
					   return false;
				   }
				}
			   return false;
	}
}
