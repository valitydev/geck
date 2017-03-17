package com.rbkmoney.geck.common.stack;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public final class ObjectStack<E> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] stack;
    private int size;
    private final boolean defaultValSet;
    private final E defaultVal;

    public ObjectStack() {
        this(INITIAL_CAPACITY);
    }

    public ObjectStack(final E defaultVal) {
        this(true, defaultVal, INITIAL_CAPACITY);
    }

    public ObjectStack(final int initialCapacity) {
        this(false, null, initialCapacity);
    }

    public ObjectStack(final E defaultVal, final int initialCapacity) {
        this(true, defaultVal, initialCapacity);
    }

    private ObjectStack(final boolean defaultValSet, final E defaultVal, final int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.stack = new Object[initialCapacity];
        this.size = 0;
        this.defaultValSet = defaultValSet;
        this.defaultVal = defaultVal;
    }

    public void push(final E item) {
        if (size == stack.length) {
            Object[] newStack = new Object[size << 1];
            for (int i = 0; i < stack.length; ++i) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        stack[size++] = item;
    }


    public E pop() {
        if (size == 0) {
            if (defaultValSet) {
                return defaultVal;
            } else {
                throw new RuntimeException("No more elements");
            }
        }
        int newSize = --size;
        Object val = stack[newSize];
        stack[newSize] = null;
        return (E) val;
    }

    public E peek() {
        if (size == 0) {
            if (defaultValSet) {
                return defaultVal;
            } else {
                throw new RuntimeException("No more elements");
            }
        }
        return (E) stack[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}


