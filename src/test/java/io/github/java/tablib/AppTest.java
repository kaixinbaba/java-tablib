package io.github.java.tablib;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void test() {
        System.out.println("hello world");
    }

    @Test
    public void testLists() {
        List a = Lists.newArrayList("bbbb");
        List b = Lists.newArrayList("aaaa");
        System.out.println(a);
        System.out.println(b);
        System.out.println("----");
        a.retainAll(b);
        System.out.println(!a.isEmpty());
        System.out.println(a);
        System.out.println(b);
    }
}
