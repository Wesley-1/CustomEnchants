package zlurpyenchants.zlurpyenchants;


import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import zlurpyenchants.zlurpyenchants.listeners.BlockBreak;
import zlurpyenchants.zlurpyenchants.objects.Enchantment;
import zlurpyenchants.zlurpyenchants.utils.RandomCollection;

import java.util.HashMap;
import java.util.Map;

public final class ZlurpyEnchants extends JavaPlugin {
    private static ZlurpyEnchants instance;
    public static String name = "ZlurpyEnchants";
    private Map<String, Enchantment> loadEnchantments = new HashMap<>();


    public ZlurpyEnchants() {
        this.loadEnchantments = new HashMap<>();
    }

    public Map<String, Enchantment> getLoadedEnchants() {
        return loadEnchantments;
    }

    public static ZlurpyEnchants getInstance() {
        return ZlurpyEnchants.instance;
    }



    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        loadListeners();
        loadEnchantments();

    }
    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadConfig() {
        saveDefaultConfig();
    }
    private void loadListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new BlockBreak(), this);
    }
    private void loadEnchantments() {
        for(String keys : getConfig().getConfigurationSection("Enchants").getKeys(false)) {
            String name = keys;
            // Prices
            double basePrice = getConfig().getDouble("Enchants." + name + ".basePrice");
            double priceIncrement = getConfig().getDouble("Enchants." + name + ".priceIncrement");
            // Chances
            double baseChance = getConfig().getDouble("Enchants." + name + ".baseChance");
            double chanceIncrement = getConfig().getDouble("Enchants." + name + ".chanceIncrement");
            // MaxLevels
            int maxLevel = getConfig().getInt("Enchants." + name + ".maxLevel");
            // Effect Type
            String effectType = getConfig().getString("Enchants." + name + ".effect");
            // Command Type
            RandomCollection<String> collection = new RandomCollection<>();
            for(String command : getConfig().getStringList("Enchants." + name + ".commands")) {
                String[] split = command.split(";");
                String chance = split[0];
                int c = Integer.parseInt(split[1]);
                collection.add(c, chance);
            }
            // Types
            String type = getConfig().getString("Enchants." + name + ".type");
            // Lores
            String lore = getConfig().getString("Enchants." + name + ".lore");
            // Object
            Enchantment enchantment = new Enchantment(baseChance, collection, basePrice, priceIncrement, chanceIncrement, type, maxLevel, keys, lore, effectType);
            // Loads Them
            getLoadedEnchants().put(keys, enchantment);
        }
    }
    public static String translate(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}