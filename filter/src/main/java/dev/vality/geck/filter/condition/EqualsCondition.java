package dev.vality.geck.filter.condition;

import dev.vality.geck.filter.Condition;

public class EqualsCondition implements Condition {

    private final Object object;

    public EqualsCondition(Object object) {
        this.object = object;
    }

    @Override
    public boolean accept(Object t) {
        return object.equals(t);
    }
}
