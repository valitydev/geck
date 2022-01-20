package dev.vality.geck.serializer.kit.tbase;

import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.StructProcessor;

import java.io.IOException;

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
