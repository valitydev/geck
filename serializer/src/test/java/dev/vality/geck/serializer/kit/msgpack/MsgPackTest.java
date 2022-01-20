package dev.vality.geck.serializer.kit.msgpack;

import dev.vality.geck.serializer.Geck;
import dev.vality.geck.serializer.GeckTestUtil;
import dev.vality.geck.serializer.domain.Status;
import dev.vality.geck.serializer.domain.TestObject;
import dev.vality.geck.serializer.domain.Unknown;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.stream.IntStream;

public class MsgPackTest {
    Geck geck = new Geck();

    @Test
    public void test() throws IOException {
        TestObject testObject = GeckTestUtil.getTestObject(100, i -> Status.unknown(new Unknown("SomeData" + i)));
        byte[] serializedData = geck.toMsgPack(testObject, true);
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testReuse() throws IOException {
        TestObject testObject = GeckTestUtil.getTestObject(100, i -> Status.unknown(new Unknown("SomeData" + i)));
        MsgPackHandler<byte[]> handler = MsgPackHandler.newBufferedInstance();
        TBaseProcessor serializer = new TBaseProcessor();
        MsgPackProcessor<byte[]> msgPackProcessor = MsgPackProcessor.newBinaryInstance();
        byte[] firstSerializedData = serializer.process(testObject, handler);
        IntStream.range(1, 10000).forEach(i -> {
            try {
                byte[] serializedData = serializer.process(testObject, handler);
                Assert.assertArrayEquals(firstSerializedData, serializedData);
                byte[] doubleSerialized = msgPackProcessor.process(firstSerializedData, handler);
                Assert.assertArrayEquals(doubleSerialized, msgPackProcessor.process(doubleSerialized, handler));
                Assert.assertEquals(testObject, msgPackProcessor.process(doubleSerialized, new TBaseHandler<>(TestObject.class)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
