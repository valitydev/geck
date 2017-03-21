package com.rbkmoney.geck.filter.rule;

import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Parser;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.parser.PathParser;

import java.util.Objects;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public class PathConditionRule implements Rule {

    private final Parser parser;
    private final Condition[] conditions;

    public PathConditionRule(String path, Condition... conditions) {
        this(new PathParser(path), conditions);
    }

    public PathConditionRule(Parser parser, Condition... conditions) {
        this.parser = parser;
        this.conditions = conditions;
    }

    public Parser getParser() {
        return parser;
    }

    @Override
    public Condition[] getConditions() {
        return conditions;
    }

}
