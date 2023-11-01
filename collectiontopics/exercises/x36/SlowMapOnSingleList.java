package exercises.x36;

import exercises.x35.MapEntry;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class SlowMapOnSingleList <K,V> extends AbstractMap<K,V> {
    private ArrayList<MapEntry<K,V>> entries = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        MapEntry<K,V> aNew = new MapEntry<>(key, value);
        Optional<MapEntry<K,V>> result = entries.stream()
                .filter(cur -> cur.getKey().equals(key))
                .findAny();
        AtomicReference<V> previous = new AtomicReference<>();
        result.ifPresentOrElse(cur -> {
            previous.set(cur.getValue());
            cur.setValue(value);
        }, () -> entries.add(aNew));

        return previous.get();
    }

    @Override
    public V get(Object key) { // key: type Object, not K
        Optional<MapEntry<K,V>> result = entries.stream()
                .filter(cur -> cur.getKey().equals(key))
                .findAny();
        if (result.isPresent())
            return result.get().getValue();
        else return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>(entries);
    }
}