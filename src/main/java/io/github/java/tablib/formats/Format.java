package io.github.java.tablib.formats;

/**
 * 文件处理类的父接口
 */
public interface Format {

    String ext();

    default String dotExt() {
        return "." + ext();
    }
}
