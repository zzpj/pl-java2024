package pl.zzpj.cleancode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MapValuesIncrementer {

    Map<Integer, Integer> integersMap = new HashMap<Integer, Integer>();

    private int minInt = 0;
    private int maxInt = 0;

    public MapValuesIncrementer(List<Integer> keys) {
        incrementValueForKey(keys);
    }

    public MapValuesIncrementer() {
    }

    public void incrementValueForKey(Integer key) {
        if (this.integersMap.containsKey(key)) {
            this.integersMap.put(key, this.integersMap.get(key) + 1);
        } else {
            this.integersMap.put(key, 1);
        }

        if (key < minInt) {
            this.minInt = key;
        }
        if (key > maxInt) {
            this.maxInt = key;
        }
    }

    public void incrementValueForKey(List<Integer> keys) {
        for (Integer key : keys) {
            this.incrementValueForKey(key);
        }
    }

    public int getValueFromKey(int key) {
        return this.integersMap.getOrDefault(key, 0);
    }

    public double calculateSumOfProductsToSumRatio() {
        double sumOfProducts = 0.0;
        int sum = 0;
        for (Entry<Integer, Integer> entry : this.integersMap.entrySet()) {
            sum += entry.getValue();
            sumOfProducts += entry.getKey() * entry.getValue();
        }
        return sumOfProducts / sum;
    }

    public int getMinInt() {
        return this.minInt;
    }

    public int getMaxInt() {
        return this.maxInt;
    }
}