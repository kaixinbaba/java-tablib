package io.github.java.tablib.core;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 代表的是一行数据
 */
@SuppressWarnings("all")
public class Row {

    private List row;
    private List tags;

    Row() {
        this(null, (Collection) null);
    }

    Row(Collection row, String tag) {
        this(row, Lists.newArrayList(tag));
    }

    Row(Collection row, Collection tags) {
        this.row = Lists.newArrayList();
        if (row != null) {
            this.row.addAll(row);
        }
        this.tags = Lists.newArrayList();
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }
}
