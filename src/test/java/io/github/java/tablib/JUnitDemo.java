package io.github.java.tablib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
        List<List> c = Lists.newArrayList();
        List a = Lists.newArrayList();
        a.add(1);
        a.add(2);
        a.add(3);
        List b = Lists.newArrayList();
        b.add(1);
        b.add(2);
        b.add(3);
        c.add(a);
        c.add(b);
        System.out.println(c);
        List<Object> d = c.stream().map(l -> l.remove(1)).collect(Collectors.toList());
        System.out.println(c);
        System.out.println(d);
    }
}
