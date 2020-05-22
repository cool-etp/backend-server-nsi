package org.cooletp.server.nsi.console.exception;

public class CliParseException extends RuntimeException {
    public CliParseException() {
    }

    public CliParseException(String message) {
        super(message);
    }

    public CliParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CliParseException(Throwable cause) {
        super(cause);
    }

    public CliParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
