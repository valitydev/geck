package dev.vality.geck.migrator;

public interface SerializerSpec<I, O> {
    SerializerDef<I> getInDef();
    SerializerDef<O> getOutDef();

}
