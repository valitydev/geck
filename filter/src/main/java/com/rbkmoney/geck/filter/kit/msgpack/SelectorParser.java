package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.condition.EqualsCondition;
import com.rbkmoney.geck.filter.rule.ConditionRule;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.rbkmoney.geck.filter.kit.msgpack.MatchKeySelector.MATCH_RULE;
import static com.rbkmoney.geck.filter.kit.msgpack.SelectorParser.State.*;

public class SelectorParser {
    enum State {
        EVAL_READY,
        EVAL_FINISHED,
        NAME,
        ARRAY_OPEN,
        ARRAY_CLOSE,
        KEY_OPEN,
        KEY_CLOSE,
        ANY_KEY,
        ANY_NAME,
        ANY_ELEM,
        LEVEL
    }

    /*private void safeRemoveSubstate(ObjectStack<State> stack, State bound) {
        while (!stack.isEmpty() && stack.pop() != bound) ;
        if (stack.isEmpty()) {
            throw new IllegalArgumentException(String.format("Illegal state transition: %s expected in stack", bound));
        }
    }*/

    public Selector.Config[] parse(String line, Rule valueMatcher) throws IllegalArgumentException {
        List<Selector> selectors = parse(new StringReader(line), new ObjectStack<>(EVAL_READY), null, valueMatcher);

        List<Selector.Config> configs = new ArrayList<>(selectors.size());
        for (int i = 0; i < selectors.size(); i++) {
            configs.add(new Selector.Config(selectors.get(i).createContext()));
        }

        for (int i = 0; i < configs.size(); i++) {
            Selector.Config config = configs.get(i);
            if (0 == i) {
                config.prevConfig = null;
                config.prevNativeConfig = null;
            } else {
                config.prevNativeConfig = configs.get(i - 1);
                config.prevConfig = configs.get(i - 1);
            }
            if (i == selectors.size() - 1) {
                config.nextConfig = null;
                config.nextNativeConfig = null;
            } else {
                config.nextNativeConfig = configs.get(i + 1);
                config.nextConfig = configs.get(i + 1);
            }
        }

        return configs.toArray(new Selector.Config[configs.size()]);
    }

    private List<Selector> parse(Reader reader, ObjectStack<State> stateStack, State breakState, Rule valueMatcher) throws IllegalArgumentException {
        try {
            List<Selector> selectors = new ArrayList<>();
            State state = EVAL_READY;
            State prevState = stateStack.peek();
            StringBuilder buffer = new StringBuilder();

            while (state != breakState && state != EVAL_FINISHED) {
                int nextChar = reader.read();
                state = computeState(nextChar, stateStack);
                if (!(prevState == state && state == NAME)) {
                    stateStack.push(state);
                }
                switch (state) {
                    case NAME:
                        if (prevState == KEY_OPEN) {
                            selectors.add(createMapKeySelector());
                        }
                        if (prevState != NAME) {
                            selectors.add(createStructSelector());
                        }
                        buffer.append((char)nextChar);
                        break;
                    case LEVEL:
                        if (prevState == NAME) {
                            selectors.add(createNameSelector(getAndReset(buffer)));
                        } else if (prevState == KEY_CLOSE) {
                            selectors.add(createMapValueSelector());
                        }
                        break;
                    case ANY_ELEM:
                        selectors.add(createArrayAnyIndexSelector());
                        break;
                    case ANY_NAME:
                        selectors.add(createStructSelector());
                        selectors.add(createAnyNameSelector());
                        break;
                    case ARRAY_CLOSE:
                        break;
                    case ARRAY_OPEN:
                        selectors.add(createArraySelector());
                        selectors.addAll(parse(reader, stateStack, ARRAY_CLOSE, null));
                        break;
                    case KEY_OPEN:
                        selectors.add(createMapSelector());
                        selectors.addAll(parse(reader, stateStack, KEY_CLOSE, null));
                        break;
                    case ANY_KEY:
                        selectors.add(createMapKeySelector());
                        selectors.add(createMapKeyMatchSelector());
                        break;
                    case KEY_CLOSE:
                        if (prevState == NAME) {
                            selectors.add(createNameSelector(getAndReset(buffer), true));
                        }
                        selectors.add(createMapValueSelector());
                        break;
                }

                prevState = state;
            }

            prevState = stateStack.peek(1);

            if (stateStack.peek(state == breakState && state != EVAL_FINISHED ? 0 : 1) == NAME) {
                selectors.add(createNameSelector(getAndReset(buffer)));
            }

            if (valueMatcher != null) {
                /*if (state == EVAL_FINISHED && prevState == KEY_CLOSE && breakState == null) {
                    selectors.add(createMapValueSelector());
                }*/
                selectors.add(createValueSelector(valueMatcher));
            }

            return selectors;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error", e);
        }
    }

    private State computeState(int c, ObjectStack<State> stack) {
        State prevState = stack.peek();
        State nextState;
        switch (c) {
            case '[':
                if (prevState == EVAL_READY || prevState == LEVEL || prevState == KEY_OPEN) {
                    nextState = ARRAY_OPEN;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, ARRAY_OPEN));
                }
                break;
            case ']':
                if (prevState == ANY_ELEM) {
                    nextState = ARRAY_CLOSE;
                } else if (prevState == ARRAY_OPEN) {
                    stack.push(ARRAY_CLOSE);
                    nextState = EVAL_FINISHED;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, ARRAY_CLOSE));
                }
                break;
            case '{':
                if (prevState == EVAL_READY || prevState == LEVEL || prevState == KEY_OPEN) {
                    nextState = KEY_OPEN;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, KEY_OPEN));
                }
                break;
            case '}':
                if (prevState == NAME || prevState == ANY_NAME || prevState == ARRAY_CLOSE || prevState == KEY_CLOSE || prevState == ANY_KEY || prevState == EVAL_FINISHED) {
                    nextState = KEY_CLOSE;
                } else if (prevState == KEY_OPEN) {
                    stack.push(KEY_CLOSE);
                    nextState = EVAL_FINISHED;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, KEY_CLOSE));
                }
                break;
            case '*':
                if (prevState == EVAL_READY || prevState == LEVEL || prevState == ARRAY_OPEN || prevState == KEY_OPEN) {
                    nextState = (prevState == ARRAY_OPEN) ? ANY_ELEM : (prevState == KEY_OPEN) ? ANY_KEY : ANY_NAME;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s|%s|%s", prevState, ANY_NAME, ANY_ELEM, ANY_KEY));
                }
                break;
            case '.':
                if (prevState == NAME || prevState == ANY_NAME || prevState == ARRAY_CLOSE || prevState == KEY_CLOSE) {
                    nextState = LEVEL;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, LEVEL));
                }
                break;
            case -1:
                /*if (prevState == ARRAY_CLOSE || prevState == KEY_CLOSE) {
                    State lastEncloseState = stack.peek(1);
                    if (lastEncloseState != ARRAY_OPEN && lastEncloseState != KEY_OPEN) {
                        throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s -> EOF", lastEncloseState, prevState));
                    }
                } else if (prevState != NAME && prevState != ANY_NAME && prevState != EVAL_FINISHED) {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> EOF", prevState));
                }*/
                nextState = EVAL_FINISHED;
                break;
            default:
                if (prevState == EVAL_READY || prevState == NAME || prevState == LEVEL || prevState == KEY_OPEN) {
                    nextState = NAME;
                } else {
                    throw new IllegalArgumentException(String.format("Illegal state transition: %s -> %s", prevState, NAME));
                }
                break;
        }
        return nextState;
    }

    private String getAndReset(StringBuilder builder) {
        String data = builder.toString();
        builder.setLength(0);
        return data;
    }

    private ArrayIndexSelector createArrayIndexSelector(Rule rule, Selector.Type type) {
        return new ArrayIndexSelector(rule, type);
    }

    private ArrayIndexSelector createArrayAnyIndexSelector() {
        return createArrayIndexSelector(MATCH_RULE, Selector.Type.REPEATABLE);
    }

    private ArraySelector createArraySelector() {
        return new ArraySelector(MATCH_RULE, Selector.Type.UNREPEATABLE);
    }

    private MapValueSelector createMapValueSelector() {
        return new MapValueSelector(MATCH_RULE, Selector.Type.UNREPEATABLE);
    }

    private MatchKeySelector createMapKeyMatchSelector() {
        return new MatchKeySelector(Selector.Type.UNREPEATABLE);
    }

    private MapKeySelector createMapKeySelector(Rule rule, Selector.Type type) {
        return new MapKeySelector(rule, type);
    }

    private MapKeySelector createMapKeySelector() {
        return createMapKeySelector(MATCH_RULE, Selector.Type.REPEATABLE);
    }

    private MapSelector createMapSelector() {
        return new MapSelector(MATCH_RULE, Selector.Type.UNREPEATABLE);
    }

    private StructSelector createStructSelector() {
        return new StructSelector(MATCH_RULE, Selector.Type.UNREPEATABLE);
    }

    private FieldSelector createAnyNameSelector() {
        return createNameSelector(MATCH_RULE, Selector.Type.REPEATABLE);
    }

    private FieldSelector createNameSelector(String name, boolean jumpValue) {
        return createNameSelector(new ConditionRule(new EqualsCondition(name)), Selector.Type.REPEATABLE, jumpValue);
    }
    private FieldSelector createNameSelector(String name) {
        return createNameSelector(name, false);
    }

    private FieldSelector createNameSelector(Rule rule, Selector.Type type, boolean jumpValue) {
        return new FieldSelector(rule, type, jumpValue);
    }
    private FieldSelector createNameSelector(Rule rule, Selector.Type type) {
        return createNameSelector(rule, type, false);
    }

    private ValueSelector createValueSelector(Rule rule) {
        return new ValueSelector(rule, Selector.Type.UNREPEATABLE);
    }

}
