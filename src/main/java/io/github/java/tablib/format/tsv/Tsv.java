package io.github.java.tablib.format.tsv;

import io.github.java.tablib.format.Format;

public class Tsv implements Format {

    private static final String ext = "tsv";

    @Override
    public String ext() {
        return ext;
    }
}
