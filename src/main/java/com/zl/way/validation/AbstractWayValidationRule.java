package com.zl.way.validation;

import java.util.Map;

/**
 * @author xuzhongliang
 */
public abstract class AbstractWayValidationRule<T> {

    public AbstractWayValidationRule(T value) {
        this.value = value;
    }

    public AbstractWayValidationRule(T value, String alias) {
        this.value = value;
        this.alias = alias;
    }

    private T value;
    private String message;
    private String alias;
    private Map<String, Object> extra;

    protected abstract boolean check();

    public WayValidationError getErrorMessage() {
        return new WayValidationError(this.alias, this.message);
    }

    public T getValue() {
        return this.value;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public String getAlias() {
        return alias;
    }
}
