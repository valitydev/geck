package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;


abstract class TErrorHandler<R> implements StructHandler<R> {

    @Override
    public void beginList(int size) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endList() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void beginSet(int size) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endSet() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void beginMap(int size) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endMap() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void beginKey() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endKey() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void beginValue() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void endValue() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void value(boolean value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void value(String value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void value(double value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void value(long value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void value(byte[] value) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullValue() throws IOException {
        throw new UnsupportedOperationException();
    }
}
