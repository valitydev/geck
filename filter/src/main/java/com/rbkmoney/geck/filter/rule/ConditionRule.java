package com.rbkmoney.geck.filter.rule;

import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Rule;

/**
 * Created by vpankrashkin on 22.09.17.
 */
public class ConditionRule implements Rule {
    private final Condition[] conditions;

    public ConditionRule(Condition condition) {
        this.conditions = new Condition[]{condition};
    }

    @Override
    public Condition[] getConditions() {
        return conditions;
    }
}
