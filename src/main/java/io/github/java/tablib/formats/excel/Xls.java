package io.github.java.tablib.formats.excel;

import io.github.java.tablib.formats.Format;

public class Xls implements Format {

    private static final String ext = "xls";

    @Override
    public String ext() {
        return ext;
    }
}
