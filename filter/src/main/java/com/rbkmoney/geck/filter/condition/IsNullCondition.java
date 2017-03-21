package com.rbkmoney.geck.filter.condition;

import com.rbkmoney.geck.filter.Condition;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class IsNullCondition implements Condition {

    @Override
    public boolean accept(Object object) {
        return object == null;
    }

}
