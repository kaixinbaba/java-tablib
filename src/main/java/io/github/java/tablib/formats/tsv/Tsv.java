package io.github.java.tablib.formats.tsv;

import io.github.java.tablib.formats.Format;

public class Tsv implements Format {

    private static final String ext = "tsv";

    @Override
    public String ext() {
        return ext;
    }
}
