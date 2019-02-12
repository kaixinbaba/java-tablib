package io.github.java.tablib.format.excel;

import io.github.java.tablib.format.Format;

public class Xlsx implements Format {

    private static final String ext = "xlsx";

    @Override
    public String ext() {
        return ext;
    }
}
