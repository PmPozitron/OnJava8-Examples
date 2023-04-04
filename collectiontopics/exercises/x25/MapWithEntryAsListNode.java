package exercises.x25;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

public class MapWithEntryAsListNode<K, V> extends AbstractMap<K, V> {
    MapEntry<K,V>[] buckets;

    int numberOfBuckets = 8;

    public MapWithEntryAsListNode() {
        buckets = new MapEntry[numberOfBuckets];
    }

    public MapWithEntryAsListNode(int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
        new MapWithEntryAsListNode<>();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<>();
        Arrays.stream(buckets)
            .filter(Objects::nonNull)
            .forEach(bucket -> {
                MapEntry<K, V> entry = bucket;
                while (entry != null) {
                    result.add(entry);
                    entry = entry.getNext();
                }
            });

        return result;
    }

    @Override
    public int size() {
        return entrySet().size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsValue(Object value) {
        return entrySet().stream().filter(entry -> entry.getValue().equals(value)).findAny().isPresent();
    }

    @Override
    public boolean containsKey(Object key) {
        int index = key.hashCode() % numberOfBuckets;
        MapEntry<K,V> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        while(bucket != null) {
            if (bucket.getKey().equals(key)) {
                return true;
            }
            bucket = bucket.getNext();
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = key.hashCode() % numberOfBuckets;
        MapEntry<K,V> bucket = buckets[index];
        if (bucket == null) {
            throw new NoSuchElementException();
        }
        while(bucket != null) {
            if (bucket.getKey().equals(key)) {
                return bucket.getValue();
            }
            bucket = bucket.getNext();
        }
        throw new NoSuchElementException();
    }

    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % numberOfBuckets;
        if (buckets[index] == null) {
            buckets[index] = new MapEntry<>(key, value);
            return value;
        }
        MapEntry<K,V> previous = null;
        MapEntry<K,V> bucket = buckets[index];

        while(bucket != null) {
            if (bucket.getKey().equals(key)) {
                V result = bucket.getValue();
                bucket.setValue(value);
                return result;
            }
            previous = bucket;
            bucket = bucket.getNext();
        }
        previous.setNext(new MapEntry<>(key, value));
        return value;
    }

    @Override
    public V remove(Object key) {
        int index = key.hashCode() % numberOfBuckets;
        MapEntry<K,V> bucket = buckets[index];
        if (bucket == null) {
            throw new NoSuchElementException();
        }
        MapEntry<K,V> previous = null;

        while (bucket != null) {
            if (bucket.getKey().equals(key)) {
                if (previous == null) {
                    V result = bucket.getValue();
                    buckets[index] = null;
                    return result;
                } else {
                    V result = bucket.getValue();
                    previous.setNext(bucket.getNext());
                    return result;
                }
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void clear() {
        buckets = new MapEntry[numberOfBuckets];
    }

    public static void main(String[] args) {
        MapWithEntryAsListNode<Integer, String> map = new MapWithEntryAsListNode();
        IntStream.range(0, 16).forEach(i -> map.put(i, String.valueOf(i) + '-' + String.valueOf(i)));
        System.out.println(map);
        System.out.println(map.get(5));
        System.out.println(map.put(5, "updated"));
        System.out.println(map.get(5));
        System.out.println(map.remove(5));
        System.out.println(map);
        System.out.println(map.containsKey(4));
        System.out.println(map.containsValue("4-4"));
        System.out.println(map.containsKey(5));
        System.out.println(map.size());

    }
}

class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    private MapEntry<K, V> next;

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

    public MapEntry<K, V> getNext() {
        return next;
    }

    public void setNext(MapEntry<K, V> next) {
        this.next = next;
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
                ((MapEntry<K, V>)rval).getValue()) &&
            Objects.equals(next, ((MapEntry<?, ?>) rval).next);
    }
    @Override public String toString() {
        return key + "=" + value;
    }
}
