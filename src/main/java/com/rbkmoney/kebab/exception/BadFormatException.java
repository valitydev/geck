package com.rbkmoney.kebab.exception;

import java.io.IOException;

/**
 * Created by tolkonepiu on 31/01/2017.
 */
public class BadFormatException extends IOException {

    public BadFormatException() {
    }

    public BadFormatException(String message) {
        super(message);
    }

    public BadFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadFormatException(Throwable cause) {
        super(cause);
    }
}
