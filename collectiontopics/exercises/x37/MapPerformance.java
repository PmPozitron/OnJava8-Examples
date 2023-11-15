package exercises.x37;

import exercises.x36.SlowMapOnSingleList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class MapPerformance {
    static List<Test<Map<Integer,Integer>>> tests = new ArrayList<Test<Map<Integer,Integer>>>();
    static {
        tests.add(new Test<>("put") {
            public int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops;
                int size = tp.size;
                for (int i = 0; i < loops; i++) {
                    map.clear();
                    for (int j = 0; j < size; j++)
                        map.put(j, j);
                }
                return loops * size;
            }
        });
        tests.add(new Test<>("get") {
            public int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops;
                int span = tp.size * 2;
                for (int i = 0; i < loops; i++)
                    for (int j = 0; j < span; j++)
                        map.get(j);
                return loops * span;
            }
        });
        tests.add(new Test<>("iterate") {
            public int test(Map<Integer, Integer> map, TestParam tp) {
                int loops = tp.loops * 10;
                for (int i = 0; i < loops; i++) {
                    Iterator it = map.entrySet().iterator();
                    while (it.hasNext())
                        it.next();
                }
                return loops * map.size();
            }
        });
    }
    public static void main(String[] args) {
        if(args.length > 0)
            Tester.defaultParams = TestParam.array(args);
        Tester.run(new TreeMap<>(), tests);
        Tester.run(new HashMap<>(), tests);
//        SlowMapOnSingleList<Integer, Integer> slowMap = new SlowMapOnSingleList();
//        Tester.run(slowMap, tests);
//        conductChecks(slowMap);
//        Tester.run(new SimpleHashMapOnArrayList<>(), tests);
//        Tester.run(new SimpleHashMap<>(), tests);

    }

    private static void conductChecks(SlowMapOnSingleList<Integer, Integer> map) {
        Random rnd = new Random();
        HashSet<Integer> set = new HashSet<>();
        IntStream.range(1, 1000).forEach(i -> {
            int next = rnd.nextInt(Integer.MAX_VALUE / 100);
            set.add(next);
            map.put(next, next*10);
        });

        set.stream().forEach(i -> {
            assert map.get(i) == i * 10;
        });

        map.entrySet().forEach(entry -> {
            entry.setValue(entry.getValue() * 5);
        });

        set.stream().forEach(i -> {
            assert map.get(i) == i * 50;
        });
    }



}
