package exercises.x16.bybruce;

import exercises.x5.CountingMapData;

import java.util.*;
import static java.lang.System.*;

// Does not support null value as key!
class SlowMap2<K,V> extends AbstractMap<K,V> {
    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();
    @Override public V put(K key, V value) {
        if(key == null)
            throw new NullPointerException();
        V oldValue = get(key); // The old value or null
        if(!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else
            values.set(keys.indexOf(key), value);
        return oldValue;
    }
    @Override public V get(Object key) {
        if(key == null)
            throw new NullPointerException();
        if(!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }
    private EntrySet entrySet = new EntrySet();
    @Override public Set<Map.Entry<K,V>> entrySet() {
        return entrySet;
    }
    // Uses the 'Flyweight' pattern
    private class EntrySet
            extends AbstractSet<Map.Entry<K,V>> {
        @Override public Iterator<Map.Entry<K,V>> iterator() {
            return new Iterator<Map.Entry<K,V>>() {
                private int index = -1;
                boolean canRemove;
                public boolean hasNext() {
                    return index < keys.size() - 1;
                }
                public Map.Entry<K,V> next() {
                    canRemove = true;
                    ++index;
                    return new MapEntry<K,V>(
                            keys.get(index), values.get(index));
                }
                public void remove() {
                    if(!canRemove)
                        throw new IllegalStateException();
                    canRemove = false;
                    keys.remove(index);
                    values.remove(index--);
                }
            };
        }
        @SuppressWarnings("unchecked")
        @Override public boolean contains(Object o) {
            if(o instanceof MapEntry) {
                MapEntry<K,V> e = (MapEntry<K,V>)o;
                K key = e.getKey();
                if(keys.contains(key))
                    return e.equals(new MapEntry<K,V>(key, get(key)));
            }
            return false;
        }
        @SuppressWarnings("unchecked")
        @Override public boolean remove(Object o) {
            if(contains(o)) {
                MapEntry<K,V> e = (MapEntry<K,V>)o;
                K key = e.getKey();
                V val = e.getValue();
                keys.remove(key);
                values.remove(val);
                return true;
            }
            return false;
        }
        @Override public int size() { return keys.size(); }
        @Override public void clear() {
            keys.clear();
            values.clear();
        }
    }
}

class E16_SlowMapTest {
    public static void printKeys(Map<Integer,String> map) {
        out.println("Size = " + map.size() + ", ");
        out.println("Keys: ");
        out.println(map.keySet()); // Produce a Set of the keys
    }
    public static void test(Map<Integer,String> map) {
        out.println(map.getClass().getSimpleName());
        map.putAll(new CountingMapData(25));
        // Map has 'Set' behavior for keys:
        map.putAll(new CountingMapData(25));
        printKeys(map);
        // Producing a Collection of the values:
        out.println("Values: ");
        out.println(map.values());
        out.println(map);
        out.println("map.containsKey(11): " + map.containsKey(11));
        out.println("map.get(11): " + map.get(11));
        out.println("map.containsValue(\"F0\"): "
                + map.containsValue("F0"));
        Integer key = map.keySet().iterator().next();
        out.println("First key in map: " + key);
        map.remove(key);
        printKeys(map);
        map.clear();
        out.println("map.isEmpty(): " + map.isEmpty());
        map.putAll(new CountingMapData(25));
        // Operations on the Set change the Map:
        map.keySet().removeAll(map.keySet());
        out.println("map.isEmpty(): " + map.isEmpty());
    }
    public static void main(String[] args) {
        System.out.println("Testing SlowMap2");
        test(new SlowMap2<Integer,String>());
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
