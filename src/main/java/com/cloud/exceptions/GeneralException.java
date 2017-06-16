package com.cloud.exceptions;

public class GeneralException extends RuntimeException {

    public GeneralException() {
        super("General Exception!");
    }

    public GeneralException(final String message) {
        super(message);
    }

    public GeneralException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GeneralException(final Throwable cause) {
        super(cause);
    }
}