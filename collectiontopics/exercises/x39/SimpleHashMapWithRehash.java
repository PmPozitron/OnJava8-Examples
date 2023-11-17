package exercises.x39;

// equalshashcode/SimpleHashMap.java
// (c)2021 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://OnJava8.com for more book information.
// A demonstration hashed Map

import exercises.x37.MapEntry;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.IntStream;

public class SimpleHashMapWithRehash<K, V> extends AbstractMap<K, V> {
    // Choose a prime number for the hash table
    // size, to achieve a uniform distribution:
    static final int INITIAL_NUMBER_OF_BUCKETS = 997;
    static final float MAXIMUM_LOAD_FACTOR = 0.75f;
    int currentSize = 0;
    int nextMaximumSize = INITIAL_NUMBER_OF_BUCKETS * 10;

    // You can't have a physical array of generics,
    // but you can upcast to one:
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[INITIAL_NUMBER_OF_BUCKETS];


    @Override
    public V put(K key, V value) {

        V oldValue = null;
        int index = Math.abs(key.hashCode()) % INITIAL_NUMBER_OF_BUCKETS;
        if (buckets[index] == null)
            buckets[index] = new LinkedList<>();
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
        if (!found) {
            buckets[index].add(pair);
            currentSize++;
            if (currentSize >= nextMaximumSize * MAXIMUM_LOAD_FACTOR) {
                rehash();
            }
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % INITIAL_NUMBER_OF_BUCKETS;
        if (buckets[index] == null) return null;
        for (MapEntry<K, V> iPair : buckets[index])
            if (iPair.getKey().equals(key))
                return iPair.getValue();
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket == null) continue;
            for (MapEntry<K, V> mpair : bucket)
                set.add(mpair);
        }
        return set;
    }

    private void rehash() {
        nextMaximumSize = calculateNextNumberOfBuckets();
        LinkedList[] old = buckets;
        buckets = new LinkedList[old.length * 2];
//        nextMaximumSize = nextMaximumSize * 2;
        System.out.println("load factor after rehash " + ((float)currentSize / nextMaximumSize));
        entrySet().stream().forEach(entry -> put(entry.getKey(), entry.getValue()));
    }

    private int calculateNextNumberOfBuckets() {
        int result = buckets.length * 2;
        boolean notFound = true;
        while (notFound) {
            int i = 2;
            for (; i < result; i++) {
                if (result % i == 0) {
                    break;
                }
            }
            if (i == result) {
                notFound = false;
            } else {
                ++result;
//                System.out.println("incrementing result " + result);
            }
        }
        System.out.println("next max size " + result);
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        SimpleHashMapWithRehash<Integer, String> theMap = new SimpleHashMapWithRehash<>();
        IntStream.range(0, theMap.nextMaximumSize / 2).forEach(i -> {
            theMap.put(i, String.valueOf(i));
            list.add(i);
        });

        Collections.shuffle(list);
        long start = System.nanoTime();

        list.stream().forEach(theMap::get);
        System.out.println(System.nanoTime() - start);
        System.out.println((float)theMap.currentSize / theMap.nextMaximumSize);

        list.clear();
        IntStream.range(theMap.currentSize, theMap.nextMaximumSize).forEach(i -> {
            list.add(i);
            theMap.put(i, String.valueOf(i));
        });

        Collections.shuffle(list);
        start = System.nanoTime();

        list.stream().forEach(theMap::get);
        System.out.println(System.nanoTime() - start);
        System.out.println((float)theMap.currentSize / theMap.nextMaximumSize);
    }
}
/* Output:
CAMEROON=Yaounde
ANGOLA=Luanda
BURKINA FASO=Ouagadougou
BURUNDI=Bujumbura
ALGERIA=Algiers
BENIN=Porto-Novo
CAPE VERDE=Praia
BOTSWANA=Gaberone
Porto-Novo
CAMEROON=Yaounde
ANGOLA=Luanda
BURKINA FASO=Ouagadougou
BURUNDI=Bujumbura
ALGERIA=Algiers
BENIN=Porto-Novo
CAPE VERDE=Praia
BOTSWANA=Gaberone
*/

