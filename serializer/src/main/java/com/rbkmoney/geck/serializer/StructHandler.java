package com.rbkmoney.geck.serializer;

import java.io.IOException;

/**
 * Created by tolkonepiu on 26/01/2017.
 */
public interface StructHandler<R> {

    byte DEFAULT_FIELD_ID = Byte.MIN_VALUE;

    void beginStruct(int size) throws IOException;

    void endStruct() throws IOException;

    void beginList(int size) throws IOException;

    void endList() throws IOException;

    void beginSet(int size) throws IOException;

    void endSet() throws IOException;

    void beginMap(int size) throws IOException;

    void endMap() throws IOException;

    void beginKey() throws IOException;

    void endKey() throws IOException;

    void beginValue() throws IOException;

    void endValue() throws IOException;

    void name(String name) throws IOException;

    default void name(byte id, String name) throws IOException {
        name(name);
    }

    void value(boolean value) throws IOException;

    void value(String value) throws IOException;

    void value(double value) throws IOException;

    void value(long value) throws IOException;

    void value(byte[] value) throws IOException;

    void nullValue() throws IOException;

    R getResult() throws IOException;

}
