package io.github.java.tablib.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

@SuppressWarnings("all")
public class TablibUtilsTest {


    @Test
    public void testRange() {
        List<Integer> range = TablibUtils.range(11);
        Assert.assertEquals(range.size(), 11);
        List<Integer> range1 = TablibUtils.range(11, 20);
        Assert.assertEquals(range1.size(), 20 - 11);
    }

}
