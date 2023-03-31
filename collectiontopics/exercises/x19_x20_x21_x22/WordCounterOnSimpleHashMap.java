package exercises.x19_x20_x21_x22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

public class WordCounterOnSimpleHashMap {
    public static void main(String[] args) throws IOException {
        testRemoveAndClear();
    }

    private static void countWords() throws IOException {
        SimpleHashMap<String, Integer> map = new SimpleHashMap<>();
        Files.readAllLines(Paths.get("collections/SetOperations.java"))
            .stream()
            .map(string -> string.split("\\W+"))
            .flatMap(array -> Arrays.stream(array))
            .forEach(word -> {
                map.merge(word, 1, (anOld, aNew) -> anOld + aNew);
            });
        System.out.println(map);
    }

    private static void testRemoveAndClear() {
        int size = 50;
        SimpleHashMap<Integer, String> map = new SimpleHashMap<>();
        IntStream.range(0, size).forEach(i -> map.put(i, UUID.randomUUID().toString()));
        System.out.println(map);
        int index = (int)(Math.random() * size);
        System.out.println("index " + index);
        map.remove(index);
        System.out.println("===");
        System.out.println(map);
        map.put(index, UUID.randomUUID().toString());
        System.out.println("===");
        System.out.println(map);
        map.remove(index);
        System.out.println(map);
        System.out.println("===");
        map.clear();
        System.out.println(map);
    }
}

class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    // Choose a prime number for the hash table
    // size, to achieve a uniform distribution:
    static final int SIZE = 997;

    // You can't have a physical array of generics,
    // but you can upcast to one:
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            buckets[index] = new LinkedList<>();
        else if (! buckets[index].stream()
            .filter(item -> item.getKey().equals(key))
            .findAny()
            .isPresent()){
            System.out.printf("key's '%s' hash '%d' collided with '%d' elements in this map\n", key, index, buckets[index].size());
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        MapEntry<K, V> pair = new MapEntry<>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K, V>> it = bucket.listIterator();
        while (it.hasNext()) {
            MapEntry<K, V> iPair = it.next();
            if (iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                it.set(pair); // Replace old with new
                found = true;
                break;
            }
        }
        if (!found)
            buckets[index].add(pair);
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            return null;
        int counter = 0;
        for (MapEntry<K, V> iPair : buckets[index]) {
            if (iPair.getKey().equals(key)) {
                System.out.printf("took '%d' steps to find item by key '%s'\n", counter, key);
                return iPair.getValue();
            }
            counter++;
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null)
                continue;
            for (MapEntry<K, V> mpair : bucket)
                set.add(mpair);
        }
        return set;
    }

    @Override
    public V remove(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            return null;

        for (Iterator<MapEntry<K,V>> iterator = buckets[index].iterator(); iterator.hasNext();) {
            MapEntry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void clear() {
        buckets = new LinkedList[SIZE];
    }
}
class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;
    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }
    @Override public K getKey() { return key; }
    @Override public V getValue() { return value; }
    @Override public V setValue(V v) {
        V result = value;
        value = v;
        return result;
    }
    @Override public int hashCode() {
        return Objects.hash(key, value);
    }
    @SuppressWarnings("unchecked")
    @Override public boolean equals(Object rval) {
        return rval instanceof MapEntry &&
            Objects.equals(key,
                ((MapEntry<K, V>)rval).getKey()) &&
            Objects.equals(value,
                ((MapEntry<K, V>)rval).getValue());
    }
    @Override public String toString() {
        return key + "=" + value;
    }
}


