package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public interface SerializerSpec<I, O> {
    SerializerDef<I> getInDef();
    SerializerDef<O> getOutDef();

}
