package com.trafalcraft.seller.file;

import com.google.common.collect.Maps;
import com.trafalcraft.seller.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class FileManager {

        private final static Map<String, YamlConfiguration> shopList = Maps.newHashMap();

        public static void updateFile(Inventory inv, String name) throws IOException {
                File file = new File(Main.getPlugin().getDataFolder().getPath() + "//shops//", name + ".yml");
                if (!shopList.containsKey(name)) {
                        YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
                        file.createNewFile();
                        yc.set("version", "1.0");
                        yc = invToYaml(yc, inv);
                        yc.save(file);
                        shopList.put(name, yc);
                } else {
                        YamlConfiguration yc = shopList.get(name);
                        yc = invToYaml(yc, inv);
                        yc.save(file);
                }
        }

        public static void loadAllFile() {
                File f2 = new File(Main.getPlugin().getDataFolder() + "//shops//");
                for (String sF : Objects.requireNonNull(f2.list())) {
                        if (sF.endsWith(".yml")) {
                                File f = new File(Main.getPlugin().getDataFolder() + "//shops//", sF);

                                YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
                                if (yamlConfiguration.getString("version") == null || !yamlConfiguration
                                        .getString("version").equals("1.0")) {
                                        File newFile = new File(
                                                f.getPath() + "-" + yamlConfiguration.getString("version") + ".old");
                                        f.renameTo(newFile);
                                        Main.getPlugin().getLogger().warning(
                                                "You shop " + sF + " file is not up to date please update it");
                                } else {
                                        shopList.put(f.getName().replace(".yml", ""), yamlConfiguration);
                                        Bukkit.getLogger().info("The shopType " + sF + " has been loaded");
                                }
                        }
                }
        }

        public static boolean contains(String name) {
                return shopList.containsKey(name);
        }

        public static void removeFile(String name) {
                if (shopList.containsKey(name)) {
                        File file = new File(Main.getPlugin().getDataFolder().getPath() + "//shops//", name + ".yml");
                        file.delete();
                        shopList.remove(name);

                }
        }

        public static Map<String, YamlConfiguration> getListFile() {
                return shopList;
        }

        public static Collection<YamlConfiguration> getAllFile() {
                return shopList.values();
        }

        public static Collection<String> getAllName() {
                return shopList.keySet();
        }

        public static YamlConfiguration getShop(String name) {
                return shopList.get(name);
        }

        public static void clear() {
                shopList.clear();
        }

        private static YamlConfiguration invToYaml(YamlConfiguration yc, Inventory inv) {
                String[] invTitleSplit = inv.getTitle().split("§r§4>§r§4");
                int pageNumber = Integer.parseInt(invTitleSplit[invTitleSplit.length - 1]);
                for (int i = 45; i < 54; i++) {
                        inv.setItem(i, new ItemStack(Material.AIR));
                }
                for (int i = 0; i < 45; i++) {
                        ItemStack item = inv.getItem(i);
                        if (item != null) {
                                if (item.getAmount() > 1) {
                                        item.setAmount(1);
                                }
                                yc.set("page." + pageNumber + ".item." + i + ".itemStack", item);
                                if (yc.get("page." + pageNumber + ".item." + i + ".buy") == null) {
                                        yc.set("page." + pageNumber + ".item." + i + ".buy", "UNDEFINED");
                                }
                                if (yc.get("page." + pageNumber + ".item." + i + ".sell") == null) {
                                        yc.set("page." + pageNumber + ".item." + i + ".sell", "UNDEFINED");
                                }
                        } else {
                                yc.set("page." + pageNumber + ".item." + i, null);
                        }
                }
                return yc;
        }
}
