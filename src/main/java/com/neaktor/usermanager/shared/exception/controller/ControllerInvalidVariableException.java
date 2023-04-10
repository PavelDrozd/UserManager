package com.neaktor.usermanager.shared.exception.controller;

public class ControllerInvalidVariableException extends ControllerException {

    public ControllerInvalidVariableException() {
        super();
    }

    public ControllerInvalidVariableException(String message) {
        super(message);
    }

    public ControllerInvalidVariableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerInvalidVariableException(Throwable cause) {
        super(cause);
    }

    protected ControllerInvalidVariableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
