package com.rbkmoney.kebab;

import com.rbkmoney.kebab.serializer.TBaseSerializer;
import com.rbkmoney.kebab.writer.JsonStructWriter;
import org.apache.thrift.TBase;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by tolkonepiu on 24/01/2017.
 */
public class Kebab<T extends TBase> {

    public String toJson(T src) {
        StringWriter writer = new StringWriter();
        Serializer serializer = new TBaseSerializer();
        try {
            serializer.write(new JsonStructWriter(writer), src);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return writer.toString();
    }

    public byte[] toMsgPack(T src) {
        throw new UnsupportedOperationException("under contruction");
    }


}
