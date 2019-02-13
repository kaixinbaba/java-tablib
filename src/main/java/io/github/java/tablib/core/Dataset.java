package io.github.java.tablib.core;

import com.google.common.collect.Maps;
import io.github.java.tablib.formats.Format;
import io.github.java.tablib.formats.Formats;

import java.util.Map;

/**
 * 核心对象，支持了绝大部分的方法
 */
public class Dataset {


    private static final Map<String, Format> formats = Maps.newHashMap();

    static {
        registerFormats();
    }

    private static void registerFormats() {
        for (Format format : Formats.AVAILABLE) {
            formats.put(format.ext(), format);
        }
    }
    
    public Dataset() {
        
    }
}
