package io.github.java.tablib.format.dbf;

import io.github.java.tablib.format.Format;

public class Dbf implements Format {

    private static final String ext = "dbf";

    @Override
    public String ext() {
        return ext;
    }
}
