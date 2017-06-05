import java.util.ArrayList;
import java.util.List;

public class HashMap<K extends Comparable<K>, V extends Comparable<V>> {

    private class Entry {
        private K key;
        private V value;
        private Entry (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<List<Entry>> table;
    private int numEntries;
    private int numBuckets;

    // Input: An object's hash code. Every object has a default Object.hashCode().
    // Output: The hash for internal use. Simple modulus on number of buckets.
    private int hashFunc(int hashCode) {
        return Math.abs(hashCode % numBuckets);
    }

    public HashMap(int size) {
        table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(new ArrayList<>());
        }
        numEntries = 0;
        numBuckets = size;
    }

    public V get(K key) {
        int idx = hashFunc(key.hashCode());
        List<Entry> chain = table.get(idx);
        if (chain != null) {
            for (Entry entry : chain) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    public void put(K key, V value) {
        // Check load factor, max 1.
        if ((numEntries + 1) / numBuckets >= 1) {
            numBuckets *= 2;
            HashMap<K, V> hashMap = new HashMap<>(numBuckets);
            for (List<Entry> chain : table) {
                for (Entry entry : chain) {
                    hashMap.put(entry.key, entry.value);
                }
            }
            table = hashMap.table;
        }
        Entry entry = new Entry(key, value);
        int idx = hashFunc(key.hashCode());
        List<Entry> chain = table.get(idx);
        if (chain == null) {
            chain = new ArrayList<>();
            table.set(idx, chain);
        }
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key.equals(key)) {
                chain.set(i, entry);
                return;
            }
        }
        chain.add(entry);
        numEntries++;
    }

    public void remove(K key) {
        int idx = hashFunc(key.hashCode());
        List<Entry> chain = table.get(idx);
        if (chain != null) {
            for (int i = 0; i < chain.size(); i++) {
                if (chain.get(i).key.equals(key)) {
                    chain.remove(i);
                    numEntries--;
                    return;
                }
            }
        }
    }
}
