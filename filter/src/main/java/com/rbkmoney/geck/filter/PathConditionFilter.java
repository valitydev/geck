package com.rbkmoney.geck.filter;

import com.rbkmoney.geck.common.util.TBaseUtil;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.geck.filter.rule.PathConditionRule;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

/**
 * Created by tolkonepiu on 17/03/2017.
 */
public class PathConditionFilter implements Filter<TBase> {

    private final PathConditionRule[] rules;

    public PathConditionFilter(PathConditionRule... rules) {
        this.rules = rules;
    }


    @Override
    public boolean match(TBase object) {
        for (PathConditionRule rule : rules) {
            Object value = object;
            Parser parser = rule.getParser();

            for (int item = 0; item < parser.size(); item++) {
                if (value == null || !(value instanceof TBase)) {
                    return false;
                }
                value = getFieldValue(parser.getItem(item), TypeUtil.convertType(TBase.class, value));
            }

            for (Condition condition : rule.getConditions()) {
                if(!condition.accept(value)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Object getFieldValue(String path, TBase tBase) {
        TFieldIdEnum tFieldIdEnum = TBaseUtil.getField(path, tBase);
        if (tFieldIdEnum != null && tBase.isSet(tFieldIdEnum)) {
            return tBase.getFieldValue(tFieldIdEnum);
        }
        return null;
    }
}
