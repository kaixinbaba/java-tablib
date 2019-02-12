package io.github.java.tablib;

import com.google.common.collect.Lists;
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
        String s = "df";
        List a = Lists.newArrayList(s);
        System.out.println(a);
    }
}
