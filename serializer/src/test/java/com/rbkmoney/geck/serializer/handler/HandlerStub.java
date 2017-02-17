package com.rbkmoney.geck.serializer.handler;

import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;

/**
 * Created by vpankrashkin on 03.02.17.
 */
public class HandlerStub implements StructHandler<byte[]> {
    @Override
    public void beginStruct(int size) throws IOException {

    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void beginList(int size) throws IOException {

    }

    @Override
    public void endList() throws IOException {

    }

    @Override
    public void beginSet(int size) throws IOException {

    }

    @Override
    public void endSet() throws IOException {

    }

    @Override
    public void beginMap(int size) throws IOException {

    }

    @Override
    public void endMap() throws IOException {

    }

    @Override
    public void beginKey() throws IOException {

    }

    @Override
    public void endKey() throws IOException {

    }

    @Override
    public void beginValue() throws IOException {

    }

    @Override
    public void endValue() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {

    }

    @Override
    public void value(boolean value) throws IOException {

    }

    @Override
    public void value(String value) throws IOException {

    }

    @Override
    public void value(double value) throws IOException {

    }

    @Override
    public void value(long value) throws IOException {

    }

    @Override
    public void value(byte[] value) throws IOException {

    }

    @Override
    public void nullValue() throws IOException {

    }

    @Override
    public byte[] getResult() throws IOException {
        return new byte[0];
    }

}
