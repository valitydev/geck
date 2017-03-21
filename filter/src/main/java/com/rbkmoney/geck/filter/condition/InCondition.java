package com.rbkmoney.geck.filter.condition;

import com.rbkmoney.geck.filter.Condition;

import java.util.Collection;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class InCondition<T> implements Condition {

    private final Collection<T> collection;

    public InCondition(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public boolean accept(Object object) {
        return collection.contains(object);
    }
}
