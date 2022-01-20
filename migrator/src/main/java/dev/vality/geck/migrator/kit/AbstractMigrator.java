package dev.vality.geck.migrator.kit;

import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.StructProcessor;
import dev.vality.geck.migrator.*;

import java.io.IOException;

public abstract class AbstractMigrator implements Migrator {
    private final SerializerProvider serializerProvider;

    public AbstractMigrator() {
        this(new BaseSerializerProvider());
    }

    public AbstractMigrator(SerializerProvider serializerProvider) {
        this.serializerProvider = serializerProvider;
    }

    protected <I, O> O serialize(I src, SerializerSpec<I, O> serializerSpec, ThriftSpec thriftSpec) throws MigrationException {
        return serialize(src, serializerSpec.getInDef(), serializerSpec.getOutDef(), thriftSpec);
    }

    protected <I, O> O serialize(I src, SerializerDef<I> inDef, SerializerDef<O> outDef, ThriftSpec thriftSpec) throws MigrationException {
        if (inDef.equals(outDef)) {
            return (O) src;
        }
        StructProcessor<I> processor = serializerProvider.getStructProcessor(inDef, thriftSpec.getInDef());
        StructHandler<O> handler = serializerProvider.getStructHandler(outDef, thriftSpec.getOutDef());
        try {
            return processor.process(src, handler);
        } catch (IOException e) {
            throw new MigrationException("Failed to process source", e);
        }
    }

    protected SerializerProvider getSerializerProvider() {
        return serializerProvider;
    }
}
