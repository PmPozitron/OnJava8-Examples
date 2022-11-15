package exercises.x11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CollectionVisitor {
    public static void main(String[] args) {
        Object[] objects = new Object[]{1, 2.0, "lalala", Boolean.TRUE, 'c'};
        List<Object> theList = new ArrayList<>();
        Set<Object> theSet = new HashSet<>();
        Collections.addAll(theList, objects);
        Collections.addAll(theSet, objects);

        for (Collection<Object> collection : List.of(theList, theSet)) {
            visit(collection);
        }

        Collections.shuffle(theList);
        visit(theList);



    }

    public static void visit(Collection<Object> clt) {
        Iterator<Object> iterator = clt.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
