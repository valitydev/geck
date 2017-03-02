package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public class MigrationException extends RuntimeException {
    public MigrationException() {
    }

    public MigrationException(String message) {
        super(message);
    }

    public MigrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MigrationException(Throwable cause) {
        super(cause);
    }

    public MigrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
