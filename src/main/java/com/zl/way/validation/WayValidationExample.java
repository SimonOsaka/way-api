package com.zl.way.validation;

import java.util.HashMap;

public class WayValidationExample {
    public static void main(String[] args) {
        WayValidation validation = new WayValidation();

        AbstractWayValidationRule<Long> validationRule = new AbstractWayValidationRule<Long>(0L, "id") {

            @Override
            public boolean check() {
                Long value = getValue();
                if (value == null) {
                    setMessage("错了错了，null了");
                    return false;
                }

                Long a = (Long)getExtra().get("a");
                if (a > getValue()) {
                    setMessage("不对");
                    return false;
                }
                return true;
            }

        };
        validationRule.setExtra(new HashMap<String, Object>() {
            {
                put("a", 1L);
                put("b", 2L);
            }
        });
        validation.validRule(validationRule).validRule(new WayValidationRuleString(""));
        if (validation.hasErrors()) {
            System.out.println(validation.getErrors());
        }
    }

    static class WayValidationRuleString extends AbstractWayValidationRule<String> {
        public WayValidationRuleString(String value) {
            super(value);
        }

        public WayValidationRuleString(String value, String alias) {
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
}
