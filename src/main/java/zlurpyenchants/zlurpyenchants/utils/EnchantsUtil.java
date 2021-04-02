package zlurpyenchants.zlurpyenchants.utils;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import zlurpyenchants.zlurpyenchants.ZlurpyEnchants;

import java.util.ArrayList;

@SuppressWarnings("unused")

public class EnchantsUtil {
    public static void setupCompound(Player player, NBTItem item) {
        item.addCompound(ZlurpyEnchants.name + "Enchants");
        addEnchantLore(player, item);
    }
    public static void setEnchantLevel(Player player, NBTItem item, int amount, String name) {
        item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").setInteger(name, amount);
        addEnchantLore(player, item);
    }
    public static void addEnchantLevel(Player player, NBTItem item, int amount, String name) {
        int level = item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getInteger(name);
        item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").setInteger(name, level + amount);
        addEnchantLore(player, item);
    }
    public static void takeEnchantLevel(Player player, NBTItem item, int amount, String name) {
        int level = item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getInteger(name);
        item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").setInteger(name, level - amount);
        addEnchantLore(player, item);
    }
    public static void clearEnchants(Player player, NBTItem item) {
        item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getKeys().clear();
        addEnchantLore(player, item);
    }
    public static void clearCertainEnchant(Player player, NBTItem item, String name) {
        item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").removeKey(name);
        addEnchantLore(player, item);
    }
    // Clears whole lore and sets only enchantments
    public static void addEnchantLore(Player player, NBTItem item) {
        ItemMeta meta = item.getItem().getItemMeta();
        ArrayList<String> loreList = (ArrayList<String>) meta.getLore();
        loreList.clear();
        for(String string : ZlurpyEnchants.getInstance().getConfig().getStringList("Item.lore")) {
            loreList.add(string);
        }
        for(String name : item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getKeys()) {
            int enchantLevels = item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getInteger(name);
            loreList.add(translateColor(ZlurpyEnchants.getInstance().getConfig().getString("Enchants." + name + ".lore").replace("%level%", String.valueOf(enchantLevels))));
        }
        ItemStack newItem = item.getItem();
        meta.setLore(loreList);
        newItem.setItemMeta(meta);
        player.getInventory().setItemInHand(newItem);
    }
    public static String translateColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
