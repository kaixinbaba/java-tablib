package io.github.java.tablib.formats;

import com.google.common.collect.Lists;
import io.github.java.tablib.formats.csv.Csv;
import io.github.java.tablib.formats.dbf.Dbf;
import io.github.java.tablib.formats.excel.Xls;
import io.github.java.tablib.formats.excel.Xlsx;
import io.github.java.tablib.formats.html.Html;
import io.github.java.tablib.formats.json.Json;
import io.github.java.tablib.formats.tsv.Tsv;
import io.github.java.tablib.formats.yaml.Yaml;

import java.util.List;

public abstract class Formats {


    public static final List<Format> AVAILABLE = Lists.newArrayList();

    static {
        addAllFormats();
    }

    private static void addAllFormats() {
        AVAILABLE.add(new Csv());
        AVAILABLE.add(new Dbf());
        AVAILABLE.add(new Xlsx());
        AVAILABLE.add(new Xls());
        AVAILABLE.add(new Html());
        AVAILABLE.add(new Json());
        AVAILABLE.add(new Tsv());
        AVAILABLE.add(new Yaml());
    }
}
