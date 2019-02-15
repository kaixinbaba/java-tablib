package io.github.java.tablib;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
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

    @AllArgsConstructor
    private static class TRow {
        @Getter
        private int i;

        @Override
        public String toString() {
            return String.valueOf(i);
        }
    }

    @Test
    public void testLists() {
        int n = 5;
        String s = "abc";
        String ss = String.join("", Collections.nCopies(n, s));
        System.out.println(ss);

    }

    @Test
    public void testStringformat() {
        String format = "%-5skkk%-9skkk";
        List<String> result = Lists.newArrayList();
        String s = String.format(format, "xjj", "abcde");
        String s1 = String.format(format, "dkfj", "23");
        result.add(s);
        result.add(s1);
        System.out.println(String.join("\n", result));
    }

    @Test
    public void testFunc() {
        List<Integer> fl = Lists.newArrayList();
        fl.add(3);
        fl.add(5);
        fl.add(2);
        String formatString = String.join("|", fl.stream().map(l -> String.format("%%-%ds", l)).collect(Collectors.toList()));
        System.out.println(formatString);
        List<String> h = Lists.newArrayList();
        h.add("a");
        h.add("b");
        h.add("c");
        System.out.println(String.format(formatString, h.toArray()));

    }
}
