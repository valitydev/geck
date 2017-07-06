package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;

/**
 * Created by tolkonepiu on 06/07/2017.
 */
public interface FieldHandler {

    void handle(StructHandler handler) throws IOException;

}
