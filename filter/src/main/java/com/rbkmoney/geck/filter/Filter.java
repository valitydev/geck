package com.rbkmoney.geck.filter;

import java.util.List;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public interface Filter<T> {

    /**
     * @return true - if all rules matching; false - otherwise
     * */
    boolean match(T object);

    /**
     * @return first matched rule
     * */
    Rule matchRule(T value);

    /**
     * @return all matched rules
     * */
    List<Rule> matchRules(T value);

}
