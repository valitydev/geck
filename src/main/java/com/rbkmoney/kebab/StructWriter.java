package com.rbkmoney.kebab;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tolkonepiu on 26/01/2017.
 */
public interface StructWriter extends Closeable {

    StructWriter beginStruct() throws IOException;

    StructWriter endStruct() throws IOException;

    StructWriter beginList(int size) throws IOException;

    StructWriter endList() throws IOException;

    StructWriter beginMap(int size) throws IOException;

    StructWriter endMap() throws IOException;

    StructWriter beginKey() throws IOException;

    StructWriter endKey() throws IOException;

    StructWriter beginValue() throws IOException;

    StructWriter endValue() throws IOException;

    StructWriter name(String name) throws IOException;

    StructWriter value(boolean value) throws IOException;

    StructWriter value(String value) throws IOException;

    StructWriter value(byte value) throws IOException;

    StructWriter value(short value) throws IOException;

    StructWriter value(int value) throws IOException;

    StructWriter value(double value) throws IOException;

    StructWriter value(long value) throws IOException;

    StructWriter value(byte[] value) throws IOException;

    StructWriter nullValue() throws IOException;

}
