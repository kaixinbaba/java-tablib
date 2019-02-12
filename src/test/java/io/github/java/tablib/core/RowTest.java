package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void testlpush() {
        this.row.append("fake1");
        this.row.append("fake2");
        String value = "kaixin";
        this.row.lpush(value);
        Assert.assertEquals(this.row.get(0), value);
    }

    @Test
    public void testrpush() {
        this.row.append("fake1");
        this.row.append("fake2");
        String value = "kaixin";
        this.row.rpush(value);
        Assert.assertEquals(this.row.get(this.row.size() - 1), value);
    }

    @Test
    public void testinsert() {
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


}
