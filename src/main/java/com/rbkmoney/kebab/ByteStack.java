package com.rbkmoney.kebab;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public class ByteStack {
    private byte[] stack;
    private int size;


    public ByteStack(final int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.stack = new byte[initialCapacity];
        this.size = 0;
    }

    public ByteStack() {
        this(10);
    }

    public final void push(final byte item) {
        if (size == stack.length) {
            byte[] newStack = new byte[size << 1];
            for (int i = 0; i < stack.length; ++i) {
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        stack[size++] = item;
    }


    public final byte pop() {
        if (size == 0) {
            throw new RuntimeException("No more elements");
        }
        return stack[--size];
    }

    public final byte peek() {
        if (size == 0) {
            throw new RuntimeException("No more elements");
        }
        return stack[size];
    }

    public final int size() {
        return size;
    }

    public final boolean isEmpty() {
        return size == 0;
    }

}


