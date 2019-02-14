package io.github.java.tablib.utils;

import com.google.common.collect.Lists;

import java.util.List;

public abstract class TablibUtils {


    public static List<Integer> range(int end) {
        return range(0, end);
    }

    public static List<Integer> range(int start, int end) {
        List<Integer> result = Lists.newArrayList();
        if (start >= end) {
            return result;
        }
        for (int i = start; i < end; i++) {
            result.add(i);
        }
        return result;
    }
}
