package com.rbkmoney.geck.filter;

import com.rbkmoney.geck.filter.rule.PathConditionRule;

import java.util.List;

public class MsgPackFilter implements Filter<byte[]> {
    private final PathConditionRule[] rules;


    public MsgPackFilter(PathConditionRule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean match(byte[] object) {
        return false;
    }

    @Override
    public Rule matchRule(byte[] value) {
        return null;
    }

    @Override
    public List<Rule> matchRules(byte[] value) {
        return null;
    }


}
