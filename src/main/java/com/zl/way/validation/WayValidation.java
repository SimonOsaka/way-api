package com.zl.way.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzhongliang
 */
public class WayValidation {
    private List<WayValidationError> errorList;

    public WayValidation() {
        errorList = new ArrayList<>();
    }

    public <T> WayValidation validRule(AbstractWayValidationRule<T> validationRule) {
        if (!validationRule.check()) {
            WayValidationError error = validationRule.getErrorMessage();
            errorList.add(error);
        }
        return this;
    }

    public boolean hasErrors() {
        return !errorList.isEmpty();
    }

    public List<WayValidationError> getErrors() {
        return errorList;
    }

}
