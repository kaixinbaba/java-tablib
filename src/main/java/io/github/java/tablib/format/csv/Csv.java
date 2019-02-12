package io.github.java.tablib.format.csv;

import io.github.java.tablib.format.Format;

public class Csv implements Format {

    private static final String ext = "csv";

    @Override
    public String ext() {
        return ext;
    }
}
