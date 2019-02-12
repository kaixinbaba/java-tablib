package io.github.java.tablib.formats.yaml;

import io.github.java.tablib.formats.Format;

public class Yaml implements Format {

    private static final String ext = "yml";

    @Override
    public String ext() {
        return ext;
    }
}
