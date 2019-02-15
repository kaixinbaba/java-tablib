package io.github.java.tablib.core;

import com.google.common.collect.Lists;
import io.github.java.tablib.exceptions.InvalidDimensionsException;
import io.github.java.tablib.exceptions.TablibBaseException;
import io.github.java.tablib.utils.TablibUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatasetTest {

    private Dataset dataset;

    @Before
    public void before() {
        dataset = new Dataset();
    }

    @Test
    public void testHeaders() {
        Assert.assertFalse(this.dataset.hasHeaders());
        ArrayList<String> strings = Lists.newArrayList("111");
        this.dataset.setHeaders(strings);
        Assert.assertTrue(this.dataset.hasHeaders());
        List<String> headers = this.dataset.getHeaders();
        Assert.assertEquals(strings.size(), headers.size());
        this.dataset.removeHeaders();
        Assert.assertFalse(this.dataset.hasHeaders());
    }
    @Test
    public void isEmpty() {
        Assert.assertTrue(dataset.isEmpty());
        dataset.append(Lists.newArrayList("dkfjkj"));
        Assert.assertFalse(dataset.isEmpty());
    }

    @Test
    public void testPush() {
        ArrayList<String> row = Lists.newArrayList("123", "dfkjk");
        this.dataset.append(row);
        ArrayList<String> lpush = Lists.newArrayList("abc", "dfkjk");
        this.dataset.lpush(lpush);
        Assert.assertEquals(this.dataset.get(0).get(0), "abc");
        ArrayList<String> rpush = Lists.newArrayList("xyz", "dfkjk");
        this.dataset.rpush(rpush);
        Assert.assertEquals(this.dataset.get(-1).get(0), "xyz");
        Collection<Collection> extend = Lists.newArrayList();
        extend.add(row);
        extend.add(lpush);
        extend.add(rpush);
        this.dataset.extend(extend);
        Assert.assertEquals(this.dataset.size(), extend.size() + 3);
    }

    @Test
    public void testPop() {
        ArrayList<String> row = Lists.newArrayList("123", "dfkjk");
        this.dataset.append(row);
        ArrayList<String> row1 = Lists.newArrayList("abc", "dfkjk");
        this.dataset.append(row1);
        ArrayList<String> row2 = Lists.newArrayList("efg", "dfkjk");
        this.dataset.append(row2);
        ArrayList<String> row3 = Lists.newArrayList("xyz", "dfkjk");
        this.dataset.append(row3);

        Row pop = this.dataset.pop();
        Assert.assertEquals(pop.get(0), "xyz");
        Row pop1 = this.dataset.rpop();
        Assert.assertEquals(pop1.get(0), "efg");
        Row pop2 = this.dataset.lpop();
        Assert.assertEquals(pop2.get(0), "123");
    }

    @Test
    public void testHW() {
        Assert.assertEquals(this.dataset.width(), 0);
        Assert.assertEquals(this.dataset.height(), 0);
        this.dataset.append(Lists.newArrayList("123", "dfk", "dfkj"));
        Assert.assertEquals(this.dataset.width(), 3);
        Assert.assertEquals(this.dataset.height(), 1);
    }


    @Test
    public void testGet() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "dfk", "dfkj"));
        Assert.assertEquals(this.dataset.get(0).get(0), "123");
        Assert.assertEquals(this.dataset.get(-1).get(0), "abc");
        Assert.assertEquals(this.dataset.getCol("age").get(0), "18");
        Assert.assertEquals(this.dataset.getCol(2).get(0), "nan");
        Assert.assertEquals(this.dataset.getCol(-2).get(0), "18");
    }

    @Test
    public void testSet() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "dfk", "dfkj"));
        this.dataset.set(0, Lists.newArrayList("xyz", "28", "nv"));
        Assert.assertEquals(this.dataset.get(0).get(0), "xyz");
        this.dataset.set(-1, Lists.newArrayList("hig", "34", "nv"));
        Assert.assertEquals(this.dataset.get(-1).get(0), "hig");
    }

    @Test
    public void testRemove() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        this.dataset.append(Lists.newArrayList("efg", "25", "ier"));
        this.dataset.append(Lists.newArrayList("efg", "25", "ier"));
        this.dataset.append(Lists.newArrayList("xyz", "25", "ier"));
        List remove = this.dataset.remove(1);
        Assert.assertEquals(remove.get(0), "abc");
        Assert.assertEquals(this.dataset.height(), 4);
        List remove1 = this.dataset.remove(-1);
        Assert.assertEquals(remove1.get(0), "xyz");
        List age = this.dataset.remove("age");
        Assert.assertEquals(age.get(0), "18");
        Assert.assertEquals(this.dataset.width(), 2);
        this.dataset.removeDuplicates();
        Assert.assertEquals(this.dataset.height(), 2);
    }

    @Test
    public void testClear() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        this.dataset.clear();
        Assert.assertTrue(this.dataset.isEmpty());
        Assert.assertTrue(this.dataset.hasHeaders());
    }

    @Test
    public void testWipe() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        this.dataset.wipe();
        Assert.assertTrue(this.dataset.isEmpty());
        Assert.assertFalse(this.dataset.hasHeaders());
    }

    @Test
    public void testSubset() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        Dataset subset = this.dataset.subset();
        Assert.assertEquals(this.dataset.size(), subset.size());
        Assert.assertEquals(this.dataset.width(), subset.width());
        Assert.assertNotEquals(this.dataset, subset);

        Dataset subset1 = this.dataset.subset(Lists.newArrayList("age", "sex"));
        Assert.assertEquals(this.dataset.size(), subset1.size());
        Assert.assertEquals(this.dataset.width(), subset1.width() + 1);

        Dataset subset2 = this.dataset.subset(Lists.newArrayList(0), Lists.newArrayList("age", "sex"));
        Assert.assertEquals(this.dataset.size(), subset2.size() + 1);
        Assert.assertEquals(this.dataset.width(), subset2.width() + 1);
    }

    @Test(expected = InvalidDimensionsException.class)
    public void testStack() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        Dataset other = new Dataset();
        other.append(Lists.newArrayList("9u34", "18", "dkd"));
        Dataset stack = this.dataset.stack(other);
        Assert.assertNotEquals(this.dataset, stack);
        Assert.assertEquals(this.dataset.size() + other.size(), stack.size());
        Dataset other1 = new Dataset();
        other1.append(Lists.newArrayList("dkfjk", "18"));
        Dataset stack1 = this.dataset.stack(other1);
    }

    @Test
    public void testPushCol() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        this.dataset.rpushCol(Lists.newArrayList("1", "2"), "no");
        Assert.assertEquals(this.dataset.width(), 4);
        Assert.assertEquals(this.dataset.getCol(3).get(0), "1");
        this.dataset.lpushCol(Lists.newArrayList("99", "100"), "id");
        Assert.assertEquals(this.dataset.width(), 5);
        Assert.assertEquals(this.dataset.getCol(0).get(0), "99");
        this.dataset.insertCol(1, Lists.newArrayList("fd", "dff"), "spec");
        Assert.assertEquals(this.dataset.width(), 6);
        Assert.assertEquals(this.dataset.getCol(1).get(0), "fd");
    }

    @Test
    public void testTranspose() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"));
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"));
        this.dataset.append(Lists.newArrayList("efg", "25", "ier"));
        this.dataset.append(Lists.newArrayList("efg", "25", "ier"));
        this.dataset.append(Lists.newArrayList("xyz", "25", "ier"));
        Dataset transpose = this.dataset.transpose();
        Assert.assertNotEquals(this.dataset, transpose);
        Assert.assertEquals(this.dataset.width(), transpose.height());
        Assert.assertEquals(this.dataset.height() + 1, transpose.width());
    }

    @Test
    public void testFilter() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("123", "18", "nan"), "classA");
        this.dataset.append(Lists.newArrayList("abc", "56", "nv"), "classB");
        this.dataset.append(Lists.newArrayList("efg", "25", "ier"), "classC");
        Dataset classA = this.dataset.filter("classA");
        Assert.assertNotEquals(this.dataset, classA);
        Assert.assertEquals(this.dataset.height(), 3);
        Assert.assertEquals(classA.height(), 1);
        Dataset classTwo = this.dataset.filter(Lists.newArrayList("classA", "classC"));
        Assert.assertEquals(classTwo.height(), 2);
    }

    @Test
    public void testSort() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("c", 18.2, "nan"));
        this.dataset.append(Lists.newArrayList("a", 56.5634454, "nv"));
        this.dataset.append(Lists.newArrayList("b", 25.3, "ier"));
        Dataset name = this.dataset.sort("name");
        Assert.assertEquals(name.get(0).get(0), "a");
        Dataset age = this.dataset.sort("age");
        Assert.assertEquals(age.get(0).get(0), "c");
    }

    @Test
    public void testPrettyString() {
        this.dataset.setHeaders(Lists.newArrayList("name", "age", "sex"));
        this.dataset.append(Lists.newArrayList("c", 18.2, "nan"));
        this.dataset.append(Lists.newArrayList("a", 56.5634454, "nv"));
        this.dataset.append(Lists.newArrayList("b", 25.3, "ier"));
        this.dataset.setTitle("xlsx");
        System.out.println(dataset.prettyString(false));
        System.out.println(TablibUtils.repeatStr("-", 60));
        System.out.println(dataset.prettyString());
    }

}
