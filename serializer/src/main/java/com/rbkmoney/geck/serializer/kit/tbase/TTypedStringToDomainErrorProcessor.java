package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class TTypedStringToDomainErrorProcessor implements StructProcessor<String> {
    @Override
    public <R> R process(String value, StructHandler<R> handler) throws IOException {
        String[] tokens = value.split(":");
        for (int i = 0; i < tokens.length; i++) {
            handler.beginStruct(1);
            handler.name(tokens[i]);
        }
        handler.beginStruct(0);
        handler.endStruct();
        for (int i = 0; i < tokens.length; i++) {
            handler.endStruct();
        }
        return handler.getResult();
    }
}
