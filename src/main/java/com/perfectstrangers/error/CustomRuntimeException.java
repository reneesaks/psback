package com.perfectstrangers.error;

public final class CustomRuntimeException extends RuntimeException {

    public CustomRuntimeException() {
        super();
    }

    public CustomRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(final String message) {
        super(message);
    }

    public CustomRuntimeException(final Throwable cause) {
        super(cause);
    }

}
