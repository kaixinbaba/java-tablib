package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import io.github.java.tablib.common.Tuple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
public class RowTest {

    private Row row;

    @Before
    public void before() {
        this.row = new Row("kaixinbaba");
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(this.row.isEmpty());
        this.row.append("111");
        Assert.assertFalse(this.row.isEmpty());
    }

    @Test
    public void testSize() {
        Assert.assertEquals(0, this.row.size());
        this.row.append("111");
        Assert.assertEquals(1, this.row.size());
    }

    @Test
    public void testLpush() {
        this.row.append("fake1");
        this.row.append("fake2");
        String value = "kaixin";
        this.row.lpush(value);
        Assert.assertEquals(this.row.get(0), value);
    }

    @Test
    public void testRpush() {
        this.row.append("fake1");
        this.row.append("fake2");
        String value = "kaixin";
        this.row.rpush(value);
        Assert.assertEquals(this.row.get(this.row.size() - 1), value);
    }

    @Test
    public void testInsert() {
        this.row.append("fake1");
        this.row.append("fake2");
        String value = "kaixin";
        this.row.insert(1, value);
        Assert.assertEquals(this.row.get(1), value);
    }

    @Test
    public void testHasTag() {
        Assert.assertTrue(this.row.hasTag("kaixinbaba"));
        Assert.assertFalse(this.row.hasTag("bukaixin"));
        Assert.assertTrue(this.row.hasTag(Lists.newArrayList("kaixinbaba")));
    }

    @Test
    public void testToSet() {
        this.row.append("111");
        this.row.append("222");
        Set set = this.row.toSet();
        Assert.assertEquals(this.row.size(), set.size());
    }

    @Test
    public void testToArray() {
        this.row.append("111");
        this.row.append("222");
        Object[] objects = this.row.toArray();
        Assert.assertEquals(this.row.size(), objects.length);
    }

    @Test
    public void testToList() {
        this.row.append("111");
        this.row.append("222");
        List list = this.row.toList();
        Assert.assertEquals(this.row.size(), list.size());
    }

    @Test
    public void testToTuple() {
        this.row.append("111");
        this.row.append("222");
        Tuple tuple = this.row.toTuple();
        Assert.assertEquals(this.row.size(), tuple.size());
    }

    @Test
    public void testContains() {
        this.row.append("111");
        Assert.assertTrue(this.row.contains("111"));
        Assert.assertFalse(this.row.contains("333"));
    }

    @Test
    public void testExtend() {
        this.row.append("111");
        int oldSize = this.row.size();
        ArrayList<String> strings = Lists.newArrayList("123", "455");
        int extendSize = strings.size();
        this.row.extend(strings);
        Assert.assertEquals(this.row.size(), oldSize + extendSize);
    }

    @Test
    public void testGet() {
        this.row.append("111");
        this.row.append("222");
        Assert.assertEquals(this.row.get(0), "111");
        Assert.assertEquals(this.row.get(1), "222");
        Assert.assertEquals(this.row.get(-1), "222");
    }

    @Test
    public void testSet() {
        this.row.append("111");
        this.row.append("askjdfk");
        this.row.set(1, "222");
        Assert.assertEquals(this.row.get(1), "222");
        this.row.set(-1, "333");
        Assert.assertEquals(this.row.get(-1), "333");
    }

    @Test
    public void testEquals() {
        this.row.append("111");
        this.row.append("askjdfk");
        Row other1 = new Row();
        other1.append("111");
        other1.append("askjdfk");
        Assert.assertEquals(this.row, other1);
        Row other2 = new Row();
        other2.append("112");
        other2.append("askjdfk");
        Assert.assertNotEquals(this.row, other2);
    }


}
