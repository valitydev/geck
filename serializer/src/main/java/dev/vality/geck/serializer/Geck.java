package dev.vality.geck.serializer;

import dev.vality.geck.serializer.kit.json.JsonHandler;
import dev.vality.geck.serializer.kit.msgpack.MsgPackHandler;
import dev.vality.geck.serializer.kit.msgpack.MsgPackProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import org.apache.thrift.TBase;

import java.io.IOException;

public class Geck {

    public static String toJson(TBase src) {
        try {
            TBaseProcessor structProcessor = new TBaseProcessor();
            JsonHandler jsonHandler = new JsonHandler();
            return structProcessor.process(src, jsonHandler).toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] toMsgPack(TBase src) {
        return toMsgPack(src, true);
    }

    public static byte[] toMsgPack(TBase src, boolean useDict) {
        try {
            MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance(useDict);
            TBaseProcessor serializer = new TBaseProcessor();

            return serializer.process(src, handler);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends TBase> T msgPackToTBase(byte[] data, Class<T> tClass) {
        try {
            return MsgPackProcessor.newBinaryInstance().process(data, new TBaseHandler<>(tClass));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <R> R write(TBase src, StructHandler<R> writer) {
        TBaseProcessor structProcessor = new TBaseProcessor();
        try {
            return structProcessor.process(src, writer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
