package com.zl.way.validation;

/**
 * @author xuzhongliang
 */
public class WayValidationError {
    public WayValidationError(String message) {
        this.message = message;
    }

    public WayValidationError(String alias, String message) {
        this.alias = alias;
        this.message = message;
    }

    private String alias;
    private String message;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WayValidationError{" + "alias='" + alias + '\'' + ", message='" + message + '\'' + '}';
    }
}
