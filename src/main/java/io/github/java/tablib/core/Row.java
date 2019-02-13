package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.github.java.tablib.common.Tuple;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 代表的是一行数据
 */
@SuppressWarnings("all")
public class Row implements Iterable {

    private List row;
    private List tags;

    Row() {
        this(null, (Collection) null);
    }

    Row(String tag) {
        this(null, tag);
    }

    Row(Iterable row) {
        this(row, (Iterable) null);
    }

    Row(Iterable row, String tag) {
        this(row, Lists.newArrayList(tag));
    }

    Row(Iterable row, Iterable tags) {
        this.row = Lists.newArrayList();
        if (row != null) {
            this.row.addAll(Lists.newArrayList(row));
        }
        this.tags = Lists.newArrayList();
        if (tags != null) {
            this.tags.addAll(Lists.newArrayList(tags));
        }
    }

    public Tuple toTuple() {
        return new Tuple(this.row);
    }

    public List toList() {
        return Lists.newArrayList(this.row);
    }

    public Set toSet() {
        return Sets.newHashSet(this.row);
    }

    public boolean contains(Object value) {
        return this.row.contains(value);
    }

    public void lpush(Object value) {
        this.row.add(0, value);
    }

    public void rpush(Object value) {
        this.row.add(value);
    }

    public void append(Object value) {
        this.rpush(value);
    }

    public void insert(int index, Object value) {
        this.row.add(index, value);
    }

    public Object remove(int index) {
        return this.row.remove(index);
    }

    public void set(int index, Object value) {
        this.row.set(index, value);
    }

    public Object get(int index) {
        return this.row.get(index);
    }

    public int size() {
        return this.row.size();
    }

    public boolean isEmpty() {
        return this.row.isEmpty();
    }

    public boolean hasTag(String tag) {
        if (StringUtils.isEmpty(tag)) {
            return false;
        }
        return this.tags.contains(tag);
    }

    public boolean hasTag(Collection tags) {
        if (tags == null || tags.isEmpty()) {
            return false;
        }
        Set set1 = Sets.newHashSet(tags);
        Set set2 = Sets.newHashSet(this.tags);
        set1.retainAll(set2);
        return !set1.isEmpty();
    }

    @Override
    public String toString() {
        return this.row.toString();
    }

    @Override
    public Iterator iterator() {
        return this.row.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Row)) {
            return false;
        }
        Row other = (Row) obj;
        if (this.size() != other.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            Object o1 = this.get(i);
            Object o2 = other.get(i);
            if (!Objects.equals(o1, o2)) {
                return false;
            }
        }
        return true;
    }
}
