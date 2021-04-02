package zlurpyenchants.zlurpyenchants.objects;
import zlurpyenchants.zlurpyenchants.utils.RandomCollection;

public class Enchantment {

    private double baseChance;
    private double basePrice;
    private double priceIncrease;
    private double chanceIncrease;
    private String type;
    private int maxLevel;
    private String name;
    private String lore;
    private RandomCollection<String> commands;
    private String effectType;

    public Enchantment(double baseChance, RandomCollection<String> commands, double basePrice, double priceIncrease, double chanceIncrease, String type, int maxLevel, String name, String lore, String effectType) {
        this.baseChance = baseChance;
        this.basePrice = basePrice;
        this.priceIncrease = priceIncrease;
        this.chanceIncrease = chanceIncrease;
        this.type = type;
        this.maxLevel = maxLevel;
        this.name = name;
        this.lore = lore;
        this.commands = commands;
        this.effectType = effectType;
    }

    public double getBaseChance() {
        return baseChance;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getPriceIncrease() {
        return priceIncrease;
    }

    public double getChanceIncrease() {
        return chanceIncrease;
    }

    public String getType() {
        return type;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public RandomCollection<String> getCommands() {
        return commands;
    }

    public String getEffectType() {
        return effectType;
    }
}