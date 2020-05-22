package org.cooletp.server.nsi.console.exception;

import java.io.IOException;

public class FtpClientException extends IOException {
    public FtpClientException() {
    }

    public FtpClientException(String message) {
        super(message);
    }

    public FtpClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpClientException(Throwable cause) {
        super(cause);
    }
}
