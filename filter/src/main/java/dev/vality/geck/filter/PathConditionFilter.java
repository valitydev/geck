package dev.vality.geck.filter;

import dev.vality.geck.common.util.TBaseUtil;
import dev.vality.geck.common.util.TypeUtil;
import dev.vality.geck.filter.rule.PathConditionRule;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TUnion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PathConditionFilter implements Filter<TBase> {

    private final PathConditionRule[] rules;

    public PathConditionFilter(PathConditionRule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(TBase value) {
        for (PathConditionRule rule : rules) {
            Parser parser = rule.getParser();
            if (!match(value, parser, rule.getConditions())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<Rule> matchRules(TBase value) {
        List<Rule> matched = new ArrayList<>(rules.length);
        for (PathConditionRule rule : rules) {
            Parser parser = rule.getParser();
            if (match(value, parser, rule.getConditions())) {
                matched.add(rule);
            }
        }
        return matched;
    }

    @Override
    public Rule matchRule(TBase value) {
        for (PathConditionRule rule : rules) {
            Parser parser = rule.getParser();
            if (match(value, parser, rule.getConditions())) {
                return rule;
            }
        }

        return null;
    }

    public boolean match(Object value, Parser parser, Condition... conditions) {
        for (int item = 0; item < parser.size(); item++) {
            if (value instanceof TBase) {
                TBase tBase = TypeUtil.convertType(TBase.class, value);
                TFieldIdEnum tFieldIdEnum = TBaseUtil.getField(parser.getItem(item), tBase);
                if (tFieldIdEnum == null
                        || ((tBase instanceof TUnion) && !tBase.isSet(tFieldIdEnum))) {
                    return false;
                }
                value = tBase.getFieldValue(tFieldIdEnum);
            } else if (value instanceof Collection) {
                Collection collection = TypeUtil.convertType(Collection.class, value);
                for (Object collectionItem : collection) {
                    if (match(collectionItem, parser.getSubParser(item), conditions)) {
                        return true;
                    }
                }
                return false;
            }
        }

        for (Condition condition : conditions) {
            if (!condition.accept(value)) {
                return false;
            }
        }
        return true;
    }

}
