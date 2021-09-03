package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.SerializerDef;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;

public interface SerializerProvider {
    <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef);
    <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef);
    boolean accept(SerializerDef sDef);
}
