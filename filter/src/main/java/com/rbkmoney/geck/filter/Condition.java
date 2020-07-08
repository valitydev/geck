package com.rbkmoney.geck.filter;

import java.util.Objects;

public interface Condition<T> {

    boolean accept(T t);

    default Condition<T> not() {
        return cond -> !accept(cond);
    }

    default Condition<T> and(Condition... others) {
        Objects.requireNonNull(others, "condition(s) needs to be set");
        return cond -> {
            boolean result = true;
            for (Condition<T> other : others) {
                result &= this.accept(cond) && other.accept(cond);
            }
            return result;
        };
    }

    default Condition<T> or(Condition... others) {
        Objects.requireNonNull(others, "condition(s) needs to be set");
        return cond -> {
            boolean result = false;
            for (Condition<T> other : others) {
                result |= this.accept(cond) || other.accept(cond);
            }
            return result;
        };
    }

}
