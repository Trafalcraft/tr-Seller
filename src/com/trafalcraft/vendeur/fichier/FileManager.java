package com.trafalcraft.vendeur.fichier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Maps;
import com.trafalcraft.vendeur.Main;

public class FileManager {
	
	private final static Map<String, YamlConfiguration> shopList = Maps.newHashMap();
	
	public static void addFile(File DataFolder , String name){
		if(!shopList.containsKey(name)){
			File file = new File( DataFolder.getPath()+"//shops//" , name+".yml");
			try {
				file.createNewFile();
				YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
				yc.set("item.name.1.type","DIAMOND");
				yc.set("item.name.1.data",0);
				yc.set("item.name.1.Displayname","Diamond");
				yc.set("item.name.1.buy",200);
				yc.set("item.name.1.sell",150);
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add("");
				yc.set("item.name.1.lore",lore);
				
				yc.set("item.name.2.type","STAINED_CLAY");
				yc.set("item.name.2.data",14);
				yc.set("item.name.2.Displayname","red clay");
				yc.set("item.name.2.buy",60);
				yc.set("item.name.2.sell",25);
//				lore.add("");
//				lore.add("");
				yc.createSection("item.name.2.lore");
				
				yc.set("item.name.3.type","POTION");
				yc.set("item.name.3.data",16452);
				yc.set("item.name.3.Displayname","splash poison potion");
				yc.set("item.name.3.buy",200);
				yc.set("item.name.3.sell",150);
//				lore.add("");
//				lore.add("");
				yc.createSection("item.name.3.lore");
				
				yc.set("item.name.4.type","WOOD");
				yc.set("item.name.4.data",0);
				yc.set("item.name.4.Displayname","plank of wood");
				yc.set("item.name.4.buy",20);
				yc.set("item.name.4.sell",15);
//				lore.add("");
//				lore.add("");
				yc.createSection("item.name.4.lore");
				
				yc.set("item.name.5.type","POTION");
				yc.set("item.name.5.data",0);
				yc.set("item.name.5.Displayname","Water bottle");
				yc.set("item.name.5.buy",2);
				yc.set("item.name.5.sell",1);
//				lore.add("");
//				lore.add("");
				yc.createSection("item.name.5.lore");
				yc.save(file);
				shopList.put(name, yc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadAllFile(){
		File f2 = new File(Main.getPlugin().getDataFolder()+"//shops//");
		for(String sF : f2.list()){
			if(sF.endsWith(".yml")){
				File f = new File(Main.getPlugin().getDataFolder()+"//shops//" , sF);
				shopList.put(f.getName().replace(".yml", ""), YamlConfiguration.loadConfiguration(f));
				Bukkit.getLogger().info("The shopType "+sF+" has been loaded");
			}
		}
	}
	
	public static boolean contains(String name){
		if(shopList.containsKey(name)){
			return true;
		}
		return false;
	}
	
	public static void removeFile(String name){
		if(shopList.containsKey(name)){
			File file = new File( Main.getPlugin().getDataFolder().getPath()+"//shops//" , name+".yml");
			file.delete();
			shopList.remove(name);
			
		}
	}
	
	public static Map<String, YamlConfiguration> getListFile(){
		return shopList;
	}
	
	public static Collection<YamlConfiguration> getAllFile(){
		return shopList.values();
	}
	
	public static Collection<String> getAllName(){
		return shopList.keySet();
	}
	
	public static YamlConfiguration getShop(String name){
			return shopList.get(name);
	}
	
	public static void clear(){
		shopList.clear();
	}
}
