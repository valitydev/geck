package com.rbkmoney.kebab;

import java.io.IOException;

/**
 * Created by tolkonepiu on 03/02/2017.
 */
public class MockStructWriter implements StructWriter {
    @Override
    public void beginStruct(int size) throws IOException {
        //do nothing
    }

    @Override
    public void endStruct() throws IOException {
        //do nothing
    }

    @Override
    public void beginList(int size) throws IOException {
        //do nothing
    }

    @Override
    public void endList() throws IOException {
        //do nothing
    }

    @Override
    public void beginMap(int size) throws IOException {
        //do nothing
    }

    @Override
    public void endMap() throws IOException {
        //do nothing
    }

    @Override
    public void beginKey() throws IOException {
        //do nothing
    }

    @Override
    public void endKey() throws IOException {
        //do nothing
    }

    @Override
    public void beginValue() throws IOException {
        //do nothing
    }

    @Override
    public void endValue() throws IOException {
        //do nothing
    }

    @Override
    public void name(String name) throws IOException {
        //do nothing
    }

    @Override
    public void value(boolean value) throws IOException {
        //do nothing
    }

    @Override
    public void value(String value) throws IOException {
        //do nothing
    }

    @Override
    public void value(byte value) throws IOException {
        //do nothing
    }

    @Override
    public void value(short value) throws IOException {
        //do nothing
    }

    @Override
    public void value(int value) throws IOException {
        //do nothing
    }

    @Override
    public void value(double value) throws IOException {
        //do nothing
    }

    @Override
    public void value(long value) throws IOException {
        //do nothing
    }

    @Override
    public void value(byte[] value) throws IOException {
        //do nothing
    }

    @Override
    public void nullValue() throws IOException {
        //do nothing
    }

    @Override
    public void close() throws IOException {
        //do nothing
    }
}
