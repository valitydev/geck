package com.rbkmoney.kebab;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by tolkonepiu on 26/01/2017.
 */
public interface Writer extends Closeable {

    Writer beginObject() throws IOException;

    Writer endObject() throws IOException;

    Writer beginList() throws IOException;

    Writer endList() throws IOException;

    Writer beginMap() throws IOException;

    Writer endMap() throws IOException;

    Writer beginKey() throws IOException;

    Writer endKey() throws IOException;

    Writer beginValue() throws IOException;

    Writer endValue() throws IOException;

    Writer name(String name) throws IOException;

    Writer value(boolean value) throws IOException;

    Writer value(String value) throws IOException;

    Writer value(byte value) throws IOException;

    Writer value(short value) throws IOException;

    Writer value(int value) throws IOException;

    Writer value(double value) throws IOException;

    Writer value(long value) throws IOException;

    Writer binaryValue(byte[] value) throws IOException;

    Writer nullValue() throws IOException;

}
