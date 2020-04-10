package com.zl.way.validation.rule;

import com.zl.way.validation.AbstractWayValidationRule;

public class WayValidationRuleStringNotBlank extends AbstractWayValidationRule<String> {
    public WayValidationRuleStringNotBlank(String value) {
        super(value);
    }

    public WayValidationRuleStringNotBlank(String value, String alias) {
        super(value, alias);
    }

    @Override
    protected boolean check() {
        if (null == getValue() || getValue().isEmpty()) {
            setMessage("不能为空");
            return false;
        }
        return true;
    }
}
