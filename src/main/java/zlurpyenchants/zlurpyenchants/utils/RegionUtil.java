package zlurpyenchants.zlurpyenchants.utils;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.block.Block;
import zlurpymining.zlurpymining.ZlurpyMining;

public class RegionUtil {
    static RegionContainer container;

    public static boolean checkRegion(Block block, String name) {
        container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        boolean canBreak = false;
        Location loc = BukkitAdapter.adapt(block.getLocation());
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        for (ProtectedRegion protectedRegion : set) {
            if (protectedRegion.getId().contains(ZlurpyMining.getInstance().getConfig().getString("Enchants." + name + ".region"))) {
                canBreak = true;
            }

        }
        return canBreak;
    }
}