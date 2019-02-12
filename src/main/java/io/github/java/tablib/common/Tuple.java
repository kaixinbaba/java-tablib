package io.github.java.tablib.common;

import java.util.ArrayList;
import java.util.Collection;

public class Tuple<E> extends ArrayList<E> {

    public Tuple(Collection<E> collection) {
        super(collection);
    }
}
