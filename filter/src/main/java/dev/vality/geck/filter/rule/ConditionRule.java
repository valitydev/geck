package dev.vality.geck.filter.rule;

import dev.vality.geck.filter.Condition;
import dev.vality.geck.filter.Rule;

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
