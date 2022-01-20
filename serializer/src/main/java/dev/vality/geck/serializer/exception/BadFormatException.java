package dev.vality.geck.serializer.exception;

import java.io.IOException;


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
