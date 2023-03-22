package exercises.x16_x17;

import exercises.x5.CountingMapData;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;


/*
a note on 17th exercise from annotated solution guide

Exercise 17
The SlowMap2 class already uses all of Map (which you can test by invoking
each method listed in Map on SlowMap2). The two most frequently
overridden methods of AbstractMap (besides those already overridden in
SlowMap2) are containsKey( ) and remove( ). Their default use requires
linear time in the size of the map though, which is awkward. However,
overriding them in SlowMap2 gains nothing. You really can’t speed up the
code, so extra development is unnecessary.
Remember, sometimes the abstract base version of the container you are
creating already uses the methods you need (not that you create your own
containers all that often).
 */
public class SlowMapTested<K, V> extends AbstractMap<K, V> {
    private ArrayList<K> keys = new ArrayList<>();
    private ArrayList<V> values = new ArrayList<>();

    private Set<Entry<K, V>> entrySet = new EntrySet();
    @Override
    public V put(K key, V value) {
        V oldValue = get(key); // The old value or null
        if(!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else
            values.set(keys.indexOf(key), value);
        entrySet.add(new MapEntry<>(key, value));
        return oldValue;
    }
    @Override
    public V get(Object key) { // key: type Object, not K
        if(!keys.contains(key))
            return null;
        return values.get(keys.indexOf(key));
    }
    @Override
    public Set<Entry<K, V>> entrySet() {
        return entrySet;
    }

    @Override
    public V remove(Object key) {
        V element = values.remove(keys.indexOf(key));
        if (element != null) {
            keys.remove(key);
            entrySet.remove(new MapEntry<>(key, element));
        }

        return element;
    }

    @Override
    public void clear() {
        keys.clear();
        values.clear();
        entrySet.clear();
    }

    private class EntrySet extends AbstractSet<Entry<K, V>> {
        Set<Map.Entry<K, V>> set;

        public EntrySet() {
            set= new HashSet<>();
            Iterator<K> ki = keys.iterator();
            Iterator<V> vi = values.iterator();
            while(ki.hasNext())
                set.add(new MapEntry<>(ki.next(), vi.next()));
        }

        @Override
        public boolean add(Entry<K, V> kvEntry) {
            return set.add(kvEntry);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof MapEntry) {
                MapEntry<?, ?> entry = (MapEntry)o;
                return set.remove(entry);
            }
            throw new IllegalArgumentException();
        }

        @Override
        public Iterator<Entry<K, V>> iterator() {
            return set.iterator();
        }

        @Override
        public int size() {
            return set.size();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            AtomicBoolean mapWasUpdated = new AtomicBoolean(false);
            c.forEach(key -> {
                mapWasUpdated.set(SlowMapTested.this.remove(key) != null);
            });
            return mapWasUpdated.get();
        }
    }

    public static void main(String[] args) {
        SlowMapTested<String,String> m= new SlowMapTested<>();
        m.putAll(onjava.Countries.capitals(8));
        m.forEach((k, v) ->
                System.out.println(k + "=" + v));
        System.out.println(m.get("BENIN"));
        m.entrySet().forEach(System.out::println);
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

class Maps {
    public static void printKeys(Map<Object, Object> map) {
        System.out.println("Size = " + map.size() + ", ");
        System.out.println("Keys: ");
        System.out.println(map.keySet()); // Produce a Set of the keys
    }

    public static void printValues(Map<Object, Object> map) {
        System.out.println("Size = " + map.size() + ", ");
        System.out.println("Values: ");
        System.out.println(map.values()); // Produce a Set of the keys
    }

    public static void printEntries(Map<Object, Object> map) {
        System.out.println("Size = " + map.size() + ", ");
        System.out.println("Entries: ");
        System.out.println(map.entrySet()); // Produce a Set of the keys
    }

    public static void test(Map<Object, Object> map) {
        System.out.println(map.getClass().getSimpleName());
        map.putAll(new CountingMapData(25));
        // Map has 'Set' behavior for keys:
        map.putAll(new CountingMapData(25));
        printKeys(map);
        // Producing a Collection of the values:
        System.out.println("Values: ");
        System.out.println(map.values());
        System.out.println(map);
        System.out.println("map.containsKey(11): " + map.containsKey(11));
        System.out.println("map.get(11): " + map.get(11));
        System.out.println("map.containsValue(\"F0\"): "
                + map.containsValue("F0"));
        Integer key = (Integer) map.keySet().iterator().next();
        System.out.println("First key in map: " + key);
        System.out.println("try removing first key: " + map.remove(key));
        printEntries(map);
        map.clear();
        System.out.println("map.isEmpty(): " + map.isEmpty());
        map.putAll(new CountingMapData(25));
        // Operations on the Set change the Map:
        map.keySet().removeAll(map.keySet());
        System.out.println("map.isEmpty(): " + map.isEmpty());
        printEntries(map);
    }

    public static void main(String[] args) {
        test(new SlowMapTested<>());
    }
}

