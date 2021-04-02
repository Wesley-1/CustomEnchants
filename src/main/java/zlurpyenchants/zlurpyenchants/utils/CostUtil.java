package zlurpyenchants.zlurpyenchants.utils;

import zlurpyenchants.zlurpyenchants.objects.Enchantment;

public class CostUtil {
    // This is unused however u can use it if needed.
    public double getCost(Enchantment enchant, final int level) {
        double price = enchant.getBasePrice();
        final double priceIncrement = enchant.getPriceIncrease();
        for (int i = 0; i < level; ++i) {
            price += price * (priceIncrement / 100.0);
        }
        return price;
    }
}
