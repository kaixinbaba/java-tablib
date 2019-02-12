package io.github.java.tablib.format;

/**
 * 文件处理类的父接口
 */
public interface Format {

    String ext();

    default String dotExt() {
        return "." + ext();
    }
}