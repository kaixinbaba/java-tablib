package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.java.tablib.exceptions.HeadNeededException;
import io.github.java.tablib.exceptions.HeadNotExistsException;
import io.github.java.tablib.exceptions.InvalidDimensionsException;
import io.github.java.tablib.formats.Format;
import io.github.java.tablib.formats.Formats;
import io.github.java.tablib.utils.TablibUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 核心对象，支持了绝大部分的方法
 */
@SuppressWarnings("all")
public class Dataset implements Iterable<Row> {


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

    public Dataset(List<Collection> data, List headers) {
        this(data, headers, null);
    }

    public Dataset(List<Collection> data, List headers, String title) {
        if (data != null && !data.isEmpty() && headers != null && !headers.isEmpty()) {
            if (data.get(0).size() != headers.size()) {
                throw new InvalidDimensionsException();
            }
        }
        this.data = initData(data);
        if (headers != null) {
            this.headers = Lists.newArrayList(headers);
        } else {
            this.headers = Lists.newArrayList();
        }
        if (title != null) {
            this.title = title;
        }
    }

    private Dataset deepCopy(Dataset origin) {
        List<Collection> copyData = origin.data.stream().map(r -> {
            return r.toList();
        }).collect(Collectors.toList());
        List<String> copyHeaders = Lists.newArrayList(origin.headers);
        return new Dataset(copyData, copyHeaders, origin.title);
    }

    private List<Row> initData(List<Collection> data) {
        List<Row> result = Lists.newArrayList();
        if (data == null || data.isEmpty()) {
            return result;
        }
        for (Collection rowData : data) {
            result.add(new Row(rowData));
        }
        return result;
    }

    public List<String> getHeaders() {
        return this.headers;
    }

    public void setHeaders(Collection headers) {
        if (headers == null || headers.isEmpty()) {
            this.headers = null;
        } else {
            this.headers = Lists.newArrayList(headers);
        }
    }

    private boolean validate(Collection row) {
        return validate(row, null, false);
    }

    private boolean validate(Collection row, Collection col) {
        return validate(row, col, false);
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

    public void push(Collection row) {
        this.rpush(row);
    }

    public void push(Collection row, String tag) {
        this.rpush(row, tag);
    }

    public void push(Collection row, Iterable<String> tags) {
        this.rpush(row, tags);
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
        try {
            return this.data.size();
        } catch (NullPointerException npe) {
            return 0;
        }
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
        if (index < 0) {
            index = this.size() + index;
        }
        return this.data.get(index).toList();
    }

    public void set(int index, Collection value) {
        this.validate(value);
        if (index < 0) {
            index = this.size() + index;
        }
        this.data.set(index, new Row(value));
    }

    public List remove(int index) {
        if (index < 0) {
            index = this.size() + index;
        }
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
        if (!this.hasHeaders()) {
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

    public void fromDict(List<Map<String, Object>> from) {
        if (from == null || from.isEmpty()) {
            return;
        }
        this.wipe();
        this.headers = Lists.newArrayList(from.get(0).keySet());
        this.data = from.stream().map(m -> {
            return new Row(m.values());
        }).collect(Collectors.toList());
    }

    public List<List> toList() {
        if (this.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Row> _data = Lists.newArrayList(this.data);
        List<List> result = _data.stream().map(r -> r.toList()).collect(Collectors.toList());
        return result;
    }

    /**
     * this method will remove the origin headers
     *
     * @param from
     */
    public void fromList(List<List> from) {
        if (from == null || from.isEmpty()) {
            return;
        }
        this.wipe();
        this.data = from.stream().map(l -> new Row(l)).collect(Collectors.toList());
    }

    public Dataset filter(String tag) {
        Dataset copy = this.deepCopy(this);
        copy.data = copy.data.stream().filter(r -> r.hasTag(tag)).collect(Collectors.toList());
        return copy;
    }

    public Dataset sort(String head) {
        this.checkHeadExists(head);
        return sort(this.headers.indexOf(head));
    }

    public Dataset sort(String head, boolean reverse) {
        this.checkHeadExists(head);
        return sort(this.headers.indexOf(head), reverse);
    }

    public Dataset sort(String head, boolean inPlace, boolean reverse) {
        this.checkHeadExists(head);
        return sort(this.headers.indexOf(head), inPlace, reverse);

    }

    public Dataset sort(int headIndex) {
        return sort(headIndex, true, false);
    }

    public Dataset sort(int headIndex, boolean reverse) {
        return sort(headIndex, true, reverse);
    }

    /**
     * @param headIndex sort by which head's index
     * @param inPlace   true:replace current object, false:return a new copy object
     * @param reverse
     * @return
     */
    public Dataset sort(int headIndex, boolean inPlace, boolean reverse) {
        if (this.isEmpty()) {
            return this.handleInplace(inPlace, true);
        }
        Object o = this.data.get(0).get(0);
        if (!(o instanceof Comparable)) {
            return this.handleInplace(inPlace, false);
        }
        List<Row> _data = Lists.newArrayList(this.data);
        if (reverse) {
            _data.sort(((Comparator<Row>) (o1, o2) -> ((Comparable) o1.get(headIndex)).compareTo(o2.get(headIndex))).reversed());
        } else {
            _data.sort(((Comparator<Row>) (o1, o2) -> ((Comparable) o1.get(headIndex)).compareTo(o2.get(headIndex))));
        }
        if (inPlace) {
            this.data = _data;
            return this;
        } else {
            Dataset copy = this.deepCopy(this);
            copy.data = _data;
            return copy;
        }
    }

    private Dataset handleInplace(boolean inPlace, boolean isEmpty) {
        if (inPlace) {
            return this;
        } else {
            if (isEmpty) {
                return new Dataset();
            } else {
                return this.deepCopy(this);
            }
        }
    }

    public Dataset stack(Dataset other) {
        Dataset result = this.deepCopy(this);
        if (other.isEmpty()) {
            return result;
        }
        if (this.width() != other.width()) {
            throw new InvalidDimensionsException();
        }
        for (Row row : other.data) {
            result.append(row.toList());
        }
        return result;
    }

    public void removeDuplicates() {
        Set<Row> set = Sets.newLinkedHashSet();
        set.addAll(this.data);
        this.data = Lists.newArrayList(set);
    }


    public boolean hasHeaders() {
        return this.headers != null && !this.headers.isEmpty();
    }

    /**
     * Only clean data, not headers
     *
     * @return
     */
    public void clear() {
        this.data.clear();
    }

    /**
     * Clean data and headers
     */
    public void wipe() {
        this.clear();
        this.headers = null;
    }

    public int size() {
        return height();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Dataset subset() {
        return subset(null, null);
    }

    public Dataset subset(List<String> colIndex) {
        return subset(null, colIndex);
    }


    /**
     * return a new subset, without origin's title
     * @param rowIndex
     * @param colHeaderIndex
     * @return
     */
    public Dataset subset(List<Integer> rowIndex, List<String> colHeaderIndex) {
        if (this.isEmpty()) {
            return new Dataset();
        }
        if (rowIndex == null || rowIndex.isEmpty()) {
            rowIndex = TablibUtils.range(this.height());
        }
        if (colHeaderIndex == null || colHeaderIndex.isEmpty()) {
            colHeaderIndex = Lists.newArrayList(this.headers);
        }
        List<Integer> colNumberIndex = colHeaderIndex.stream().map(h -> this.headers.indexOf(h)).collect(Collectors.toList());
        Dataset subset = new Dataset();
        subset.data = rowIndex.stream().map(i -> {
            final Row row = this.data.get(i);
            Row newRow = new Row(colNumberIndex.stream().map(ci -> row.get(ci)).collect(Collectors.toList()));
            return newRow;
        }).collect(Collectors.toList());
        subset.headers = Lists.newArrayList(colHeaderIndex);
        return subset;
    }

    public Dataset stackCol(Dataset other) {
        Dataset copy = this.deepCopy(this);
        if (other == null || other.isEmpty()) {
            return copy;
        }
        // check header
        // must both has or hasnot
        if (this.hasHeaders() || other.hasHeaders()) {
            if (!this.hasHeaders() || !other.hasHeaders()) {
                throw new HeadNeededException();
            }
        }
        // check height
        if (this.height() != other.height()) {
            throw new InvalidDimensionsException();
        }
        copy.headers.addAll(other.headers);
        for (int i = 0; i < copy.height(); i++) {
            copy.data.get(i).extend(other.data.get(i).toList());
        }
        return copy;
    }

    public Dataset transpose() {
        if (this.isEmpty()) {
            return null;
        }
        Dataset copy = new Dataset();
        List<String> newHeaders = Lists.newArrayList();
        if (this.hasHeaders()) {
            newHeaders.add(this.headers.get(0));
        }
        newHeaders.addAll(this.getCol(0));
        copy.headers = newHeaders;
        List<Row> newData = Lists.newArrayList();
        for (int i = 0; i < width(); i++) {
            List oneRow = Lists.newArrayList();
            if (this.hasHeaders()) {
                oneRow.add(this.headers.get(i));
            }
            oneRow.addAll(this.getCol(i));
            Row newRow = new Row(oneRow);
            newData.add(newRow);
        }
        copy.data = newData;
        return copy;
    }

    public List getCol(String head) {
        this.checkHeadExists(head);
        return this.getCol(this.headers.indexOf(head));
    }

    public List getCol(int index) {
        if (index < 0) {
            index = this.width() + index;
        }
        final int newIndex = index;
        return this.data.stream().map(r -> r.get(newIndex)).collect(Collectors.toList());
    }

    public void insertCol(int colIndex, List col) {
        insertCol(colIndex, col, null);
    }

    public void insertCol(int colIndex, List col, String head) {
        if (this.hasHeaders() && StringUtils.isEmpty(head)) {
            throw new HeadNeededException();
        }
        this.validate(null, col);
        if (colIndex < 0) {
            colIndex = this.width() + colIndex;
        }
        if (this.hasHeaders()) {
            this.headers.add(colIndex, head);
        }
        for (int i = 0; i < height(); i++) {
            Row row = this.data.get(i);
            row.insert(colIndex, col.get(i));
        }
    }

    public void rpushCol(List col, String head) {
        insertCol(this.width(), col, head);
    }

    public void lpushCol(List col, String head) {
        insertCol(0, col, head);
    }

    public void appendCol(List col, String head) {
        rpushCol(col, head);
    }

    @Override
    public Iterator<Row> iterator() {
        return this.data.iterator();
    }

    // formats about


}
