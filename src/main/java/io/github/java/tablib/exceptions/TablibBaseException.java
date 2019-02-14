package io.github.java.tablib.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TablibBaseException extends RuntimeException {

    public TablibBaseException(String message) {
        super(message);
    }
}
