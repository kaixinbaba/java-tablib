package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.java.tablib.exceptions.HeadNotExistsException;
import io.github.java.tablib.exceptions.InvalidDimensionsException;
import io.github.java.tablib.formats.Format;
import io.github.java.tablib.formats.Formats;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 核心对象，支持了绝大部分的方法
 */
@SuppressWarnings("all")
public class Dataset {


    private static final Map<String, Format> formats = Maps.newHashMap();

    static {
        registerFormats();
    }

    private static void registerFormats() {
        for (Format format : Formats.AVAILABLE) {
            formats.put(format.ext(), format);
        }
    }

    private List<Row> data;
    // 列名
    private List<String> headers;
    private String title;

    public Dataset() {
        this(null);
    }

    public Dataset(List<Collection> data) {
        this(data, null, null);
    }

    public Dataset(List<Collection> data, List headers, String title) {
        if (data != null && !data.isEmpty() && headers != null && !headers.isEmpty()) {
            if (data.get(0).size() != headers.size()) {
                throw new InvalidDimensionsException();
            }
        }
        if (data != null) {
            this.data = initData(data);
        }
        if (headers != null) {
            this.headers = Lists.newArrayList(headers);
        }
        if (title != null) {
            this.title = title;
        }
    }

    private List<Row> initData(List<Collection> data) {
        List<Row> result = Lists.newArrayList();
        for (Collection rowData : data) {
            result.add(new Row(rowData));
        }
        return result;
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Collection headers) {
        validate(headers);
        if (headers == null || headers.isEmpty()) {
            this.headers = null;
        } else {
            this.headers = Lists.newArrayList(headers);
        }
    }

    private boolean validate(Collection row) {
        return validate(row, null, true);
    }

    private boolean validate(Collection row, Collection col, boolean safety) {
        boolean valid = false;
        if (row != null && !row.isEmpty()) {
            valid = width() == 0 ? true : width() == row.size();
        } else if (col != null && !col.isEmpty()) {
            valid = height() == 0 ? true : height() == col.size();
        } else {
            List<Row> uncorrect = this.data.stream().filter(r -> r.size() != width()).collect(Collectors.toList());
            valid = uncorrect.isEmpty();
        }
        if (valid) {
            return true;
        } else {
            if (safety) {
                return false;
            } else {
                throw new InvalidDimensionsException();
            }
        }
    }

    public void lpush(Collection row) {
        this.insert(0, row);
    }

    public void lpush(Collection row, String tag) {
        this.insert(0, row, tag);
    }

    public void lpush(Collection row, Iterable<String> tags) {
        this.insert(0, row, tags);
    }

    public void append(Collection row) {
        this.rpush(row);
    }

    public void extend(Collection<Collection> rows) {
        for (Collection row : rows) {
            this.append(row);
        }
    }

    public void rpush(Collection row) {
        this.insert(this.height(), row);
    }

    public void rpush(Collection row, String tag) {
        this.insert(this.height(), row, tag);
    }

    public void rpush(Collection row, Iterable<String> tags) {
        this.insert(this.height(), row, tags);
    }

    public void insert(int index, Collection row) {
        this.insert(index, row, (Iterable<String>) null);
    }

    public void insert(int index, Collection row, String tag) {
        Iterable tags = tag == null ? null : Lists.newArrayList(tag);
        this.insert(index, row, tags);
    }

    public void insert(int index, Collection row, Iterable<String> tags) {
        this.validate(row);
        this.data.add(index, new Row(row, tags));
    }

    public Row pop() {
        return rpop();
    }

    public Row rpop() {
        if (this.height() == 0) {
            return null;
        }
        return this.data.remove(this.height() - 1);
    }

    public Row lpop() {
        if (this.height() == 0) {
            return null;
        }
        return this.data.remove(0);
    }

    public int height() {
        return this.data.size();
    }

    public int width() {
        if (height() > 0) {
            return this.data.get(0).size();
        } else {
            if (this.headers.isEmpty()) {
                return 0;
            } else {
                return this.headers.size();
            }
        }
    }

    public List get(int index) {
        return this.data.get(index).toList();
    }

    public List get(String head) {
        this.checkHeadExists(head);
        int colIndex = this.headers.indexOf(head);
        return this.data.stream().map(r -> {
            return r.get(colIndex);
        }).collect(Collectors.toList());
    }

    public void set(int index, Collection value) {
        this.validate(value);
        this.data.set(index, new Row(value));
    }

    public List remove(int index) {
        Row remove = this.data.remove(index);
        return remove.toList();
    }

    private void checkHeadExists(String head) {
        if (!this.headers.contains(head)) {
            throw new HeadNotExistsException();
        }
    }

    public List remove(String head) {
        this.checkHeadExists(head);
        int colIndex = this.headers.indexOf(head);
        List removed = this.data.stream().map(r -> {
            return r.remove(colIndex);
        }).collect(Collectors.toList());
        return removed;
    }

    public List<Map<String, Object>> toDict() {
        List<Map<String, Object>> result = Lists.newArrayList();
        if (this.isEmpty()) {
            return result;
        }
        if (!this.hasHead()) {
            throw new HeadNotExistsException();
        }
        List<Row> _data = Lists.newArrayList(this.data);
        for (int r = 0; r < this.height(); r++) {
            Row row = _data.get(r);
            Map<String, Object> dict = Maps.newLinkedHashMap();
            for (int c = 0; c < this.width(); c++) {
                String head = this.headers.get(c);
                Object value = row.get(c);
                dict.put(head, value);
            }
            result.add(dict);
        }
        return result;
    }

    public List<List> toList() {
        if (this.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Row> _data = Lists.newArrayList(this.data);
        List<List> result = _data.stream().map(r -> r.toList()).collect(Collectors.toList());
        return result;
    }

    public boolean hasHead() {
        return this.headers != null && !this.headers.isEmpty();
    }

    public int size() {
        return height();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
