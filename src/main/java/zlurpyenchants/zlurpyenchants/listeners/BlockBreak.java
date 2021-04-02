package zlurpyenchants.zlurpyenchants.listeners;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import zlurpyenchants.zlurpyenchants.ZlurpyEnchants;
import zlurpyenchants.zlurpyenchants.objects.Enchantment;
import zlurpyenchants.zlurpyenchants.utils.EnchantsUtil;
import zlurpyenchants.zlurpyenchants.utils.RegionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockBreak implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        ItemStack itemStack = event.getPlayer().getItemInHand();
        NBTItem nbtItem = new NBTItem(itemStack);
        EnchantsUtil.addEnchantLevel(event.getPlayer(), nbtItem, 1, "coolCommand");
        EnchantsUtil.addEnchantLevel(event.getPlayer(), nbtItem, 1, "coolEffect");
        EnchantsUtil.addEnchantLevel(event.getPlayer(), nbtItem, 1, "coolRadius");
        runEnchantments(nbtItem, event.getPlayer(), event.getBlock());
    }

    public void runEnchantments(NBTItem item, Player player, Block block) {
        for (String e : item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getKeys()) {
            Enchantment enchantment = ZlurpyEnchants.getInstance().getLoadedEnchants().get(e);
            int level = item.getOrCreateCompound(ZlurpyEnchants.name + "Enchants").getInteger(e);
            switch (enchantment.getType()) {
                case "COMMAND":
                    if (RegionUtil.checkRegion(block, e)) {
                        if (random().intValue() < getEnchantChance(enchantment, level)) {
                            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), enchantment.getCommands().next().replace("%player%", player.getName()));
                        }
                    }
                    break;
                case "EFFECT":
                    if (RegionUtil.checkRegion(block, e)) {
                        if (random().intValue() < getEnchantChance(enchantment, level)) {
                            player.addPotionEffect((new PotionEffect(PotionEffectType.getByName(enchantment.getEffectType()), 5, level)));
                        }
                    }
                    break;
                case "RADIUS":
                    if (RegionUtil.checkRegion(block, e)) {
                        if (random().intValue() < getEnchantChance(enchantment, level)) {
                            for (Block solidBlocks : this.getNearbyBlocks(block.getLocation(),
                                    ZlurpyEnchants.getInstance().getConfig().getInt("Enchants." + e + ".radius.x"),
                                    ZlurpyEnchants.getInstance().getConfig().getInt("Enchants." + e + ".radius.y"),
                                    ZlurpyEnchants.getInstance().getConfig().getInt("Enchants." + e + ".radius.z"))) {
                                if (RegionUtil.checkRegion(solidBlocks, e)) {
                                    player.getInventory().addItem(new ItemStack(solidBlocks.getType()));
                                    solidBlocks.setType(Material.AIR);
                                }
                            }
                        }
                    }
                    break;
            }

        }
    }

    public Double random() {
        Random random = new Random();
        return random.nextDouble() * 100.0;
    }

    private double getEnchantChance(Enchantment enchantment, int level) {
        double chance;
        double baseChance = chance = enchantment.getBaseChance();
        double chanceIncrement = enchantment.getChanceIncrease();
        for (int i = 0; i < level; ++i) {
            chance += chanceIncrement;
        }
        return chance;
    }

    private List<Block> getNearbyBlocks(Location location, int radiusX, int radiusY, int radiusZ) {
        List<Block> blocks = new ArrayList<Block>();
        for (int x = location.getBlockX() - radiusX; x <= location.getBlockX() + radiusX; ++x) {
            for (int y = location.getBlockY() - radiusY; y <= location.getBlockY() + radiusY; ++y) {
                for (int z = location.getBlockZ() - radiusZ; z <= location.getBlockZ() + radiusZ; ++z) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}