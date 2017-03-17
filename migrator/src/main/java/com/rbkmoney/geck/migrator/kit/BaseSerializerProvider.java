package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.SerializerDef;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vpankrashkin on 15.03.17.
 */
public class BaseSerializerProvider implements SerializerProvider {
    private final List<SerializerProvider> providers;

    public BaseSerializerProvider(List<SerializerProvider> providers) {
        this();
        providers.addAll(0, providers);
    }

    public BaseSerializerProvider() {
        providers = new ArrayList<>();
        //add built in serializers
    }

    @Override
    public <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef) {
        SerializerProvider provider = getMatching(sDef);
        if (provider != null) {
            return provider.getStructProcessor(sDef, srcTypeDef);
        }
        return null;
    }

    @Override
    public <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef) {
        SerializerProvider provider = getMatching(sDef);
        if (provider != null) {
            return provider.getStructHandler(sDef, resTypeDef);
        }
        return null;
    }

    @Override
    public boolean accept(SerializerDef sDef) {
        return getMatching(sDef) != null;
    }

    protected SerializerProvider getMatching(SerializerDef sDef) {
        for (int i = 0; i < providers.size(); ++i) {
            if (providers.get(i).accept(sDef)) {
                return providers.get(i);
            }
        }
        return null;
    }
}
