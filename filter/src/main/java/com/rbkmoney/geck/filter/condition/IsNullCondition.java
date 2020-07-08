package com.rbkmoney.geck.filter.condition;

import com.rbkmoney.geck.filter.Condition;

public class IsNullCondition implements Condition {

    @Override
    public boolean accept(Object object) {
        return object == null;
    }

}
