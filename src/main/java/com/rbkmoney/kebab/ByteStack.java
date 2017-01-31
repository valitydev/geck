package com.rbkmoney.kebab;

/**
 * Created by vpankrashkin on 31.01.17.
 */
public final class ByteStack {
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
            throw new RuntimeException("No more elements");
        }
        return stack[--size];
    }

    public byte peek() {
        if (size == 0) {
            throw new RuntimeException("No more elements");
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


