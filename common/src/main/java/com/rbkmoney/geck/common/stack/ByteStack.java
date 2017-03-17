package com.rbkmoney.geck.common.stack;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public final class ByteStack {
    private static final int INITIAL_CAPACITY = 16;
    private byte[] stack;
    private int size;
    private final boolean defaultValSet;
    private final byte defaultVal;

    public ByteStack() {
        this(INITIAL_CAPACITY);
    }

    public ByteStack(final byte defaultVal) {
        this(true, defaultVal, INITIAL_CAPACITY);
    }

    public ByteStack(final int initialCapacity) {
        this(false, (byte) 0, initialCapacity);
    }

    public ByteStack(final byte defaultVal, final int initialCapacity) {
        this(true, defaultVal, initialCapacity);
    }

    private ByteStack(final boolean defaultValSet, final byte defaultVal, final int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.stack = new byte[initialCapacity];
        this.size = 0;
        this.defaultValSet = defaultValSet;
        this.defaultVal = defaultVal;
    }

    public void push(final byte item) {
        if (size == stack.length) {
            byte[] newStack = new byte[size << 1];
            for (int i = 0; i < stack.length; ++i) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        stack[size++] = item;
    }


    public byte pop() {
        if (size == 0) {
            if (defaultValSet) {
                return defaultVal;
            } else {
                throw new RuntimeException("No more elements");
            }
        }
        return stack[--size];
    }

    public byte peek() {
        if (size == 0) {
            if (defaultValSet) {
                return defaultVal;
            } else {
                throw new RuntimeException("No more elements");
            }
        }
        return stack[size - 1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}


