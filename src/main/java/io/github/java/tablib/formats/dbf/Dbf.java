package io.github.java.tablib.formats.dbf;

import io.github.java.tablib.formats.Format;

public class Dbf implements Format {

    private static final String ext = "dbf";

    @Override
    public String ext() {
        return ext;
    }
}
