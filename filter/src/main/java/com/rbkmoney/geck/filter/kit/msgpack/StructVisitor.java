package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.rbkmoney.geck.serializer.StructHandleResult.*;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class StructVisitor {
    private final Supplier<Selector.Config[]> cnfSupplier;
    private Selector.Config config;
    private List<Rule> selectedRules;
    private Selector.Context lastRepeatCtx;

    StructVisitor(Supplier<Selector.Config[]> cnfSupplier) {
        Objects.requireNonNull(cnfSupplier);
        this.cnfSupplier = cnfSupplier;
    }

    void init() {
        selectedRules = null;
        lastRepeatCtx = null;
        selectedRules = new ArrayList<>();
        config = cnfSupplier.get()[0];
    }

    private Selector.Config resetAndPullLevel() {
        if (config.context == lastRepeatCtx) {
            lastRepeatCtx.repeat();
            lastRepeatCtx = null;
            return config;
        } else {
            Selector.Config pulledConfig = config;
            do {
                pulledConfig.context.reset();
                pulledConfig = pulledConfig.prevConfig;
            } while (!pulledConfig.context.getSelector().isPullable());
            return pulledConfig;
        }

        /*Selector.Context upLvlContext = contexts[selector.getContextIndex() - 1];//lastRepeatCtx--
        if (upLvlContext.getSelector().getContextIndex() == lastRepeatCtx) {//reset all levels until lastRepeatCtx reached
            //upLvlContext.repeat();
            return upLvlContext.getSelector();
        } else {
            upLvlContext.reset();
            Selector.Context context = contexts[upLvlContext.getSelector().getContextIndex() - 1];
            if (lastRepeatCtx == context.getSelector().getContextIndex()) {
                context.repeat();
            }
            return context.getSelector();
        }*/

    }

    StructHandleResult visit(byte itemType, Object val) {
        StructHandleResult visitResult;
        if (lastRepeatCtx != null) {
            Selector.Config pulledConfig = resetAndPullLevel();
            if (pulledConfig != config) {
                visitResult = pulledConfig.context.getSelector().getLowestSkipOp(config.context);
                config = pulledConfig;
                return visitResult;
            }
        }
        SelectionResult result;
        do {
            result = config.context.getSelector().select(itemType, val, config);
            if (result.type == SelectionResult.SelectionType.REUSE_LEVEL) {
                config = ((SelectionResult.ReuseLevel) result).config;
            } else {
                break;
            }
        } while (true);

        switch (result.type) {
            case MATCH:
                selectedRules = ((SelectionResult.Match) result).rules;
                return StructHandleResult.TERMINATE;
            case MISMATCH:
                lastRepeatCtx = findLastRepeatPosition(config);
                if (lastRepeatCtx != null) {
                    visitResult = config.context.getSelector().getLowestSkipOp(config.context);
                    config = resetAndPullLevel();
                    return visitResult;
                } else {
                    return TERMINATE;
                }
            case REUSE_LEVEL:
                return SKIP_SUBTREE;
            case PUSH_LEVEL:
                SelectionResult.PushLevel push = (SelectionResult.PushLevel) result;
                config = push.pushedConfig;
                return push.jumpValue ? JUMP_VALUE : CONTINUE;
            default:
                throw new IllegalStateException("Illegal return type: " + result.type);
        }
    }

    List<Rule> getSelected() {
        return selectedRules;
    }

    private Selector.Context findLastRepeatPosition(Selector.Config config) {
        Selector.Config currConfig = config;
        do {
            if (!currConfig.context.isLevelConsumed() && currConfig.context.getSelector().getType() == Selector.Type.REPEATABLE) {
                return currConfig.context;
            }
            currConfig = currConfig.prevConfig;
        } while (currConfig != null);
        return currConfig == null ? null : currConfig.context;
    }
}
