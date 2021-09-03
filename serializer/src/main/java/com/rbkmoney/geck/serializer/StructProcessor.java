package com.rbkmoney.geck.serializer;

import java.io.IOException;

public interface StructProcessor<S> {

    <R> R process(S value, StructHandler<R> handler) throws IOException;

}
