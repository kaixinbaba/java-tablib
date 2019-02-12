package io.github.java.tablib.format.yaml;

import io.github.java.tablib.format.Format;

public class Yaml implements Format {

    private static final String ext = "yml";

    @Override
    public String ext() {
        return ext;
    }
}
