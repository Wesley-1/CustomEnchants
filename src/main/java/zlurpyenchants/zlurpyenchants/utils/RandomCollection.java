package zlurpyenchants.zlurpyenchants.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E>
{
    private final NavigableMap<Double, E> map;
    private final Random random;
    private double total;
    
    public RandomCollection() {
        this(new Random());
    }
    
    public RandomCollection(final Random random) {
        this.map = new TreeMap<Double, E>();
        this.total = 0.0;
        this.random = random;
    }
    
    public RandomCollection<E> add(final double weight, final E result) {
        if (weight <= 0.0) {
            return this;
        }
        this.total += weight;
        this.map.put(this.total, result);
        return this;
    }
    
    public E next() {
        final double value = this.random.nextDouble() * this.total;
        return this.map.higherEntry(value).getValue();
    }
}
