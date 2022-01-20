package dev.vality.geck.filter.rule;

import dev.vality.geck.filter.Condition;
import dev.vality.geck.filter.Parser;
import dev.vality.geck.filter.Rule;
import dev.vality.geck.filter.parser.PathParser;

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
