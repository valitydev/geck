package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.SerializerDef;
import dev.vality.geck.migrator.ThriftDef;
import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.StructProcessor;

public interface SerializerProvider {
    <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef);
    <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef);
    boolean accept(SerializerDef sDef);
}
