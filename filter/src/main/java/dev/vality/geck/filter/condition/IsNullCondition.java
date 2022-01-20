package dev.vality.geck.filter.condition;

import dev.vality.geck.filter.Condition;

public class IsNullCondition implements Condition {

    @Override
    public boolean accept(Object object) {
        return object == null;
    }

}
