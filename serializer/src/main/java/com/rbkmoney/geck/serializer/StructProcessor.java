package com.rbkmoney.geck.serializer;

import java.io.IOException;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public interface StructProcessor<S> {

    <R> R process(S value, StructHandler<R> handler) throws IOException;

}
