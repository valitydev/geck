package com.rbkmoney.geck.serializer.kit.tbase;

import java.io.IOException;

/**
 * Created by vpankrashkin on 27.02.18.
 */
public class TTypedToStringErrorHandler extends TDomainToStringErrorHandler {

    @Override
    public void beginStruct(int size) throws IOException {
    }

    @Override
    public void endStruct() throws IOException {

    }

    @Override
    public void name(String name) throws IOException {
        super.processCode(name);
    }

    @Override
    public void value(String value) throws IOException {
        throw new UnsupportedOperationException();
    }
}
