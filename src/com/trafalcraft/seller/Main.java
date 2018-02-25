package com.trafalcraft.seller;

import com.trafalcraft.seller.file.FileManager;
import com.trafalcraft.seller.setup.SetupInventoryListener;
import com.trafalcraft.seller.setup.SetupInventoryManage;
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

public class Main extends JavaPlugin {

        private static Economy econ = null;
        private static boolean economy = true;
        private static JavaPlugin plugin;

        public static JavaPlugin getPlugin() {
                return plugin;
        }

        public static Economy getEcon() {
                return econ;
        }

        public void onEnable() {

                plugin = this;
            plugin.saveDefaultConfig();
                plugin.getConfig().options().copyDefaults(true);
                plugin.saveConfig();
                plugin.reloadConfig();

                if (getConfig().getString("version") == null || !getConfig().getString("version").equals("0.2")) {
                        File f = new File(getPlugin().getDataFolder().getPath() + "//config.yml");
                        File newFile = new File(f.getPath() + "-" + getConfig().getString("version") + ".old");
                        f.renameTo(newFile);
                        plugin.getConfig().options().copyDefaults(true);
                        plugin.saveDefaultConfig();
                        plugin.reloadConfig();
                }

                Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
                Bukkit.getServer().getPluginManager().registerEvents(new SetupInventoryListener(), this);

                CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(MyTrait.class).withName("tr-seller"));

                File d = new File(plugin.getDataFolder().getPath() + "//shops");
                if (!d.exists()) {
                        d.mkdir();
                }
                try {
                        Msg.load();
                } catch (YAMLException e) {
                        this.getLogger().warning("An error as occurred in the config.yml please fix it!");
                        e.printStackTrace();
                }

                FileManager.loadAllFile();

                if (economy) {
                        if (!setupEconomy()) {
                                getLogger().severe(String
                                        .format("[%s] - No Economy (Vault) dependency found! Disabling Plugin.",
                                                getDescription().getName()));
                                economy = false;
                                getServer().getPluginManager().disablePlugin(this);
                        }
                }
        }

        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
                Player p = (Player) sender;
                if (cmd.getName().equalsIgnoreCase("seller")) {
                        if (args.length == 0 || args.length > 2) {
                                Msg.sendHelp(p);
                                return true;
                        }
                        if (p.hasPermission("seller.admin") || p.isOp()) {
                                if (args[0].equalsIgnoreCase("help")) {
                                        Msg.sendHelp(p);
                                } else if (args[0].equalsIgnoreCase("reload")) {
                                        plugin.reloadConfig();
                                        Msg.load();
                                        FileManager.clear();
                                        FileManager.loadAllFile();
                                    p.sendMessage(Msg.RELOAD.toString());
                                        return true;
                                } else if (args[0].equalsIgnoreCase("updateAllShops")) {
                                        FileManager.clear();
                                        FileManager.loadAllFile();
                                    p.sendMessage(Msg.COMMAND_UPDATE_ALL_SHOPS.toString());
                                        return true;
                                } else if (args[0].equalsIgnoreCase("manageShop")) {
                                        if (args.length > 1) {
                                                SetupInventoryManage.openManageInventory(p, args[1], 1);
                                        } else {
                                                sender.sendMessage("Usage: /seller manageShop <type>");
                                        }
                                } else if (args[0].equalsIgnoreCase("removeShop")) {
                                        if (args.length > 1) {
                                                FileManager.removeFile(args[1]);
                                            sender.sendMessage(Msg.COMMAND_REMOVE_SHOP.toString()
                                                    .replace("$fileName", args[1]));
                                        } else {
                                                sender.sendMessage("Usage: /seller removeShop <type>");
                                        }
                                } else if (args[0].equalsIgnoreCase("shopList")) {
                                    StringBuilder msg = new StringBuilder();
                                        for (String yc : FileManager.getAllName()) {
                                                msg.append(yc).append(", ");
                                        }
                                        msg = new StringBuilder(msg.substring(0, msg.length() - 2));
                                    msg = new StringBuilder(Msg.COMMAND_SHOP_LIST.toString()
                                            .replace("$shopList", msg));
                                        sender.sendMessage(ChatColor.GOLD + msg.toString());
                                } else {
                                        Msg.sendHelp(p);
                                }
                        } else {
                                p.sendMessage(Msg.PREFIX.toString() + Msg.NO_PERMISSIONS);
                        }
                }
                return false;
        }

        private boolean setupEconomy() {
                //load economy.
                if (getServer().getPluginManager().getPlugin("Vault") == null) {
                        return false;
                }
                RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager()
                        .getRegistration(Economy.class);
                if (rsp == null) {
                        return false;
                }
                econ = rsp.getProvider();
                return econ != null;
        }

}
