package io.github.java.tablib;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

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
        List<TRow> rows = Lists.newArrayList();
        rows.add(new TRow(2));
        rows.add(new TRow(1));
        rows.add(new TRow(8));
        rows.add(new TRow(7));
        rows.add(new TRow(4));
        rows.add(new TRow(3));
        System.out.println(rows);
        rows.sort(Comparator.comparing(row -> ((TRow) row).getI()).reversed());
        System.out.println(rows);

    }
}
