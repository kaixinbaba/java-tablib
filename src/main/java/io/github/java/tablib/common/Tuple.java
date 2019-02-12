package io.github.java.tablib.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

public class Tuple<E> extends ArrayList<E> {

    private static class TupleCannotModifyException extends RuntimeException {

        public TupleCannotModifyException() {
            super("Tuple is immutable object! Can't modify!");
        }
    }

    public Tuple(Collection<E> collection) {
        super(collection);
    }

    @Override
    public boolean add(E e) {
        throw new TupleCannotModifyException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new TupleCannotModifyException();
    }

    @Override
    public void add(int index, E element) {
        throw new TupleCannotModifyException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new TupleCannotModifyException();
    }

    @Override
    public E remove(int index) {
        throw new TupleCannotModifyException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new TupleCannotModifyException();
    }

    @Override
    public boolean remove(Object o) {
        throw new TupleCannotModifyException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new TupleCannotModifyException();
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw new TupleCannotModifyException();
    }

    @Override
    public E set(int index, E element) {
        throw new TupleCannotModifyException();
    }

    @Override
    public void clear() {
        throw new TupleCannotModifyException();
    }
}
