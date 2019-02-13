package io.github.java.tablib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class JUnitDemo {


    @Test
    public void test() {
        System.out.println("hello world");
    }

    @Test
    public void testLists() {
        Collection c = Lists.newArrayList();
        List a = Lists.newArrayList(c);
        System.out.println(a);
    }
}
