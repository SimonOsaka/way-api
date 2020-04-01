package com.zl.way.validation.rule;

import com.zl.way.validation.AbstractWayValidationRule;

public class WayValidationRuleNotNull extends AbstractWayValidationRule<Object> {
    public WayValidationRuleNotNull(Object value) {
        super(value);
    }

    public WayValidationRuleNotNull(Object value, String alias) {
        super(value, alias);
    }

    @Override
    protected boolean check() {
        if (null == getValue()) {
            setMessage(String.format("%s不能为null", getAlias()));
            return false;
        }
        return true;
    }
}
