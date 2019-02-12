package io.github.java.tablib.formats.csv;

import io.github.java.tablib.formats.Format;

public class Csv implements Format {

    private static final String ext = "csv";

    @Override
    public String ext() {
        return ext;
    }
}
