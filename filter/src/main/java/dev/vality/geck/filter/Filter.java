package dev.vality.geck.filter;

import java.util.List;

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
