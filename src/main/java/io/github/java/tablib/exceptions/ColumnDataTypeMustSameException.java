package io.github.java.tablib.exceptions;

public class ColumnDataTypeMustSameException extends TablibBaseException {

    public ColumnDataTypeMustSameException(Integer columnIndex) {
        super(String.format(
                "Data type must be same in one column, The wrong type column index is %s"
                , columnIndex == null ? "Unknown" : columnIndex));
    }
}
