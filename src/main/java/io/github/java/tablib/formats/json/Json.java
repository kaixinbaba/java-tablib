package io.github.java.tablib.formats.json;

import io.github.java.tablib.formats.Format;

public class Json implements Format {

    private static final String ext = "json";

    @Override
    public String ext() {
        return ext;
    }
}
