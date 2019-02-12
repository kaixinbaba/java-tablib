package io.github.java.tablib.formats.excel;

import io.github.java.tablib.formats.Format;

public class Xlsx implements Format {

    private static final String ext = "xlsx";

    @Override
    public String ext() {
        return ext;
    }
}
