package com.trafalcraft.vendeur;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.trafalcraft.vendeur.fichier.FileManager;
import com.trafalcraft.vendeur.util.Msg;
import com.trafalcraft.vendeur.util.MyTrait;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.yaml.snakeyaml.error.YAMLException;

public class Main extends JavaPlugin{
	
	  private static Economy econ = null;
	  private static boolean economy = true;
	  private static JavaPlugin plugin;
		
	public void onEnable(){
		
		plugin = this;
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveConfig();
		plugin.reloadConfig();
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		
		CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(MyTrait.class).withName("tr-seller"));

		File d = new File(plugin.getDataFolder().getPath()+"//shops");
		if(!d.exists()){
			d.mkdir();
		}
		try{
			Msg.load();
		}catch(YAMLException e){
			this.getLogger().warning("An error as occured in the config.yml please fix it!");
			e.printStackTrace();
		}
		
		FileManager.loadAllFile();
		
		if (economy) {
			if (!setupEconomy()) {
				getLogger().severe(String.format("[%s] - No Economy (Vault) dependency found! Disabling Economy.", getDescription().getName()));
				economy = false;
			}
		}
	}


	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[]args){
		//TODO Add help
		Player p = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("vendeur")){
			if(args.length == 0 || args.length > 2){
		          return true;
			}
			if(p.hasPermission("vendeur.admin")||p.isOp()){
				if(args[0].equalsIgnoreCase("help")){
					Msg.sendHelp(p);
				}else if (args[0].equalsIgnoreCase("reload")) {
	      		    plugin.reloadConfig();
	      		    Msg.load();
					p.sendMessage("§areload success");
		              return true;
	          }/*else if(args[0].equalsIgnoreCase("spawn")){
	  		    plugin.reloadConfig();
	  			spawnMOB(p, args[1]);
		        return true;
	          }*/else if(args[0].equalsIgnoreCase("addType")){
	        	  FileManager.addFile(plugin.getDataFolder(), args[1]);
	        	  sender.sendMessage("The file "+args[1]+" was created");//Le fichier "+args[1]+" a bien été crée
	  		  }else if(args[0].equalsIgnoreCase("removeType")){
	        	  FileManager.removeFile(args[1]);
	        	  sender.sendMessage("The file "+args[1]+" was removed");//a bien été supprimé
	  		  }else if(args[0].equalsIgnoreCase("typeList")){
					String msg = "Differents types of NPCs are : ";//Les differents types de npc sont
					for(String yc : FileManager.getAllName()){
						msg+=yc+", ";
					}
					msg = msg.substring(1, msg.length()-2); 
					sender.sendMessage(ChatColor.GOLD+ msg);
	  		  }else if(args[0].equalsIgnoreCase("getMaterial")){
	        	  p.sendMessage(p.getInventory().getItemInMainHand().getType()+">1");
	        	  p.sendMessage(p.getInventory().getItemInMainHand().getDurability()+">2");
	        	  p.sendMessage(p.getInventory().getItemInMainHand().getEnchantments()+">3");
	        	  //p.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.KNOCKBACK, 20);
	          }else{
	        	  Msg.sendHelp(p);
	          }
	        	  /*else if(args[0].equalsIgnoreCase("testBlock")){
	        	  Block b = Bukkit.getWorld("world").getBlockAt(p.getLocation());
	              b.setType(Material.STANDING_BANNER);
	              Banner banner = (Banner)b.getState();
	              org.bukkit.material.Banner banner2 = (org.bukkit.material.Banner) banner.getData();
	              banner2.setFacingDirection(BlockFace.NORTH);
	              banner.setBaseColor(DyeColor.RED);
	              banner.update();
	          }*/
			}else{
				p.sendMessage(Msg.PREFIX.toString()+Msg.NO_PERMISSIONS);
			}
		}
		return false;
	}
	
	
	public void spawnMOB(Player p, String Type){
		Villager pnj = (Villager) p.getLocation().getWorld().spawn(p.getLocation(), Villager.class);
				
		pnj.setCustomName("§r§4"+Msg.SELLER_NAME+"->"+Type);
		pnj.setCustomNameVisible(true);
		pnj.setCanPickupItems(true);
		pnj.setAdult();
		pnj.setAgeLock(true);
		pnj.setMetadata("vendeur", new FixedMetadataValue(this, pnj));
		pnj.setAI(false);

	}
	
	private boolean setupEconomy() {
		//charge l'économie.
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
		//Chargement de l'économie terminé.
	}
  
	public static JavaPlugin getPlugin(){
		return plugin;
	}
	public static Economy getEcon(){
		return econ;
	}

}
