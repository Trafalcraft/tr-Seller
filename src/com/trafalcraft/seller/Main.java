package com.trafalcraft.seller;


import com.trafalcraft.seller.fichier.FileManager;
import com.trafalcraft.seller.util.Msg;
import com.trafalcraft.seller.util.MyTrait;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;

public class Main extends JavaPlugin{

    private static Economy econ = null;
    private static boolean economy = true;
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public static Economy getEcon(){
        return econ;
    }

    public void onEnable(){

        plugin = this;
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        plugin.reloadConfig();

        if(getConfig().getString("version") == null || !getConfig().getString("version").equals("0.1")){
            File f = new File(getPlugin().getDataFolder().getPath()+"//config.yml");
            File newFile = new File(f.getPath()+"-"+getConfig().getString("version")+".old");
            f.renameTo(newFile);
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
        }

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(MyTrait.class).withName("tr-seller"));

        File d = new File(plugin.getDataFolder().getPath()+"//shops");
        if(!d.exists()){
            d.mkdir();
        }
        try{
            Msg.load();
        }catch(YAMLException e){
            this.getLogger().warning("An error as occurred in the config.yml please fix it!");
            e.printStackTrace();
        }

        FileManager.loadAllFile();

        if (economy) {
            if (!setupEconomy()) {
                getLogger().severe(String.format("[%s] - No Economy (Vault) dependency found! Disabling Plugin.", getDescription().getName()));
                economy = false;
                getServer().getPluginManager().disablePlugin(this);
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[]args){
        Player p = (Player) sender;
        if(cmd.getName().equalsIgnoreCase("seller")){
            if(args.length == 0 || args.length > 2){
                Msg.sendHelp(p);
                return true;
            }
            if(p.hasPermission("seller.admin")||p.isOp()){
                if(args[0].equalsIgnoreCase("help")){
                    Msg.sendHelp(p);
                }else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    Msg.load();
                    p.sendMessage("§areload success");
                    return true;
                }else if(args[0].equalsIgnoreCase("addType")){
                    FileManager.addFile(plugin.getDataFolder(), args[1]);
                    sender.sendMessage("The file "+args[1]+" was created");//Le fichier "+args[1]+" a bien été crée
                }else if(args[0].equalsIgnoreCase("removeType")){
                    FileManager.removeFile(args[1]);
                    sender.sendMessage("The file "+args[1]+" was removed");//a bien été supprimé
                }else if(args[0].equalsIgnoreCase("typeList")){
                    StringBuilder msg = new StringBuilder("Differents types of NPCs are : ");//Les differents types de npc sont
                    for(String yc : FileManager.getAllName()){
                        msg.append(yc).append(", ");
                    }
                    msg = new StringBuilder(msg.substring(0, msg.length() - 2));
                    sender.sendMessage(ChatColor.GOLD+ msg.toString());
                }else if(args[0].equalsIgnoreCase("getMaterial")){
                    p.sendMessage("Type:"+p.getInventory().getItemInMainHand().getType());
                    p.sendMessage("Durability"+p.getInventory().getItemInMainHand().getDurability());
                    p.sendMessage("Enchantments:"+p.getInventory().getItemInMainHand().getEnchantments());
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

    private boolean setupEconomy() {
        //load economy.
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

}
