package io.github.java.tablib.core;

import com.google.common.collect.Maps;
import io.github.java.tablib.formats.Format;

import java.util.Map;

/**
 * 核心对象，支持了绝大部分的方法
 */
public class Dateset {


    private static final Map<String, Format> formats = Maps.newHashMap();

    static {
        registerFormats();
    }

    private static void registerFormats() {
    }
}
