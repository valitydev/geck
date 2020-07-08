package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;


public interface FieldHandler {

    void handle(StructHandler handler) throws IOException;

}
