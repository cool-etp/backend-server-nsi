package org.cooletp.server.nsi.console.exception;

import java.io.IOException;

public class NsiFileException extends IOException {
    public NsiFileException() {
    }

    public NsiFileException(String message) {
        super(message);
    }

    public NsiFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NsiFileException(Throwable cause) {
        super(cause);
    }
}
