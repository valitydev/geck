package dev.vality.geck.serializer.kit.mock;

import dev.vality.geck.serializer.StructHandler;

import java.io.IOException;


public interface FieldHandler {

    void handle(StructHandler handler) throws IOException;

}
